package feed;


import org.reader.feed.XmlReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class XmlReaderTests {

    private final String defaultEncoding = "UTF-8";
    private XmlReader xmlReader;
    private InputStream gizmodoZippedInputStream, lemessagerInputStream, bbcInputStream, nationInputStream,
            standardInputStream, spaceInputStream, standardWorldInputStream, standardSportsInputStream,
            standardKenyaInputStream, xkcdInputStream, spaceDailyInputStream, slashdotInputStream, slackwareInputStream,
            rottenTomatoesInputStream, ploumInputStream, novaInputStream, nasaInputStream, malikiInputStream,
            korbenInputStream, iboxInputStream, fubizInputStream, esaInputStream, distractionware2InputStream,
            distractionwareInputStream, dilbertInputStream, developpezInputStream, developerworksInputStream,
            cultizInputStream, bysmeInputStream, apodInputStream;

    @Before
    public void setup() {
        gizmodoZippedInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_gizmodo.gzip" );
        lemessagerInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_lemessager.xml" );
        bbcInputStream = getClass().getResourceAsStream( "/feed/bbc.xml" );
        nationInputStream = getClass().getResourceAsStream( "/feed/nation.xml" );
        standardInputStream = getClass().getResourceAsStream( "/feed/standard.xml" );
        spaceInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_space.xml" );
        standardWorldInputStream = getClass().getResourceAsStream( "/feed/standard-world.xml" );
        standardSportsInputStream = getClass().getResourceAsStream( "/feed/standard-sports.xml" );
        standardKenyaInputStream = getClass().getResourceAsStream( "/feed/standard-kenya.xml" );
        xkcdInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_xkcd.xml" );
        spaceDailyInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_spacedaily.xml" );
        slashdotInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_slashdot.xml" );
        slackwareInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_slackware.xml" );
        rottenTomatoesInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_rottentomatoes.xml" );
        ploumInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_ploum.xml" );
        novaInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_nova.xml" );
        nasaInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_nasa.xml" );
        malikiInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_maliki.xml" );
        korbenInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_korben.xml" );
        iboxInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_ibox.xml" );
        fubizInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_fubiz.xml" );
        esaInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_esa.xml" );
        distractionware2InputStream = getClass().getResourceAsStream( "/feed/feed_rss2_distractionware2.xml" );
        distractionwareInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_distractionware.xml" );
        dilbertInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_dilbert.xml" );
        developpezInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_developpez.xml" );
        developerworksInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_developerworks.xml" );
        cultizInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_cultiz.xml" );
        bysmeInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_bysme.xml" );
        apodInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_apod.xml" );
    }

    @Test
    public void gizmodoInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( gizmodoZippedInputStream, "UTF-8" );
    }

    @Test
    public void lemessagerInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( lemessagerInputStream, "ISO-8859-1" );
    }

    @Test
    public void bbcInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( bbcInputStream, "UTF-8" );
    }

    @Test
    public void nationInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( nationInputStream, "UTF-8" );
    }

    @Test
    public void standardInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( standardInputStream, "UTF-8" );
    }

    @Test
    public void spaceInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( spaceInputStream, "UTF-8" );
    }

    @Test
    public void standardWorldInputStreamEncodingIsCorrectlyIdentified()  throws Exception {
        verifyEncoding( standardWorldInputStream, "UTF-8" );
    }

    @Test
    public void standardSportsInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( standardSportsInputStream, "UTF-8" );
    }

    @Test
    public void standardKenyaInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( standardKenyaInputStream, "UTF-8" );
    }

    @Test
    public void xkcdInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( xkcdInputStream, "UTF-8" );
    }

    @Test
    public void spaceDailyInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( spaceDailyInputStream, "UTF-8" );
    }

    @Test
    public void slashdotInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( slashdotInputStream, "UTF-8" );
    }

    @Test
    public void slackwareInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( slackwareInputStream, "ISO-8859-1" );
    }

    @Test
    public void rottenTomatoesInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( rottenTomatoesInputStream, "ISO-8859-1" );
    }

    @Test
    public void ploumInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( ploumInputStream, "UTF-8" );
    }

    @Test
    public void novaInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( novaInputStream, "UTF-8" );
    }

    @Test
    public void nasaInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( nasaInputStream, "UTF-8" );
    }

    @Test
    public void malikiInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( malikiInputStream, "ISO-8859-1" );
    }

    @Test
    public void korbenInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( korbenInputStream, "UTF-8" );
    }

    @Test
    public void iboxInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( iboxInputStream, "WINDOWS-1251" );
    }

    @Test
    public void fubixInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( fubizInputStream, "UTF-8" );
    }

    @Test
    public void esaInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( esaInputStream, "UTF-8" );
    }

    @Test
    public void distractionware2InputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( distractionware2InputStream, "UTF-8" );
    }

    @Test
    public void distractionwareInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( distractionwareInputStream, "UTF-8" );
    }

    @Test
    public void dilbertInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( dilbertInputStream, "UTF-8" );
    }

    @Test
    public void developpezEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( developpezInputStream, "UTF-8" );
    }

    @Test
    public void developerworksInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( developerworksInputStream, "UTF-8" );
    }

    @Test
    public void cultizInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( cultizInputStream, "UTF-8" );
    }

    @Test
    public void bysmeInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( bysmeInputStream, "UTF-8" );
    }

    @Test
    public void apodInputStreamEncodingIsCorrectlyIdentified() throws Exception {
        verifyEncoding( apodInputStream, "UTF-8" );
    }

    private void verifyEncoding( InputStream inputStream, String expectedEncoding ) throws Exception {
        xmlReader = new XmlReader( inputStream, defaultEncoding );
        Assert.assertEquals( expectedEncoding, xmlReader.determineEncodingForInputStream() );
    }

    @Test
    public void inputStreamIsRestoredToItsOriginalStateAfterEncodingHasBeenChecked() throws Exception {
        List<InputStream> inputStreamList = List.of( gizmodoZippedInputStream, bbcInputStream, nationInputStream,
                standardInputStream, spaceInputStream, standardWorldInputStream, standardSportsInputStream,
                standardKenyaInputStream, xkcdInputStream );
        for ( InputStream inputStream : inputStreamList )
            verifyInputStreamIsRestoredToItsOriginalState( inputStream, "UTF-8" );
    }

    private void verifyInputStreamIsRestoredToItsOriginalState( InputStream inputStream, String expectedEncoding )
            throws Exception {
        xmlReader = new XmlReader( inputStream, defaultEncoding );
        for ( int i = 0; i < 10; i++ )
            Assert.assertEquals( expectedEncoding, xmlReader.determineEncodingForInputStream() );
    }
}
