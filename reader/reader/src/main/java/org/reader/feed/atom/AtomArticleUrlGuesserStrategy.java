package org.reader.feed.atom;

import org.reader.model.AtomLink;

import java.util.List;

public class AtomArticleUrlGuesserStrategy {

    /**
     * Guess the correct article URL from a set of links.
     */
    public String guess( List<AtomLink> atomLinks ) {
        if ( atomLinks == null || atomLinks.isEmpty() )
            return null;
        // 1st try: link from the <item> element
        for ( AtomLink atomLink : atomLinks )
            if ( atomLink.rel() == null || atomLink.type() == null )
                return atomLink.href();

        // 2nd try: link from <alternate> element
        for ( AtomLink atomLink : atomLinks )
            if ( atomLink.rel().equals( "alternate" ) && atomLink.type().equals( "text/html" ) )
                return atomLink.href();
        // Default: return the first link
        return atomLinks.get( 0 ).href();
    }
}
