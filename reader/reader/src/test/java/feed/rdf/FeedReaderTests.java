package feed.rdf;

import org.junit.Before;
import org.junit.Test;
import org.reader.constant.FeedType;
import org.reader.feed.FeedReader;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class FeedReaderTests {

    private FeedReader feedReader;
    private InputStream oatmealInputStream, meisalamInputStream, lxerInputStream, autostripInputStream;

    @Before
    public void setup() throws Exception {
        feedReader = new FeedReader();
        oatmealInputStream = getClass().getResourceAsStream( "/feed/feed_rdf_oatmeal.xml" );
        meisalamInputStream = getClass().getResourceAsStream( "/feed/feed_rdf_meisalam.xml" );
        lxerInputStream = getClass().getResourceAsStream( "/feed/feed_rdf_lxer.xml" );
        autostripInputStream = getClass().getResourceAsStream( "/feed/feed_rdf_autostrip.xml" );
    }

    @Test
    public void readerCorrectlyIdentifiesRdfFeed() throws Exception {
        feedReader.readFeed( oatmealInputStream );
        assertEquals( FeedType.RDF, feedReader.getCurrentFeedType() );
    }

    @Test
    public void oatmeanRdfFeedTests() throws Exception {
        final var feed = feedReader.readFeed( oatmealInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        final var lastArticle = articles.get( articles.size() - 1 );
        assertEquals( "The Oatmeal - Comics, Quizzes, & Stories", feed.getTitle() );
        assertEquals( "http://theoatmeal.com/", feed.getWebsiteUrl() );
        assertEquals( "The oatmeal tastes better than stale skittles found under the couch cushions",
                feed.getDescription() );
        assertNull( feed.getLanguage() );
        assertEquals( 9, articles.size() );
        assertEquals( "What the World War Z movie has in common with the book",
                firstArticle.getTitle() );
        assertEquals( "I got to pet some bears last week", lastArticle.getTitle() );
        assertEquals( "http://theoatmeal.com/blog/bears", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "I got to pet, poke, and prod a couple of" +
                " sedated black bears last week." ) );
        assertEquals( "Matthew Inman", lastArticle.getCreator() );
        assertEquals( "http://theoatmeal.com/blog/bears", lastArticle.getGuid() );
        assertNotNull( lastArticle.getPublicationDate() );

        final var expectedLocalDateTime = LocalDateTime.of( 2013, 5, 28, 16,
                50, 0 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "+0100" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }

    @Test
    public void meisalamRdfFeedTests() throws Exception {
        final var feed = feedReader.readFeed( meisalamInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "琥珀色の小箱に恋をして:*:･･:*:･国際結婚生活日記:*:･･:*:･", feed.getTitle() );
        assertEquals( "http://meisalam.blog.fc2.com/", feed.getWebsiteUrl() );
        assertEquals( "ja", feed.getLanguage() );
        assertEquals( "琥珀色の瞳をした彼に恋をして結婚。日本でポカポカ生息中。異文化交流の新婚生活と彼の観察日記（笑）を気ままに綴ります。",
                feed.getDescription() );
        assertEquals( 5, articles.size() );
        assertEquals( "外免切替に挑戦！－④最終回", firstArticle.getTitle() );
        assertEquals( "http://meisalam.blog.fc2.com/blog-entry-105.html", firstArticle.getUrl() );
        assertEquals( "http://meisalam.blog.fc2.com/blog-entry-105.html", firstArticle.getGuid() );
        assertEquals( "meisa", firstArticle.getCreator() );
        assertTrue( firstArticle.getDescription().contains( "そして外免切替実技試験第二回を控えたある日。" ) );
        assertNotNull( firstArticle.getPublicationDate() );
    }

    @Test
    public void lxerRdfFeedTests() throws Exception {
        final var feed = feedReader.readFeed( lxerInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "LXer Linux News", feed.getTitle() );
        assertEquals( "http://lxer.com/", feed.getWebsiteUrl() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Linux and Open Source news headlines", feed.getDescription() );
        assertEquals( 20, articles.size() );
        assertEquals( "GCC vs. LLVM/Clang On The AMD Richland APU", firstArticle.getTitle() );
        assertEquals( "http://lxer.com/module/newswire/ext_link.php?rid=187870", firstArticle.getUrl() );
        assertEquals( "http://lxer.com/module/newswire/ext_link.php?rid=187870", firstArticle.getGuid() );
        assertEquals( "Michael Larabel", firstArticle.getCreator() );
        assertTrue( firstArticle.getDescription().contains( "Along with benchmarking the AMD A10-6800K" ) );
        assertNotNull( firstArticle.getPublicationDate() );
    }

    @Test
    public void autostripRdfFeedTests() throws Exception {
        final var feed = feedReader.readFeed( autostripInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "autostrip", feed.getTitle() );
        assertEquals( "http://autostrip.fr/index.php", feed.getWebsiteUrl() );
        assertEquals( "fr", feed.getLanguage() );
        assertTrue( feed.getDescription().isEmpty() );
        assertEquals( 10, articles.size() );
        assertEquals( "Bill, le cocker coquin...", firstArticle.getTitle() );
        assertEquals( "http://autostrip.fr/index.php?2013/06/09/214-bill-le-cocker-coquin",
                firstArticle.getUrl() );
        assertEquals( "http://autostrip.fr/index.php?2013/06/09/214-bill-le-cocker-coquin",
                firstArticle.getGuid() );
        assertEquals( "Tristan", firstArticle.getCreator() );
        assertTrue( firstArticle.getDescription().contains( "Ahahahahahahahah" ) );
        assertNotNull( firstArticle.getPublicationDate() );
    }
}
