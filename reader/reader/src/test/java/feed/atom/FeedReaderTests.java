package feed.atom;

import org.junit.Before;
import org.junit.Test;
import org.reader.constant.FeedType;
import org.reader.feed.FeedReader;

import java.io.InputStream;

import static org.junit.Assert.*;

public class FeedReaderTests {

    private FeedReader feedReader;
    private InputStream akeweaInputStream, githubUserInputStream, haverbekeInputStream, makikoInputStream
            , ploumInputStream, whatifInputStream, xkcdInputStream;

    @Before
    public void setup() throws Exception {
        feedReader = new FeedReader();
        akeweaInputStream = getClass().getResourceAsStream( "/feed/feed_atom_akewea.xml" );
        githubUserInputStream = getClass().getResourceAsStream( "/feed/feed_atom_github_user.xml" );
        haverbekeInputStream = getClass().getResourceAsStream( "/feed/feed_atom_haverbeke.xml" );
        makikoInputStream = getClass().getResourceAsStream( "/feed/feed_atom_makiko.xml" );
        ploumInputStream = getClass().getResourceAsStream( "/feed/feed_atom_ploum.xml" );
        whatifInputStream = getClass().getResourceAsStream( "/feed/feed_atom_whatif.xml" );
        xkcdInputStream = getClass().getResourceAsStream( "/feed/feed_atom_xkcd.xml" );
    }

    @Test
    public void readerCorrectlyIdentifiesAtomFeedType() throws Exception {
        final var feed = feedReader.readFeed( akeweaInputStream );
        assertEquals( FeedType.ATOM, feedReader.getCurrentFeedType() );
    }

    @Test
    public void akeweaAtomFeedTests() throws Exception {
        final var feed = feedReader.readFeed( akeweaInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "Du son, des idées et des boulons...", feed.getTitle() );
        assertTrue( feed.getDescription().isEmpty() );
        assertEquals( "http://blog.akewea.com/", feed.getWebsiteUrl() );
        assertEquals( "fr", feed.getLanguage() );
        assertEquals( 20, articles.size() );
        assertEquals( "Hellephant", firstArticle.getTitle() );
        assertEquals( "http://blog.akewea.com/post/2013/03/17/Hellephant", firstArticle.getUrl() );
        assertEquals( "urn:md5:7ec447bbd2a59949ebe0946144726aa2", firstArticle.getGuid() );
        assertNull( firstArticle.getThumbnailUrl() );;
        assertEquals( "Akewea", firstArticle.getCreator() );
        assertNotNull( firstArticle.getPublicationDate() );
    }

    @Test
    public void githubUserAtomFeedTests() throws Exception {
        final var feed = feedReader.readFeed( githubUserInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "naku’s Activity", feed.getTitle() );
        assertNull( feed.getDescription() );
        assertEquals( "https://github.com/naku", feed.getWebsiteUrl() );
        assertEquals( "en-US", feed.getLanguage() );
        assertEquals( 7, articles.size() );
        assertEquals( "naku pushed to master at sismics/reader", firstArticle.getTitle() );
        assertEquals( "https://github.com/sismics/reader/compare/3126b91465...b7414b12d8",
                firstArticle.getUrl() );
        assertTrue( firstArticle.getDescription().contains( "News feed, event click, Event click " +
                "type:PushEvent target:actor" ) );
        assertEquals( "tag:github.com,2008:PushEvent/3379219227", firstArticle.getGuid() );
        assertEquals( "https://avatars1.githubusercontent.com/u/1281790?v=3&s=30",
                firstArticle.getThumbnailUrl() );
        assertEquals( "naku", firstArticle.getCreator() );
        assertNotNull( firstArticle.getPublicationDate() );
    }

    @Test
    public void haverbekeAtomFeedTests() throws Exception {
        final var feed = feedReader.readFeed( haverbekeInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "marijnhaverbeke.nl/blog", feed.getTitle() );
        assertNull( feed.getDescription() );
        assertNull( feed.getWebsiteUrl() );
        assertNull( feed.getLanguage() );
        assertEquals( 26, articles.size() );
        assertEquals( "Tern", firstArticle.getTitle() );
        assertEquals( "http://marijnhaverbeke.nl/blog/tern.html", firstArticle.getUrl() );
        assertNotNull( firstArticle.getDescription() );
        // TODO SAX Parser ignore content if not wrapped in CDATA
//        assertTrue( firstArticle.getDescription().contains( "I spend a rather large fraction of my " +
//                "days inside Emacs, writing and" ) );
        assertEquals( "http://marijnhaverbeke.nl/blog/tern.html", firstArticle.getGuid() );
        assertNull( firstArticle.getThumbnailUrl() );
        assertNull( firstArticle.getCreator() );
        assertNotNull( firstArticle.getPublicationDate() );
    }

    @Test
    public void makikoAtomFeedTests() throws Exception {
        final var feed = feedReader.readFeed( makikoInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "Makiko Furuichi Blog", feed.getTitle() );
        assertEquals( "こんにちは 古市牧子です。制作や留学のお話･日常その他など。絵画･映像作品制作しています。",
                feed.getDescription() );
        assertEquals( "http://makiko-f.blogspot.com/", feed.getWebsiteUrl() );
        assertNull( feed.getLanguage() );
        assertEquals( 25, articles.size() );
        assertEquals( "くいだおれ", firstArticle.getTitle() );
        assertEquals( "http://makiko-f.blogspot.com/2013/04/blog-post.html", firstArticle.getUrl() );
        assertNotNull( firstArticle.getDescription() );
        assertTrue( firstArticle.getDescription().contains( "金沢の郷土料理も食べられてとても満足させて頂きました。" ) );
        assertEquals( "tag:blogger.com,1999:blog-9184161806327478331.post-186540250833288646",
                firstArticle.getGuid() );
        assertEquals( "http://3.bp.blogspot.com/-4N8tKME_WLQ/UVjszW2k7MI/AAAAAAAALjk/" +
                        "TfVqwMOIMdY/s72-c/P1400032.JPG", firstArticle.getThumbnailUrl() );
        assertEquals( "Makiko Furuichi", firstArticle.getCreator() );
        assertNotNull( firstArticle.getPublicationDate() );
    }

    @Test
    public void ploumAtomFeedTests() throws Exception {
        final var feed = feedReader.readFeed( ploumInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "ploum.net", feed.getTitle() );
        assertEquals( "Le blog de Lionel Dricot", feed.getDescription() );
        assertEquals( "http://ploum.net", feed.getWebsiteUrl() );
        assertEquals( "en-US", feed.getLanguage() );
        assertEquals( 10, articles.size() );
        assertEquals( "Ce blog est payant", firstArticle.getTitle() );
        assertEquals( "http://ploum.net/post/ce-blog-est-payant", firstArticle.getUrl() );
        assertEquals( "http://ploum.net/?p=3030", firstArticle.getGuid() );
        assertEquals( "Lionel Dricot", firstArticle.getCreator() );
        assertTrue( firstArticle.getDescription().contains( "Voilà, ce blog est désormais" +
                " officiellement un blog payant" ) );
        assertNull( firstArticle.getThumbnailUrl() );
        assertNotNull( firstArticle.getPublicationDate() );
    }

    @Test
    public void whatifAtomFeedTests() throws Exception {
        final var feed = feedReader.readFeed( whatifInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "What If?", feed.getTitle() );
        assertNull( feed.getDescription() );
        assertEquals( "http://what-if.xkcd.com", feed.getWebsiteUrl() );
        assertNull( feed.getLanguage() );
        assertEquals( 3, articles.size() );
        assertEquals( "Pressure Cooker", firstArticle.getTitle() );
        assertEquals( "http://what-if.xkcd.com/40/", firstArticle.getUrl() );
        assertEquals( "http://what-if.xkcd.com/40/", firstArticle.getGuid() );
        assertTrue( firstArticle.getDescription().contains( "What's the worst thing that can happen if " +
                "you misuse a pressure cooker" ) );
        assertNull( firstArticle.getCreator() );
        assertNull( firstArticle.getThumbnailUrl() );
        assertNotNull( firstArticle.getPublicationDate() );
    }

    @Test
    public void xkcdAtomFeedTests() throws Exception {
        final var feed = feedReader.readFeed( xkcdInputStream );
        final var articles = feed.getArticles();
        final var firstArticle = articles.get( 0 );
        assertEquals( "xkcd.com", feed.getTitle() );
        assertEquals( "http://xkcd.com/", feed.getWebsiteUrl() );
        assertNull( feed.getDescription() );
        assertEquals( "en", feed.getLanguage() );
        assertEquals( 4, articles.size() );
        assertEquals( "Voyager 1", firstArticle.getTitle() );
        assertEquals( "http://xkcd.com/1189/", firstArticle.getUrl() );
        assertTrue( firstArticle.getDescription().contains( "the heliopause twice, and once each through" +
                " the heliosheath, heliosphere" ) );
        assertEquals( "http://xkcd.com/1189/", firstArticle.getGuid() );
        assertNull( firstArticle.getThumbnailUrl() );
        assertNull( firstArticle.getCreator() );
        assertNotNull( firstArticle.getPublicationDate() );
    }

}
