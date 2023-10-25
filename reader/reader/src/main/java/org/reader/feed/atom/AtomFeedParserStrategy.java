package org.reader.feed.atom;

import org.apache.commons.lang3.StringUtils;
import org.reader.constant.FeedType;
import org.reader.constant.Uri;
import org.reader.feed.FeedParserStrategy;
import org.reader.model.AtomLink;
import org.reader.util.DateUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.reader.constant.FeedElement.*;
import static org.reader.constant.FeedType.*;

public class AtomFeedParserStrategy extends FeedParserStrategy {

    private static final Logger log = Logger.getLogger( AtomFeedParserStrategy.class.getSimpleName() );
    private final List<AtomLink> atomLinks = new ArrayList<>();
    private final List<AtomLink> atomArticleLinks = new ArrayList<>();

    @Override
    protected void processStartElement( String uri, String localName, String qName, Attributes attributes )
            throws SAXException {
        log.info( "Start element being processed: %s".formatted( localName ) );
        if ( localName.equalsIgnoreCase( ATOM.getName() ) ) {
            setFeedLanguageFrom( attributes );
            pushElement( FEED );
        }
        else if ( getTopElementInStack() == FEED && localName.equalsIgnoreCase( "title" ) )
            pushElement( ATOM_TITLE );
        else if ( getTopElementInStack() == FEED && localName.equalsIgnoreCase( "subtitle" )  )
            pushElement( ATOM_SUBTITLE );
        else if ( getTopElementInStack() == FEED && localName.equalsIgnoreCase( "link" ) ) {
            addAtomLinkFrom( attributes );
            pushElement( ATOM_LINK );
        }
        else if ( getTopElementInStack() == FEED && localName.equalsIgnoreCase( "entry" ) ) {
            atomArticleLinks.clear();
            setArticleBaseUriFrom( attributes );
            pushElement( ENTRY );
        }
        else if ( getTopElementInStack() == ENTRY && localName.equalsIgnoreCase( "title" ) )
            pushElement( ENTRY_TITLE );
        else if ( getTopElementInStack() == ENTRY && localName.equalsIgnoreCase( "link" ) ) {
            getEntryLinkFrom( attributes );
            pushElement( ENTRY_LINK );
        }
        else if ( getTopElementInStack() == ENTRY && localName.equalsIgnoreCase( "id" ) )
            pushElement( ENTRY_ID );
        else if ( getTopElementInStack() == ENTRY && localName.equalsIgnoreCase("author" ) )
            pushElement( ENTRY_AUTHOR );
        else if ( getTopElementInStack() == ENTRY_AUTHOR && localName.equalsIgnoreCase( "name" ) )
            pushElement( AUTHOR_NAME );
        else if ( getTopElementInStack() == ENTRY && localName.equalsIgnoreCase( "updated" ) )
            pushElement( ENTRY_UPDATED );
        else if ( getTopElementInStack() == ENTRY && localName.equalsIgnoreCase( "thumbnail" ) ) {
            currentArticle.setThumbnailUrl( attributes.getValue("url") );
            pushElement( ENTRY_THUMBNAIL );
        }
        else if ( getTopElementInStack() == ENTRY && localName.equalsIgnoreCase( "content" ) )
            pushElement( ENTRY_CONTENT );
        else if ( getTopElementInStack() == ENTRY && localName.equalsIgnoreCase( "summary" ) )
            pushElement( ENTRY_SUMMARY );

//            final var xmlBase = StringUtils.trimToNull( attributes.getValue( Uri.URI_XML.getUri(), "base" ) );
//            if ( xmlBase != null )
//                currentArticle.setBaseUri( xmlBase );  // Overrides entry's xml:base..
//        }
        else
            pushElement( UNKNOWN );
    }

    private void setFeedLanguageFrom( Attributes attributes ) {
        for ( int i = 0; i < attributes.getLength(); i++ ) {
            if ( attributes.getLocalName( i ).equalsIgnoreCase( "lang" ) )
                currentFeed.setLanguage( attributes.getValue( i ) );
        }
    }

    private void addAtomLinkFrom( Attributes attributes ) {
        final var rel = attributes.getValue( "rel" );
        final var type = attributes.getValue( "type" );
        final var href = attributes.getValue( "href" );
        atomLinks.add( new AtomLink( rel, type, href ) );
    }

    private void setArticleBaseUriFrom( Attributes attributes ) {
        final var xmlBase = StringUtils.trimToNull( attributes.getValue( Uri.URI_XML.getUri(), "base" ) );
        if ( xmlBase != null ) {
            atomArticleLinks.add( new AtomLink( null, null, xmlBase ) );
            currentArticle.setBaseUri( xmlBase );
        }
    }

    private void getEntryLinkFrom( Attributes attributes ) {
        final var rel = StringUtils.trimToNull( attributes.getValue( "rel" ) );
        final var type = StringUtils.trimToNull( attributes.getValue( "type" ) );
        final var href = StringUtils.trimToNull( attributes.getValue( "href" ) );
        if ( href != null )
            atomArticleLinks.add( new AtomLink( rel, type, href ) );
    }

    @Override
    protected void processEndElement( String uri, String localName, String qName ) throws SAXException {
        log.info( "End element being processed: %s".formatted( localName ) );
        if ( getTopElementInStack() == ATOM_TITLE && localName.equalsIgnoreCase( "title" ) )
            currentFeed.setTitle( getContent() );
        else if ( getTopElementInStack() == ATOM_SUBTITLE && localName.equalsIgnoreCase( "subtitle" ) )
            currentFeed.setDescription( getContent() );
        else if ( getTopElementInStack() == FEED && localName.equalsIgnoreCase( "feed" ) )
            currentFeed.setWebsiteUrl( new AtomUrlGuesserStrategy().guessSiteUrl( atomLinks ) );
        else if ( getTopElementInStack() == ENTRY && localName.equalsIgnoreCase( "entry" ) ) {
            setArticleUrl();
            super.processCurrentArticle();
        }
        else if ( getTopElementInStack() == ENTRY_TITLE && localName.equalsIgnoreCase( "title" ) )
            currentArticle.setTitle( getContent() );
        else if ( getTopElementInStack() == ENTRY_ID && localName.equalsIgnoreCase( "id" ) )
            currentArticle.setGuid( getContent() );
        else if ( getTopElementInStack() == AUTHOR_NAME && localName.equalsIgnoreCase( "name" ) )
            currentArticle.setCreator( getContent() );
        else if ( getTopElementInStack() == ENTRY_UPDATED && localName.equalsIgnoreCase( "updated" ) )
            currentArticle.setPublicationDate( DateUtil.parseDateStringToZonedDateTime( getContent() ) );
        else if ( getTopElementInStack() == ENTRY_CONTENT && localName.equalsIgnoreCase( "content" ) )
            currentArticle.setDescription( getContent() );
        else if ( getTopElementInStack() == ENTRY_SUMMARY && localName.equalsIgnoreCase( "summary" ) ) {
            if ( currentArticle.getDescription() == null )
                currentArticle.setDescription( getContent() );
        }


        super.clearEndElementDetails();
    }

    private void setArticleUrl() {
        final var url = new AtomArticleUrlGuesserStrategy().guess( atomArticleLinks );
        currentArticle.setUrl( url );
    }

    @Override
    public FeedType getFeedType() {
        return ATOM;
    }
}
