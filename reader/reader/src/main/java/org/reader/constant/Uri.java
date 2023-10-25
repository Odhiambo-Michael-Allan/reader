package org.reader.constant;

public enum Uri {

    URI_XML( "http://www.w3.org/XML/1998/namespace" ),
    URI_ATOM( "http://www.w3.org/2005/Atom" ),
    URI_RDF( "http://www.w3.org/1999/02/22-rdf-syntax-ns#" ),
    URI_SLASH( "http://purl.org/rss/1.0/modules/slash/" ),
    URI_DC( "http://purl.org/dc/elements/1.1/" ),
    URI_CONTENT( "http://purl.org/rss/1.0/modules/content/" ),
    URI_THREAD( "http://purl.org/syndication/thread/1.0" );

    private final String uri;

    private Uri( String uri ) {
        this.uri = uri;
    }

    public String getUri() {
        return this.uri;
    }

}
