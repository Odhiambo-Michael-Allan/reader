import org.junit.Assert;
import org.junit.Test;

import java.io.PushbackInputStream;

public class PushbackInputStreamTests {

    @Test
    public void testPushbackInputStream() throws Exception {
        final var inputStream = new PushbackInputStream( getClass().getResourceAsStream(
                "/feed/feed_rss2_developpez.xml" ), 128 );
        final var header1 = new byte[ 128 ];
        final var header2 = new byte[ 128 ];
        inputStream.read( header1, 0, header1.length );
        String content = new String( header1 );
        inputStream.unread( header1 );
        inputStream.read( header2, 0, header2.length );
        inputStream.unread( header2 );
        Assert.assertEquals( content, new String( header2 ) );
        System.out.println( content );
        System.out.println( new String( header2 ) );
    }
}
