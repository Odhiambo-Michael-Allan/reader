package org.reader.feed.rss;

import org.reader.constant.FeedType;
import org.reader.feed.FeedParserStrategy;
import org.reader.util.DateUtil;
import org.xml.sax.Attributes;

import java.util.logging.Logger;
import static org.reader.constant.FeedElement.*;
import static org.reader.constant.Uri.*;

public class RssFeedParserStrategy extends FeedParserStrategy {

    private static final Logger log = Logger.getLogger( RssFeedParserStrategy.class.getSimpleName() );
    @Override
    protected void processStartElement( String uri, String localName, String qName, Attributes attributes ) {
        log.info( "Start element being processed: %s".formatted( localName ) );
        if ( localName.equalsIgnoreCase( FeedType.RSS.getName() ) ||
                localName.equalsIgnoreCase( FeedType.RDF.getName() ) )
            pushElement( ROOT_ELEMENT );
        else if ( getTopElementInStack() == ROOT_ELEMENT && localName.equalsIgnoreCase( "channel" ) )
            pushElement( CHANNEL );
        else if ( getTopElementInStack() == CHANNEL && localName.equalsIgnoreCase( "title" ) )
            pushElement( TITLE );
        else if ( getTopElementInStack() == CHANNEL && localName.equalsIgnoreCase( "description" ) )
            pushElement( DESCRIPTION );
        else if ( getTopElementInStack() == CHANNEL && localName.equalsIgnoreCase( "link" )
                && !uri.equalsIgnoreCase( URI_ATOM.getUri() ) )
            pushElement( LINK );
        else if ( getTopElementInStack() == CHANNEL && localName.equalsIgnoreCase( "language" ) )
            pushElement( LANGUAGE );
        else if ( getTopElementInStack() == CHANNEL && localName.equalsIgnoreCase( "item" ) )
            pushElement( ITEM );
        else if ( getTopElementInStack() == ITEM && localName.equalsIgnoreCase( "title" ) )
            pushElement( ITEM_TITLE );
        else if ( getTopElementInStack() == ITEM && localName.equalsIgnoreCase( "guid" ) )
            pushElement( ITEM_GUID );
        else if ( getTopElementInStack() == ITEM && localName.equalsIgnoreCase( "link" )
                && !uri.equals( URI_ATOM.getUri() ) )
            pushElement( ITEM_LINK );
        else if ( getTopElementInStack() == ITEM && localName.equalsIgnoreCase( "description" ) )
            pushElement( ITEM_DESCRIPTION );
        else if ( getTopElementInStack() == ITEM && localName.equalsIgnoreCase( "creator" )
                && uri.equalsIgnoreCase( URI_DC.getUri() ) )
            pushElement( ITEM_DC_CREATOR );
        else if ( getTopElementInStack() == ITEM && localName.equalsIgnoreCase( "pubDate" ) )
            pushElement( ITEM_PUB_DATE );
        else {
            if ( currentlyInsideAnItemElement )
                checkForArticleThumbnail( attributes );
            pushElement( UNKNOWN );
        }
    }

    private void checkForArticleThumbnail( Attributes attributes ) {
        if ( attributes.getValue( "type" ) != null && attributes.getValue( "type" ).contains( "image" ) ) {
            var thumbnailUrl = attributes.getValue( "url" );
            if ( thumbnailUrl == null )
                thumbnailUrl = attributes.getValue( "href" );
            currentArticle.setThumbnailUrl( thumbnailUrl );
        }
    }

    @Override
    protected void processEndElement( String uri, String localName, String qName ) {
        log.info( "End element being processed: %s".formatted( localName ) );
        if ( localName.equalsIgnoreCase( "title" ) && getTopElementInStack() == TITLE)
            currentFeed.setTitle( getContent() );
        else if ( localName.equalsIgnoreCase( "description" ) && getTopElementInStack() == DESCRIPTION)
            currentFeed.setDescription( getContent() );
        else if ( localName.equalsIgnoreCase( "link" ) && getTopElementInStack() == LINK)
            currentFeed.setWebsiteUrl( getContent() );
        else if ( localName.equalsIgnoreCase( "language" ) && getTopElementInStack() == LANGUAGE)
            currentFeed.setLanguage( getContent() );
        else if ( localName.equalsIgnoreCase( "item" ) && getTopElementInStack() == ITEM ) {
            super.processCurrentArticle();
        }
        else if ( localName.equalsIgnoreCase( "title" ) && getTopElementInStack() == ITEM_TITLE)
            currentArticle.setTitle( getContent() );
        else if ( localName.equalsIgnoreCase( "guid" ) && getTopElementInStack() == ITEM_GUID)
            currentArticle.setGuid( getContent() );
        else if ( localName.equals( "link" ) && getTopElementInStack() == ITEM_LINK)
            currentArticle.setUrl( getContent() );
        else if ( localName.equalsIgnoreCase( "description" ) &&
                getTopElementInStack() == ITEM_DESCRIPTION)
            currentArticle.setDescription( getContent() );
        else if ( localName.equalsIgnoreCase( "creator" ) && getTopElementInStack() == ITEM_DC_CREATOR
                && uri.equalsIgnoreCase( URI_DC.getUri() ) )
            currentArticle.setCreator( getContent() );
        else if ( localName.equalsIgnoreCase( "pubDate" ) && getTopElementInStack() == ITEM_PUB_DATE)
            currentArticle.setPublicationDate( DateUtil.parseDateStringToZonedDateTime( getContent() ) );
        super.clearEndElementDetails();
    }

    @Override
    public FeedType getFeedType() {
        return FeedType.RSS;
    }
}
