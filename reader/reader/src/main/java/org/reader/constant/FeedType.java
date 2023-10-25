package org.reader.constant;

public enum FeedType {

    RSS( "rss" ), ATOM( "feed" ), RDF( "rdf" );

    private final String name;
    private FeedType( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
