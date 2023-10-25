package org.reader.feed;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Pattern;

public final class XmlReader extends Reader {

    private static final int NUMBER_OF_BYTES_IN_XML_HEAD_TAG = 128;
    private final InputStreamReader inputStreamReader;
    private final String encodingOfSuppliedInputStream;
    private final PushbackInputStream inputStream;
    private final String defaultEncoding;

    public XmlReader( InputStream inputStream, String defaultEncoding ) throws Exception {
        this.inputStream = new PushbackInputStream( inputStream, NUMBER_OF_BYTES_IN_XML_HEAD_TAG );
        this.defaultEncoding = defaultEncoding;
        encodingOfSuppliedInputStream = determineEncodingForInputStream();
        inputStreamReader = new InputStreamReader( this.inputStream, encodingOfSuppliedInputStream );
    }

    public String determineEncodingForInputStream() throws Exception {
        var encoding = "";
        final var header = new byte[ NUMBER_OF_BYTES_IN_XML_HEAD_TAG ];
        this.inputStream.read( header, 0, header.length );
        encoding = findByteOrderMatrixMark( header );
        if ( encoding.isEmpty() )
            encoding = findEncodingInHeader( header );
        // Unread all the bytes that we read into header back into the input stream.
        this.inputStream.unread( header );
        return encoding.toUpperCase(Locale.ENGLISH );
    }

    private String findByteOrderMatrixMark(byte[] header ) {
        if ( ( header[0] == ( byte ) 0xEF ) && ( header[1] == ( byte ) 0xBB ) && ( header[2] == ( byte ) 0xBF ) )
            return "UTF-8";
        else if ( ( header[0] == ( byte ) 0xFE ) && ( header[1] == ( byte ) 0xFF ) )
            return "UTF-16BE";
        else if ( ( header[0] == ( byte ) 0xFF ) && ( header[1] == ( byte ) 0xFE ) )
            return "UTF-16LE";
        else if ( ( header[0] == ( byte ) 0x00 ) && ( header[1] == ( byte ) 0x00 ) &&
                ( header[2] == ( byte ) 0xFE ) && ( header[3] == ( byte ) 0xFF ) )
            return "UTF-32BE";
        return "";

    }

    private String findEncodingInHeader( byte[] header ) {
        final var pattern = Pattern.compile( "encoding=\"(.*?)\"" );
        final var matcher = pattern.matcher( new String( header ) );
        if ( matcher.find() ) {
            final var rawEncoding = matcher.group( 1 );
            try {
                Charset.forName( rawEncoding );
                return rawEncoding;
            }
            catch ( Exception e ) {
                // NOP
            }
        }
        return defaultEncoding;
    }

    @Override
    public int read( char[] chars, int i, int i1 ) throws IOException {
        final var res = inputStreamReader.read( chars, i, i1 );
        return res;
    }

    @Override
    public void close() throws IOException {
        inputStreamReader.close();
    }

    public String getEncoding() {
        return this.encodingOfSuppliedInputStream;
    }
}
