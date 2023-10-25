package org.reader.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.logging.Logger;

public final class DateUtil {

    private static final Logger log = Logger.getLogger( DateUtil.class.getSimpleName() );
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of( "+0000" );

    /**
     * A list of common date formats used in RSS/Atom feeds.
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional( DateTimeFormatter.ofPattern( "EEE, dd MMM yyyy HH:mm zzz" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "EEE, dd MMM yyyy HH:mm:ss Z" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "EEE, dd MMM yyyy HH:mm:ss zzz" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "EEE,  d MMM yyyy HH:mm:ss zzz" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "dd MMM yyyy HH:mm:ss Z" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "EEE, dd MMM yyyy HH:mm:ss" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "E, dd MMM yyyy HH:mm:ss z" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "dd MMM yyyy HH:mm:ss zzz" )  )
            .appendOptional( DateTimeFormatter.ofPattern( "EEE MMM dd yyyy HH:mm:ss 'GMT'Z Z" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "E, d MMM yyyy HH:mm:ss z" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm:ssZ" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm:ss.SSSZ" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm:ss.SSSz" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm:ssz" ) )
            .appendOptional( DateTimeFormatter.ofPattern( "d MMM yyyy H:mm:ss z" ) )
            .toFormatter()
            .withLocale( Locale.ENGLISH );

    public static ZonedDateTime parseDateStringToZonedDateTime(String dateString ) {
        var parsedDate = parseRssDate( dateString );
        if ( parsedDate == null ) {
            String cleanedDateString = clean( dateString );
            parsedDate = parseRssDate( cleanedDateString );
        }
        if ( parsedDate == null ) {
            log.info( "Error parsing date: " + dateString );
            return null;
        }
        log.info( "PARSED DATE: " + parsedDate );
        return parsedDate;
    }

    private static ZonedDateTime parseRssDate( String dateString ) {
        log.info( "Date being parsed: " + dateString );
        try {
            return parseRssDateStringWithTimeZoneCode( dateString );
        } catch ( Exception e ) {
            // NO-OP
        }
        try {
            return parseRssDateStringWithoutTimeZoneCode( dateString );
        } catch ( Exception e ){
            // NO-OP
        }
        return null;
    }

    private static ZonedDateTime parseRssDateStringWithTimeZoneCode( String dateString ) {
        return ZonedDateTime.parse( dateString, DATE_TIME_FORMATTER);
    }

    private static ZonedDateTime parseRssDateStringWithoutTimeZoneCode( String dateString ) {
        final var localDateTime = LocalDateTime.parse( dateString, DATE_TIME_FORMATTER);
        return ZonedDateTime.of( localDateTime, DEFAULT_ZONE_ID );
    }


    /**
     * For some reason, dates with the month names in uppercase, e.g, APR are not being parsed. If the month name
     * is present in the date string, only the first letter will be capitalized. For example,
     * this date - Thu, 04 APR 2013 20:37:27 AEST will be converted to Thu, 04 Apr 2013 20:37:27 AEST
     * Note: This method is used as a last resort when all our date parsing strategies have failed..
     * @param dateString - string to be cleaned
     */
    private static String clean( String dateString ) {
        final var monthAbbreviations = List.of( "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
                "Oct", "Nov", "Dec" );
        for ( String str : dateString.split( " " ) ) {
            for ( String abr : monthAbbreviations ) {
                if ( str.toLowerCase().contains( abr.toLowerCase() ) )
                    dateString = dateString.replace( str, abr );
            }
        }
        return dateString;
    }

    public static ZonedDateTime createZonedDateTimeWith( int year, int month, int day, int hour, int minute, int second,
                                                        String zoneId )  {
        final var date = LocalDate.of( year, month, day );
        final var time = LocalTime.of( hour, minute, second );
        return ZonedDateTime.of( date, time, ZoneId.of( zoneId ) );
    }
}
