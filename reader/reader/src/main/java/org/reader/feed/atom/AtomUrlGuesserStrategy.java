package org.reader.feed.atom;

import org.reader.model.AtomLink;

import java.util.List;

public class AtomUrlGuesserStrategy {

    /**
     * Guess the correct site URL from a set of links.
     */
    public String guessSiteUrl( List<AtomLink> atomLinks ) {
        if ( atomLinks == null || atomLinks.isEmpty() )
            return null;
        // Return alternate links first ( e.g. Blogspot )
        for ( AtomLink link : atomLinks )
            if ( link.rel() != null && link.rel().equalsIgnoreCase( "alternate" ) )
                return link.href();
        // Default: return the first valid link.
        for ( AtomLink link : atomLinks ) {
            if ( link.rel() == null )
                return link.href();
            else if ( !link.rel().equalsIgnoreCase( "self" ) )
                return link.href();
        }
        return null;
    }

}
