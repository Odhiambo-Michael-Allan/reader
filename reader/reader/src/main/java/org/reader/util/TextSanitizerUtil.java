package org.reader.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.util.regex.Pattern;

public class TextSanitizerUtil {
    private final static PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
    private final static Pattern TAG_PATTERN = Pattern.compile("&lt;.+&gt;");

    /**
     * Sanitize title contents.
     *
     * @param html HTML to sanitize
     * @return Sanitized HTML
     */
    public static String sanitize(String html) {
        final String safeHtml = policy.sanitize(html);
        return TAG_PATTERN.matcher(safeHtml).replaceAll("");
    }
}
