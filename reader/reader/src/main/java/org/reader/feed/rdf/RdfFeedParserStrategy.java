package org.reader.feed.rdf;

import org.reader.constant.FeedType;
import org.reader.constant.Uri;
import org.reader.feed.rss.RssFeedParserStrategy;
import org.reader.util.DateUtil;
import org.xml.sax.Attributes;

import static org.reader.constant.FeedElement.*;

public class RdfFeedParserStrategy extends RssFeedParserStrategy {

    @Override
    protected void processStartElement( String uri, String localName, String qName, Attributes attributes ) {
        if ( getTopElementInStack() == ITEM && localName.equalsIgnoreCase( "date" )
                && uri.equalsIgnoreCase( Uri.URI_DC.getUri() ) )
            pushElement( ITEM_DC_DATE );
        else if ( localName.equalsIgnoreCase( "item" ) && getTopElementInStack() == ROOT_ELEMENT ) {
            pushElement( ITEM );
            setCurrentArticleGuid( attributes );
        }
        else
            super.processStartElement( uri, localName, qName, attributes );
    }

    private void setCurrentArticleGuid( Attributes attributes ) {
        final var about = attributes.getValue( Uri.URI_RDF.getUri(), "about" );
        if ( !about.isBlank() )
            currentArticle.setGuid( about );
    }
    @Override
    protected void processEndElement( String uri, String localName, String qName ){
        if ( localName.equalsIgnoreCase( "date" ) && getTopElementInStack() == ITEM_DC_DATE
                && uri.equalsIgnoreCase( Uri.URI_DC.getUri() ) ) {
            currentArticle.setPublicationDate( DateUtil.parseDateStringToZonedDateTime( getContent() ) );
            super.clearEndElementDetails();
        }
        else
            super.processEndElement( uri, localName, qName );
    }

    public FeedType getFeedType() {
        return FeedType.RDF;
    }
}
