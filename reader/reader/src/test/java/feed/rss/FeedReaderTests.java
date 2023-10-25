package feed.rss;

import org.reader.constant.FeedType;
import org.reader.model.Article;
import org.reader.feed.FeedReader;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.time.*;
import java.util.List;

import static org.junit.Assert.*;

public class FeedReaderTests {

    private InputStream gizmodoZippedInputStream, lemessagerInputStream, bbcInputStream, nationInputStream,
            standardInputStream, spaceInputStream, standardWorldInputStream, standardSportsInputStream,
            standardKenyaInputStream, spaceDailyInputStream, slashdotInputStream, slackwareInputStream,
            rottenTomatoesInputStream, ploumInputStream, novaInputStream, nasaInputStream, malikiInputStream,
            korbenInputStream, iboxInputStream, fubizInputStream, esaInputStream,
            distractionwareInputStream, dilbertInputStream, developerworksInputStream,
            cultizInputStream, bysmeInputStream, apodInputStream;
    private FeedReader feedReader;

    @Before
    public void setup() throws Exception {
        gizmodoZippedInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_gizmodo.gzip" );
        lemessagerInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_lemessager.xml" );
        bbcInputStream = getClass().getResourceAsStream( "/feed/bbc.xml" );
        nationInputStream = getClass().getResourceAsStream( "/feed/nation.xml" );
        standardInputStream = getClass().getResourceAsStream( "/feed/standard.xml" );
        spaceInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_space.xml" );
        standardWorldInputStream = getClass().getResourceAsStream( "/feed/standard-world.xml" );
        standardSportsInputStream = getClass().getResourceAsStream( "/feed/standard-sports.xml" );
        standardKenyaInputStream = getClass().getResourceAsStream( "/feed/standard-kenya.xml" );
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
        distractionwareInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_distractionware.xml" );
        dilbertInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_dilbert.xml" );
        developerworksInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_developerworks.xml" );
        cultizInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_cultiz.xml" );
        bysmeInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_bysme.xml" );
        apodInputStream = getClass().getResourceAsStream( "/feed/feed_rss2_apod.xml" );
        feedReader = new FeedReader();
    }

    @Test
    public void readerDetectsGzippedFeedCorrectly() throws Exception {
        assertTrue( feedReader.inputStreamIsGzipped( new PushbackInputStream( gizmodoZippedInputStream, 2 ) ) );
    }

    @Test
    public void readerDetectsOrdinaryFeedCorrectly() throws Exception {
        assertFalse( feedReader.inputStreamIsGzipped( new PushbackInputStream( lemessagerInputStream, 2 ) ) );
    }

    @Test
    public void readerCorrectlyIdentifiesRssFeedType() throws Exception {
        feedReader.readFeed( lemessagerInputStream );
        assertEquals( FeedType.RSS, feedReader.getCurrentFeedType() );
    }

    @Test
    public void gizmodoZippedRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( gizmodoZippedInputStream );
        assertEquals( "Gizmodo", feed.getTitle() );
        assertEquals( "http://gizmodo.com", feed.getWebsiteUrl() );
        assertEquals( "en", feed.getLanguage() );
        assertEquals( "The Gadget Guide", feed.getDescription() );
        List<Article> articleList = feed.getArticles();
        assertEquals( 25, articleList.size() );
        Article article = articleList.get( 0 );
        assertEquals( "IKEA Uses a Staggering One Percent of the World's Wood", article.getTitle() );
        assertEquals( "http://gizmodo.com/ikea-uses-a-staggering-one-percent-of-the-worlds-wood-677540490",
                article.getUrl() );
        assertEquals( "677540490", article.getGuid() );
        assertNotNull( article.getCreator() );
        assertTrue( article.getDescription().contains( "Plenty of critics would argue that IKEA is unnecessarily" ) );
        assertNotNull( article.getPublicationDate() );
    }

    @Test
    public void lemessagerRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( lemessagerInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Genevois : Le Messager", feed.getTitle() );
        assertEquals( "http://www.lemessager.fr", feed.getWebsiteUrl() );
        assertEquals( "Le Messager actualités", feed.getDescription() );
        assertEquals( "fr", feed.getLanguage() );
        assertEquals( "Cyclisme : tout roule pour Maeva Paret-Peintre",
                firstArticle.getTitle() );
        assertEquals( "Le 50e collège du département sera construit entre Annemasse et la basse vallée " +
                "de l´Arve", lastArticle.getTitle() );
        assertEquals( "http://www.lemessager.fr/Actualite/Genevois/2013/12/13/article_le_50e_college_du_" +
                        "departement_sera_const.shtml", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "ral de la Haute-Savoie qui en a la comp" ) );
        assertEquals( "gps://story/1789307", lastArticle.getGuid() );
        assertEquals( "http://www.lemessager.fr//mediastore/VDN/A2013/M12/1242979-le-50e-col-52a04905.jpg.jpg",
                lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2013, 12, 12, 14,
                00, 00 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "+0200" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void bbcRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( bbcInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "BBC News - Home", feed.getTitle() );
        assertEquals( "https://www.bbc.co.uk/news/", feed.getWebsiteUrl() );
        assertEquals( "BBC News - Home", feed.getDescription() );
        assertEquals( "en-gb", feed.getLanguage() );
        assertEquals( "Millions of public sector workers to get pay rise",
                firstArticle.getTitle() );
        assertEquals( "How to get a job: Six expert tips for finding work",
                lastArticle.getTitle() );
        assertEquals( "https://www.bbc.co.uk/news/business-64939070?at_medium=RSS&at_campaign=KARANGA",
                lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "There are 10m people out of work in the UK, " +
                "so if you're searching for a job" ) );
        assertEquals( "https://www.bbc.co.uk/news/business-64939070", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2023, 3, 14, 14,
                29, 30 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "GMT" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void nationRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( nationInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "| Nation", feed.getTitle() );
        assertEquals( "https://nation.africa", feed.getWebsiteUrl() );
        assertEquals( "Nation.Africa brings the Latest News from Kenya, Africa and the World. Get live news" +
                " and latest stories from Politics, Business, Technology, Sports and more.", feed.getDescription() );
        assertEquals( "en", feed.getLanguage() );
        assertEquals( "Maandamano aftermath: Quickmart, Eastmatt owners count their losses",
                firstArticle.getTitle() );
        assertEquals( "CS Kindiki blames DPP, Judiciary for Shakahola killings",
                lastArticle.getTitle() );
        assertEquals( "https://nation.africa/kenya/news/cs-kindiki-blames-dpp-judiciary-for-" +
                "shakahola-killings-4300466", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "Interior CS accuses DPP of failing to keep " +
                "Mackenzie behind bars" ) );
        assertEquals( "https://nation.africa/kenya/news/cs-kindiki-blames-dpp-judiciary-for-" +
                "shakahola-killings-4300466", lastArticle.getGuid() );
        assertEquals( "https://nation.africa/resource/image/4300482/landscape_ratio16x9/1600/900/7476952c2" +
                "ea84a5e894b7516017ca117/eC/kindiki.jpg", lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2023, 7, 12, 5,
                18, 28 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "+0300" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void standardRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( standardInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "The Standard News Feeds", feed.getTitle() );
        assertEquals( "https://www.standardmedia.co.ke/rss/headlines.php", feed.getWebsiteUrl() );
        assertEquals( "Kenya's Bold Newspaper", feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "State's most expensive call: Man loses case on Sh66m tender offered via phone",
                firstArticle.getTitle() );
        assertEquals( "Azimio's strategy to make Kalonzo ready for a duel with Ruto in 2027",
                lastArticle.getTitle() );
        assertEquals( "https://www.standardmedia.co.ke/article/2001473815/azimio-s-strategy-to-make-" +
                "kalonzo-ready-for-a-duel-with-ruto-in-2027", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "Azimios strategy is to ripen Kalonzo by " +
                "increasing his prominence in a bid" ) );
        assertEquals( "https://www.standardmedia.co.ke/article/2001473815/azimio-s-strategy-to-make-" +
                "kalonzo-ready-for-a-duel-with-ruto-in-2027", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "Standard Digital", lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2023, 5, 27, 20,
                30, 00 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "+0300" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void spaceRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( spaceInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "SPACE.com", feed.getTitle() );
        assertEquals( "http://www.space.com/", feed.getWebsiteUrl() );
        assertEquals( "Something amazing every day.", feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Collision Course? A Comet Heads for Mars",
                firstArticle.getTitle() );
        assertEquals( "LIVE Webcast at 7 pm ET Tonight: See Comet ISON from Slooh Telescope",
                lastArticle.getTitle() );
        assertEquals( "http://www.space.com/19195-night-sky-planets-asteroids-webcasts.html",
                lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "The online Slooh Space Camera will provide" +
                " free live views of Comet ISON" ) );
        assertNull( lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2013, 1, 9, 10,
                52, 05 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "America/New_York" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void standardWorldRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( standardWorldInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "The Standard World News", feed.getTitle() );
        assertEquals( "https://www.standardmedia.co.ke/rss/world.php", feed.getWebsiteUrl() );
        assertEquals( "Kenya's Bold Newspaper", feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Biden welcomes Sweden's acceptance into NATO as Summit begins",
                firstArticle.getTitle() );
        assertEquals( "Former Pakistan PM Imran Khan freed on bail",
                lastArticle.getTitle() );
        assertEquals( "https://www.standardmedia.co.ke/world/article/2001472764/former-pakistan-pm-imran" +
                "-khan-freed-on-bail", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "The court said Khan could not be arrested for" +
                " the time being" ) );
        assertEquals( "https://www.standardmedia.co.ke/world/article/2001472764/former-pakistan-pm-" +
                "imran-khan-freed-on-bail", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "Standard Digital", lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2023, 5, 12, 16,
                45, 26 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "+0300" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void standardSportsRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( standardSportsInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "The Standard Sports", feed.getTitle() );
        assertEquals( "https://www.standardmedia.co.ke/rss/sports.php", feed.getWebsiteUrl() );
        assertEquals( "Kenya's Bold Newspaper", feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Top European court favors South Africa's Caster Semenya",
                firstArticle.getTitle() );
        assertEquals( "Kisumu, Nondescripts join the big boys in Driftwood cup quarters",
                lastArticle.getTitle() );
        assertEquals( "https://www.standardmedia.co.ke/sports/article/2001476864/kisumu-nondescripts-join-the" +
                "-big-boys-in-driftwood-cup-quarters", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "Homeboyz, Nakuru are out of the Driftwood leg." ) );
        assertEquals( "https://www.standardmedia.co.ke/sports/article/2001476864/kisumu-nondescripts-" +
                "join-the-big-boys-in-driftwood-cup-quarters", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "Standard Digital", lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2023, 7, 9, 06,
                00, 00 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "+0300" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void standardKenyaRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( standardKenyaInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "The Standard Kenya", feed.getTitle() );
        assertEquals( "https://www.standardmedia.co.ke/rss/kenya.php", feed.getWebsiteUrl() );
        assertEquals( "Kenya's Bold Newspaper", feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Elderly woman in Kisii demonstrations is mentally ill, family confirms",
                firstArticle.getTitle() );
        assertEquals( "EALA MP Sankok on fire over 'Lovers Nest' sexist remark",
                lastArticle.getTitle() );
        assertEquals( "https://www.standardmedia.co.ke/article/2001475335/eala-mp-sankok-" +
                        "on-fire-over-lovers-nest-sexist-remark",
                lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "The women from different groups in the country are" ) );
        assertEquals( "https://www.standardmedia.co.ke/article/2001475335/eala-mp-sankok-" +
                "on-fire-over-lovers-nest-sexist-remark", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "Standard Digital", lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2023, 6, 16, 18,
                53, 48 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "+0300" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void spaceDailyRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( spaceDailyInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Space News From SpaceDaily.Com", feed.getTitle() );
        assertEquals( "http://www.spacedaily.com/index.html", feed.getWebsiteUrl() );
        assertNull( feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Collision Course? A Comet Heads for Mars",
                firstArticle.getTitle() );
        assertEquals( "BusinessCom Networks Connects Mars 2013",
                lastArticle.getTitle() );
        assertEquals( "http://www.marsdaily.com/reports/BusinessCom_Networks_Connects_Mars_2013_999.html",
                lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "<img src=\"http://www.spxdaily.com/images-bg/" +
                "mars-base-spix-bg.jpg\" hspace=5" ) );
        assertNull( lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2013, 4, 4, 20,
                37, 27 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "Australia/Sydney" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void slashDotRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( slashdotInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Slashdot", feed.getTitle() );
        assertEquals( "http://slashdot.org/", feed.getWebsiteUrl() );
        assertEquals( "News for nerds, stuff that matters", feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Higgs Data Could Spell Trouble For Leading Big Bang Theory",
                firstArticle.getTitle() );
        assertEquals( "OpenShot Close To Funding Final Stretch Goal: Video Editing Server",
                lastArticle.getTitle() );
        assertEquals( "http://rss.slashdot.org/~r/Slashdot/slashdot/~3/bMKaCMTQ8JE/story01.htm",
                lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "The Kickstarter project we mentioned late " +
                "last month to bring open source video editor OpenShot" ) );
        assertEquals( "http://slashdot.feedsportal.com/c/35028/f/647410/s/2ac9c33e/l/0Lnews0Bslashdot0Borg0Cst" +
                "ory0C130C0A40C160C16520A30Copenshot0Eclose0Eto0Efunding0Efinal0Estretch0Egoal0Evideo0Eediting" +
                "0Eserver0Dutm0Isource0Frss10B0Amainlinkanon0Gutm0Imedium0Ffeed/story01.htm",
                lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "timothy", lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2013, 4, 16, 16,
                5, 00 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "GMT" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void slackwareRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( slackwareInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Alien's Slackware packages", feed.getTitle() );
        assertEquals( "http://www.slackware.com/~alien/", feed.getWebsiteUrl() );
        assertEquals( "Eric Hameleers (alien's) Slackware package repository. The package directories " +
                "include the SlackBuild script and sources.", feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Thu, 19 Dec 2013 10:46:28 GMT", firstArticle.getTitle() );
        assertEquals( "Fri, 22 Nov 2013 11:46:14 GMT", lastArticle.getTitle() );
        assertTrue( lastArticle.getDescription().contains( "For the (unchanged) dependency list, see the" +
                " 'Fri Aug 23 14:53:15 UTC 2013'" ) );
        assertEquals( "20131122124614", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2013, 11,
                22, 11, 46, 14 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "GMT" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void rottenTomatoesRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( rottenTomatoesInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Rotten Tomatoes: News", feed.getTitle() );
        assertEquals( "http://www.rottentomatoes.com/news/", feed.getWebsiteUrl() );
        assertEquals( "Entertainment news and headlines compiled by the editors of Rotten Tomatoes",
                feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Parental Guidance: Iron Man 3 and The Guilt Trip", firstArticle.getTitle() );
        assertEquals( "The Great Gatsby, Then and Now", lastArticle.getTitle() );
        assertEquals( "With Baz Luhrmann's adaptation nearing its debut, here's a look back at" +
                " the other times F. Scott Fitzgerald's classic novel made its way to the screen.",
                lastArticle.getDescription() );
        assertEquals( "http://www.rottentomatoes.com/m/1927370/news/1927370/",
                lastArticle.getGuid() );
        assertEquals( "http://content9.flixster.com/movie/11/16/53/11165363_tmb.jpg",
                lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDateTime = LocalDateTime.of( 2013, 5,
                1, 14, 25, 12 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "+0000" ) ),
                lastArticle.getPublicationDate() );
    }

    @Test
    public void ploumRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( ploumInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "ploum.net", feed.getTitle() );
        assertEquals( "http://ploum.net", feed.getWebsiteUrl() );
        assertEquals( "Le blog de Lionel Dricot", feed.getDescription() );
        assertEquals( "en-US", feed.getLanguage() );
        assertEquals( "The Disruptive Free Price", firstArticle.getTitle() );
        assertEquals( "Ripple, making Bitcoin easier (or obsolete)", lastArticle.getTitle() );
        assertEquals( "http://ploum.net/post/ripple-making-bitcoin-easier-or-obsolete", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "Who needs borders and local " +
                "regulations when you have internet?" ) );
        assertEquals( "http://ploum.net/?p=2641", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "Lionel Dricot", lastArticle.getCreator() );

        final var expectedLocalDate = LocalDate.of( 2013, 2, 26 );
        final var expectedLocalTime = LocalTime.of( 14, 31, 50 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate, expectedLocalTime,
                ZoneId.of( "+0000" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }

    @Test
    public void novaRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( novaInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "NOVA | PBS", feed.getTitle() );
        assertEquals( "http://www.pbs.org/nova/", feed.getWebsiteUrl() );
        assertEquals( "NOVA turns its lens on the timeliest developments and most intriguing personalities in science" +
                " and technology in a new magazine series, NOVA scienceNOW, and we want to hear what you" +
                " think about it.", feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Killer Typhoon", firstArticle.getTitle() );
        assertEquals( "ScienceCafes.org", lastArticle.getTitle() );
        assertEquals( "http://sciencecafes.org/", lastArticle.getUrl() );
        assertEquals( "Find out about Science Cafés. Their casual, open format readily engages the public" +
                " in conversations about science.", lastArticle.getDescription() );
        assertEquals( "http://www.pbs.org/wgbh/nova/rss/redir/http://sciencecafes.org/",
                lastArticle.getGuid() );
        assertEquals( "http://www.pbs.org/wgbh/nova/assets/img/thumbnails/science-cafes-ic.jpg",
                lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDate = LocalDate.of( 2013, 7, 18 );
        final var expectedLocalTime = LocalTime.of( 17, 0, 0 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "America/New_York" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }

    @Test
    public void nasaRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( nasaInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "NASA Breaking News", feed.getTitle() );
        assertEquals( "http://www.nasa.gov/", feed.getWebsiteUrl() );
        assertEquals( "A RSS news feed containing the latest NASA news articles and press releases.",
                feed.getDescription() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "NASA Signs Agreement with Space Florida to Operate Historic Landing Facility",
                firstArticle.getTitle() );
        assertEquals( "International Spacecraft Carrying NASA’s Aquarius Instrument Ends Operations",
                lastArticle.getTitle() );
        assertEquals( "http://www.nasa.gov/press-release/international-spacecraft-carrying-nasa-s-aquarius-" +
                "instrument-ends-operations", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "An international Earth-observing mission launched " +
                "in 2011 to" ) );
        assertEquals( "http://www.nasa.gov/press-release/international-spacecraft-carrying" +
                "-nasa-s-aquarius-instrument-ends-operations", lastArticle.getGuid() );
        assertEquals( "http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/" +
                "thumbnails/image/15-126b.jpg?itok=qE6IrQQ7", lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );
    }

    @Test
    public void malikiRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( malikiInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Le webcomic", feed.getTitle() );
        assertEquals( "http://www.maliki.com", feed.getWebsiteUrl() );
        assertEquals( "Le webcomic de Maliki", feed.getDescription() );
        assertEquals( "fr", feed.getLanguage() );
        assertEquals( "strip : Rétro gamines 2", firstArticle.getTitle() );
        assertEquals( "strip : Hit the road", lastArticle.getTitle() );
        assertEquals( "http://www.maliki.com/strip.php?strip=323", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "rang des grandes catastrophes technologiques mondiales " +
                "(un dossier dans Sciences et Vie je crois)" ) );
        assertNull( lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDate = LocalDate.of( 2012, 5, 30 );
        final var expectedLocalTime = LocalTime.of( 13, 25,00 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "+0000" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }

    @Test
    public void korbenRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( korbenInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Korben", feed.getTitle() );
        assertEquals( "http://korben.info", feed.getWebsiteUrl() );
        assertEquals( "Upgrade your mind", feed.getDescription() );
        assertEquals( "fr-FR", feed.getLanguage() );
        assertTrue( firstArticle.getTitle().contains( "La console pour les nostalgiques de la cartouche" ) );
        assertEquals( "Faire un screencast sous Ubuntu", lastArticle.getTitle() );
        assertEquals( "http://korben.info/logiciel-ubuntu-screencast.html", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "pensez à Kazam Screencaster. " +
                "L'avantage de Kazam c'est qu'il est simple" ) );
        assertEquals( "http://korben.info/?p=38768", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "Korben", lastArticle.getCreator() );

        final var expectedLocalDate = LocalDate.of( 2013, 3, 18 );
        final var expectedLocalTime = LocalTime.of( 12, 30, 36 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate, expectedLocalTime,
                ZoneId.of( "+0000" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }

    @Test
    public void iboxRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( iboxInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Topsport.bg - Испания", feed.getTitle() );
        assertEquals( "http://topsport.ibox.bg/section/id_20", feed.getWebsiteUrl() );
        assertEquals( "", feed.getDescription() );
        assertNull( feed.getLanguage() );
        assertEquals( "Роналдо не спира да бележи, Реал набира скорост", firstArticle.getTitle() );
        assertEquals( "Кристиано не тренира след ритника в София", lastArticle.getTitle() );
        assertEquals( "http://topsport.ibox.bg/news/id_1054327176", lastArticle.getUrl() );
        assertEquals( "Чичарито и Ияраменди също пропуснаха заниманието", lastArticle.getDescription() );
        assertEquals( "1054327176", lastArticle.getGuid() );
        assertEquals( "http://images.ibox.bg/2014/10/01/%EB%F3%E4-%F0%E5%E0%EB/75x75.jpg",
                lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDate = LocalDate.of( 2014, 10, 03 );
        final var expectedLocalTime = LocalTime.of( 8, 38, 14 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "+0300" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }

    @Test
    public void fubizRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( fubizInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Fubiz™", feed.getTitle() );
        assertEquals( "Daily dose of inspiration", feed.getDescription() );
        assertEquals( "http://www.fubiz.net", feed.getWebsiteUrl() );
        assertEquals( "fr", feed.getLanguage() );
        assertEquals( "Keep Left", firstArticle.getTitle() );
        assertEquals( "Ian Davis Paintings", lastArticle.getTitle() );
        assertEquals( "http://www.fubiz.net/2013/04/16/ian-davis-paintings/", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "Focus sur Ian Davis, un artiste qui imagine" +
                " de superbe peintures à" ) );
        assertEquals( "http://www.fubiz.net/?p=324358", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "Baptiste.B", lastArticle.getCreator() );

        final var expectedLocalDate = LocalDate.of( 2013, 4, 16 );
        final var expectedLocalTime = LocalTime.of( 12, 20, 17 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "+0000" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }
    
    @Test
    public void esaRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( esaInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "ESA Top News", feed.getTitle() );
        assertEquals( "ESA Top News", feed.getDescription() );
        assertEquals( "www.esa.int/", feed.getWebsiteUrl() );
        assertEquals( "en-GB", feed.getLanguage() );
        assertEquals( "ATV-4 scheduled for June liftoff", firstArticle.getTitle() );
        assertEquals( "ESA's Proba family", lastArticle.getTitle() );
        assertEquals( "http://spaceinvideos.esa.int/Videos/2013/03/Proba_Family", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains(
                "<img src=\"http://esa.int/var/esa/storage/images/esa_multimedia/images/2013" ) );
        assertEquals( "b68fd4aef0cfb9a977f31d98130f519f", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDate = LocalDate.of( 2013, 4, 10 );
        final var expectedLocalTime = LocalTime.of( 10, 53, 00 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "+0200" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }

    @Test
    public void distractionwareRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( distractionwareInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "distractionware", feed.getTitle() );
        assertEquals( "my development ramblings", feed.getDescription() );
        assertEquals( "http://distractionware.com/blog", feed.getWebsiteUrl() );
        assertEquals( "en-US", feed.getLanguage() );
        assertEquals( "Crash course", firstArticle.getTitle() );
        assertEquals( "Bosca Ceoil", lastArticle.getTitle() );
        assertEquals( "http://distractionware.com/blog/2013/08/bosca-ceoil/", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "I made a thing! It&#8217;s a simple music creation tool, " +
                "called Bosca Ceoil" ) );
        assertEquals( "http://distractionware.com/blog/?p=3354", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "Terry", lastArticle.getCreator() );

        final var expectedLocalDate = LocalDate.of( 2013, 8, 23 );
        final var expectedLocalTime = LocalTime.of( 23, 39, 10 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "+0000" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }

    @Test
    public void dilbertRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( dilbertInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Dilbert.com Blog", feed.getTitle() );
        assertEquals( "Regular thoughts and updates from Dilbert.com", feed.getDescription() );
        assertEquals( "http://dilbert.com/blog", feed.getWebsiteUrl() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( "Inventing the Unnecessary", firstArticle.getTitle() );
        assertEquals( "Hiring Versus Firing", lastArticle.getTitle() );
        assertEquals( "http://dilbert.com/blog/entry/hiring_versus_firing/", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "Imagine a manager who is excellent at identifying " +
                "and hiring talent but not so good at firing the people who need it" ) );
        assertEquals( "http://dilbert.com/blog/entry/929/", lastArticle.getGuid() );
        assertNull( lastArticle.getCreator() );

        final var expectedLocalDate = LocalDate.of( 2013, 5, 28 );
        final var expectedLocalTime = LocalTime.of( 01, 00, 01 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "America/Chicago" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
    }

    @Test
    public void developerWorksTests() throws Exception {
        final var feed = feedReader.readFeed( developerworksInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "IBM developerWorks : Java technology", feed.getTitle() );
        assertEquals( "The latest content from IBM developerWorks", feed.getDescription() );
        assertEquals( "http://www.ibm.com/developerworks/", feed.getWebsiteUrl() );
        assertEquals( "en", feed.getLanguage() );
        assertEquals( "Process real-time big data with Twitter Storm", firstArticle.getTitle() );
        assertEquals( "Node.js for Java developers", lastArticle.getTitle() );
        assertEquals( "http://www.ibm.com/developerworks/java/library/j-nodejs/index.html?ca=drs-",
                lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "Node.js presents an exciting " +
                "alternative to traditional Java concurrency" ) );

        final var expectedLocalDate = LocalDate.of( 2011, 11, 29 );
        final var expectedLocalTime = LocalTime.of( 5, 0, 0 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "+0000" ) );
        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertNull( lastArticle.getCreator() );
    }

    @Test
    public void cultizRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( cultizInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "Cultiz.com", feed.getTitle() );
        assertEquals( "Les accroculturoliques vont être servis !", feed.getDescription() );
        assertEquals( "http://cultiz.com/blog", feed.getWebsiteUrl() );
        assertEquals( "fr-FR", feed.getLanguage() );
        assertEquals( "2 Sample Or Not 2 Sample // Jean-Sébastien Bach", firstArticle.getTitle() );
        assertEquals( "Peintures historiques sur corps par Chadwick Gray et Laura Spector",
                lastArticle.getTitle() );
        assertEquals( "http://cultiz.com/blog/peintures-historiques-sur-corps-par-chadwick-gray-" +
                        "et-laura-spector/", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains(
                "<p><strong>Chadwick Gray et Laura Spector nous font perdre toutes" ) );

        final var expectedLocalDate = LocalDate.of( 2013, 7, 23 );
        final var expectedLocalTime = LocalTime.of( 8, 24, 30 );
        final var expectedLocalDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "+0000" ) );

        assertEquals( expectedLocalDateTime, lastArticle.getPublicationDate() );
        assertEquals( "http://cultiz.com/blog/?p=14646", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "PKD", lastArticle.getCreator() );
    }

    @Test
    public void bysmeRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( bysmeInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        final var lastArticle = feed.getArticles().get( feed.getArticles().size() - 1 );
        assertEquals( "by.S", feed.getTitle() );
        assertEquals( "by.S（バイ・エス）は忙しい現代女性ために、厳選したニュース・トレンド・美容・ファッション・" +
                "恋愛情報などを届けるメディアです。女性なら知っておきたいお得な情報を隙間時間にまとめてみることができます。",
                feed.getDescription() );
        assertEquals( "http://by-s.me", feed.getWebsiteUrl() );
        assertEquals( "ja", feed.getLanguage() );
        assertEquals( "キスの相性が悪いと別れる確率7割！？SEXの相性より大切な”キスの科学”", firstArticle.getTitle() );
        assertEquals( "プチプラなのにちゃんと可愛い！1万円以下で揃う2015年トレンド浴衣ブランド4選",
                lastArticle.getTitle() );
        assertEquals( "http://by-s.me/article/160942439736740666", lastArticle.getUrl() );
        assertTrue( lastArticle.getDescription().contains( "夏祭り、花火大会のシーズンが近づいてきました。" +
                "もう浴衣は準備しましたか。" ) );

        final var expectedLocalDate = LocalDate.of( 2015, 6, 19 );
        final var expectedLocalTime = LocalTime.of( 18, 58, 32 );
        final var expectedZonedDateTime = ZonedDateTime.of( expectedLocalDate,
                expectedLocalTime, ZoneId.of( "Asia/Tokyo" ) );

        assertEquals( expectedZonedDateTime, lastArticle.getPublicationDate() );
        assertEquals( "http://by-s.me/article/160942439736740666", lastArticle.getGuid() );
        assertNull( lastArticle.getThumbnailUrl() );
        assertEquals( "by.S", lastArticle.getCreator() );
    }

    

    @Test
    public void apodRssFeedTests() throws Exception {
        final var feed = feedReader.readFeed( apodInputStream );
        final var firstArticle = feed.getArticles().get( 0 );
        assertEquals( "APOD", feed.getTitle() );
        assertEquals( "Astronomy Picture of the Day", feed.getDescription() );
        assertEquals( "http://antwrp.gsfc.nasa.gov/", feed.getWebsiteUrl() );
        assertEquals( "en-us", feed.getLanguage() );
        assertEquals( 7, feed.getArticles().size() );
        assertEquals(  "Saturn Hurricane", firstArticle.getTitle() );
        assertEquals( "http://antwrp.gsfc.nasa.gov/apod/astropix.html", firstArticle.getUrl() );
        assertTrue( firstArticle.getDescription().contains( "href=\"http://antwrp.gsfc.nasa.gov/apod/astropix.html" ) );
        assertNull( firstArticle.getPublicationDate() );
        assertNull( firstArticle.getGuid() );
        assertNull( firstArticle.getThumbnailUrl() );
        assertNull( firstArticle.getCreator() );
    }

}
