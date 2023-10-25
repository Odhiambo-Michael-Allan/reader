package org.reader.feed;

import org.reader.constant.FeedType;
import org.reader.model.Feed;
import org.xml.sax.InputSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.GZIPInputStream;

public final class FeedReader {

    private final FeedParser feedParser = new FeedParser();
    private SAXParser saxParser;

    public FeedReader() throws Exception {
        initializeSaxParser();
    }

    private void initializeSaxParser() throws Exception {
        final var saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware( true );
        saxParserFactory.setFeature( "http://apache.org/xml/features/nonvalidating/load-external-dtd", false );
        saxParserFactory.setFeature( "http://apache.org/xml/features/continue-after-fatal-error", true );
        saxParser = saxParserFactory.newSAXParser();
    }

    public Feed readFeed( InputStream inputStream ) throws Exception {
        inputStream = unzipInputStream( inputStream );
        return parseInputStream( inputStream );
    }

    private InputStream unzipInputStream( InputStream inputStream ) throws Exception {
        final var pushbackInputStream = new PushbackInputStream( inputStream, 2 );
        if ( inputStreamIsGzipped( pushbackInputStream ) )
            return new GZIPInputStream( pushbackInputStream );
        return pushbackInputStream;
    }

    public boolean inputStreamIsGzipped( PushbackInputStream inputStream ) throws IOException {
        final var signature = new byte[ 2 ];
        inputStream.read( signature );
        inputStream.unread( signature );
        return signature[ 0 ] == ( byte ) GZIPInputStream.GZIP_MAGIC &&
                signature[ 1 ] == ( byte ) ( GZIPInputStream.GZIP_MAGIC >> 8 );
    }

    private Feed parseInputStream( InputStream inputStream ) throws Exception {
        try {
            final var reader = new XmlReader( inputStream, "UTF-8" );
            final var inputSource = new InputSource( reader );
            saxParser.parse( inputSource, feedParser );
        } catch ( InternalError e ) {
            throw new Exception( e );
        }
        return feedParser.getCurrentFeed();
    }

    public FeedType getCurrentFeedType() {
        return feedParser.getCurrentFeedType();
    }

}
