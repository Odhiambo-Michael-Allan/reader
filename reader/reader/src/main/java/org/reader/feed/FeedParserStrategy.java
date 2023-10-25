package org.reader.feed;

import org.apache.commons.lang3.StringUtils;
import org.reader.constant.FeedElement;
import org.reader.constant.FeedType;
import org.reader.model.Article;
import org.reader.model.Feed;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Stack;
import java.util.logging.Logger;

public abstract class FeedParserStrategy {

    private static final Logger log = Logger.getLogger( FeedParserStrategy.class.getSimpleName() );
    protected Feed currentFeed = new Feed();
    protected Article currentArticle = new Article();
    protected String contentOfCurrentElement;
    protected boolean currentlyInsideAnItemElement;
    private final Stack<FeedElement> elementStack = new Stack<>();

    protected abstract void processStartElement( String uri, String localName, String qName, Attributes attributes )
            throws SAXException;
    protected abstract void processEndElement( String uri,String localName, String qName ) throws SAXException;

    public void processCharacters( char[] chars, int start, int length ) {
        final var newContent = new String( chars, start, length );
        contentOfCurrentElement = contentOfCurrentElement == null ? newContent : contentOfCurrentElement + newContent;
    }

    protected void pushElement( FeedElement newElement ) {
        elementStack.push( newElement );
        if ( newElement == FeedElement.ITEM )
            currentlyInsideAnItemElement = true;
        log.info( ">> %s\n".formatted( newElement ) );
    }

    protected void popElement() {
        if ( !elementStack.isEmpty() ) {
            FeedElement topElement = elementStack.pop();
            log.info( "<< %s\n".formatted( topElement ) );
            if ( topElement == FeedElement.ITEM)
                currentlyInsideAnItemElement = false;
        }
    }

    protected String getContent() {
        final var content = StringUtils.trim( this.contentOfCurrentElement );
        this.contentOfCurrentElement = null;
        return content;
    }

    protected FeedElement getTopElementInStack() {
        if ( elementStack.isEmpty() )
            return null;
        return elementStack.peek();
    }

    protected void processCurrentArticle() {
        currentFeed.addArticle( currentArticle );
        currentArticle = new Article();
    }

    protected void clearEndElementDetails() {
        contentOfCurrentElement = null;
        popElement();
    }

    public Feed getCurrentFeed() {
        return currentFeed;
    }
    public abstract FeedType getFeedType();
}
