package org.reader.feed;

import org.reader.constant.FeedType;
import org.reader.feed.atom.AtomFeedParserStrategy;
import org.reader.feed.rdf.RdfFeedParserStrategy;
import org.reader.model.Feed;
import org.reader.feed.rss.RssFeedParserStrategy;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class FeedParser extends DefaultHandler {

    private static final Logger log = Logger.getLogger( FeedParser.class.getSimpleName() );
    private FeedParserStrategy feedParserStrategy;

    @Override
    public void startElement( String uri, String localName, String qName, Attributes attributes ) throws SAXException {
        if ( localName.equalsIgnoreCase( FeedType.RSS.getName() ) )
            feedParserStrategy = new RssFeedParserStrategy();
        else if ( localName.equalsIgnoreCase( FeedType.RDF.getName() ) )
            feedParserStrategy = new RdfFeedParserStrategy();
        else if ( localName.equalsIgnoreCase( FeedType.ATOM.getName() ) )
            feedParserStrategy = new AtomFeedParserStrategy();
        if ( feedParserStrategy == null )
            throw new SAXException( "Root element doesn't designate an RSS/ATOM/RDF feed, " +
                    "encountered: %s".formatted( localName ) );
        feedParserStrategy.processStartElement( uri, localName, qName, attributes );

    }

    @Override
    public void endElement( String uri, String localName, String qName ) throws SAXException {
        feedParserStrategy.processEndElement( uri, localName, qName );
    }

    @Override
    public void fatalError( SAXParseException exception ) {
        log.log( Level.SEVERE, "FATAL SAX parse error encountered: %s"
                .formatted( exception ) );
    }

    @Override
    public void characters( char[] chars, int start, int length ) {
        feedParserStrategy.processCharacters( chars, start, length );
    }

    public Feed getCurrentFeed() {
        return feedParserStrategy.getCurrentFeed();
    }

    public FeedType getCurrentFeedType() {
        return feedParserStrategy.getFeedType();
    }
}
