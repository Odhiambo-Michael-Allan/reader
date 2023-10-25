package util;

import org.reader.util.DateUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class DateUtilTests {

    private final String bbcDateString = "Thu, 13 Jul 2023 12:45:55 GMT",
            akeweaDateString = "2013-03-17T12:20:18+01:00",
            githubUserDateString = "2015-11-25T12:23:32Z",
            haverbekeDateString = "2013-04-19T00:00:00+01:00",
            makikoDateString = "2013-04-01T20:34:13.264+09:00",
            ploumDateString = "2013-07-10T11:02:07Z",
            whatifDateString = "2013-04-09T00:00:00Z",
            xkcdDateString = "2013-03-20T00:00:00Z",
            autostripDateString = "2013-06-09T00:29:11+02:00",
            lxerDateString = "2013-07-07T07:06:42-00:00",
            meisalamDateString = "2013-07-05T08:37:50+09:00",
            oatmealDateString = "2013-06-24T17:27:56+01:00",
            bysmeDateString = "Mon Jun 22 2015 20:12:20 GMT+0900 (JST)",
            cultizDateString = "Wed, 04 Sep 2013 21:40:09 +0000",
            developerworksDateString = "02 Apr 2013 04:00:00 +0000",
            dilbertDateString = "Thu, 15 Aug 2013 01:00:01 CDT",
            distractionwareDateString = "Wed, 16 Oct 2013 19:42:58 +0000",
            esaDateString = "Thu, 11 Apr 2013 15:02:00 +0200",
            fubizDateString = "Wed, 17 Apr 2013 10:25:59 +0000",
            iboxDateString = "Sun, 05 Oct 2014 23:51:21 +0300",
            korbenDateString = "Sun, 24 Mar 2013 07:03:43 +0000",
            lemessagerDateString = "Thu, 19 Dec 2013 14:00:00 +0200",
            malikiDateString = "Fri, 21 Jun 2013 11:03:25",
            nasaDateString = "Mon, 22 Jun 2015 11:41 EDT",
            novaDateString = "19 Dec 2013 9:00:00 EST",
            ploumRssDateString = "Thu, 28 Mar 2013 15:41:53 +0000",
            rottenTomatoesDateString = "2013-05-02 09:48:21",
            slackwareDateString = "Mon, 16 Dec 2013 00:11:55 GMT",
            slashdotDateString = "2013-04-17T15:36:00Z",
            spaceDateString = "Wed, 27 Mar 2013 19:10:22 EDT",
            spaceDailyDateString = "Thu, 04 APR 2013 20:37:27 AEST",
            xkcdRssDateString = "Wed, 20 Mar 2013 04:00:00 -0000",
            nationDateString = "Thu, 13 Jul 2023 13:31:39 +0300",
            standardDateString = "Wed, 12 Jul 2023 19:30:00 +0300",
            standardKenyaDateString = "Mon, 10 Jul 2023 16:46:25 +0300",
            standardSportsDateString = "Thu, 13 Jul 2023 09:30:00 +0300",
            standardWorldDateString = "Tue, 11 Jul 2023 08:23:21 +0300";
    private final List<String> dateStringList = List.of( standardWorldDateString, standardSportsDateString,
            standardKenyaDateString, standardDateString, nationDateString, xkcdRssDateString, spaceDailyDateString,
            spaceDateString, slashdotDateString, slackwareDateString, rottenTomatoesDateString, ploumRssDateString,
            novaDateString, nasaDateString, malikiDateString, lemessagerDateString, korbenDateString, iboxDateString,
            fubizDateString, esaDateString, distractionwareDateString, dilbertDateString, developerworksDateString,
            cultizDateString, bysmeDateString, oatmealDateString, meisalamDateString, lxerDateString,
            autostripDateString, xkcdDateString, whatifDateString, ploumDateString, makikoDateString,
            haverbekeDateString, githubUserDateString, akeweaDateString, bbcDateString );

    @Test
    public void testDateUtilOnAvailableFeedDates() throws Exception {
        for ( String dateString : dateStringList )
            assertNotNull( DateUtil.parseDateStringToZonedDateTime( dateString ) );
    }


    @Test
    public void dateUtilCorrectlyParsesStandardDate() {
        final var expectedDate = DateUtil.createZonedDateTimeWith( 2023, 7, 12, 19, 30,
                0, "+03" );
        final var standardDateString = "Wed, 12 Jul 2023 19:30:00 +0300";
        final var parsedDate = DateUtil.parseDateStringToZonedDateTime( standardDateString );
        assertNotNull( parsedDate );
        assertEquals( expectedDate, parsedDate );
    }

    @Test
    public void dateUtilCorrectlyParsesBbcDate() {
        final var expectedDate = DateUtil.createZonedDateTimeWith( 2023, 7, 13,
                5, 2, 56, "GMT" );
        final var bbcDateString = "Thu, 13 Jul 2023 05:02:56 GMT";
        final var parsedDate = DateUtil.parseDateStringToZonedDateTime( bbcDateString );
        assertNotNull( parsedDate );
        assertEquals( expectedDate, parsedDate );
    }

    @Test
    public void dateUtilCorrectlyParsesNationDateString() {
        final var expectedDate = DateUtil.createZonedDateTimeWith( 2023, 7, 13, 13,
                31, 39, "+03" );
        final var nationDateString = "Thu, 13 Jul 2023 13:31:39 +0300";
        final var parsedDate = DateUtil.parseDateStringToZonedDateTime( nationDateString );
        assertNotNull( parsedDate );
        assertEquals( expectedDate, parsedDate );
    }

    @Test
    public void dateUtilCorrectlyParsesSpaceDailyDateString() {
        final var parsedDate = DateUtil.parseDateStringToZonedDateTime( "Thu, 04 APR 2013 20:37:27 AEST" );
        final var expectedLocalDateTime = LocalDateTime.of( 2013, 4, 4, 20, 37,
                27 );
        assertEquals( ZonedDateTime.of( expectedLocalDateTime, ZoneId.of( "Australia/Sydney" ) ),
                parsedDate );
    }

    @Test
    public void dateUtilCorrectlyParsesGizmodoDateString() {
        final var parsedDate = DateUtil.parseDateStringToZonedDateTime( "Fri, 5 Jul 2013 16:00:00 GMT" );
        assertNotNull( parsedDate );
    }

}
