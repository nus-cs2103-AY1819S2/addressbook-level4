package seedu.address.commons.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.NoInternetException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Weblink;

/**
 * Validates Weblink and whether there is internet connection
 */
public class WebUtil {

    public static final String INVALID_URL_MESSAGE = "%1$s is not found. Please enter a correct weblink";
    private static final String HTTPS_PREFIX = "https://";
    private static final String HTTP_PREFIX = "http://";
    private static final String MESSAGE_NO_INTERNET = "Internet connection is not available."
            + " Please check your connections.";
    private static final String DUMMY_WEBLINK = "https://www.google.com.sg";

    public static boolean isUrlValid(String urlString) {
        try {
            final URL url = new URL(prependHttps(urlString));
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (IOException httpsE) {
            return false;
        }
    }
    
    /**
     * To check internet connection against a reliable weblink, ie. google
     * @return false if fails to connect to google
     * @throws NoInternetException
     */
    private static boolean hasInternetConnection() throws NoInternetException {
        return isUrlValid(DUMMY_WEBLINK);
    }

    /**
     * If input url has no https:// prepended to it, return url with https:// prepended.
     * @param url
     * @return String that has https:// prepended to url string
     */
    public static String prependHttps(String url) {
        // if Weblink is not added for user, return url
        if (url.equals(Weblink.NO_WEBLINK_STRING)) {
            return url;
        }

        // return url with https prefix prepended to it
        return HTTPS_PREFIX.concat(url);
    }

    /**
     * If input url has no http:// prepended to it, return url with https:// prepended.
     * @param url
     * @return String that has http:// prepended to url string
     */
    private static String prependHttp(String url) {
        // if Weblink is not added for user, return url
        if (url.equals(Weblink.NO_WEBLINK_STRING)) {
            return url;
        }

        // return url with https prefix prepended to it
        return HTTP_PREFIX.concat(url);
    }

    public static String validateAndAppend(String urlString) throws NoInternetException, ParseException {
        // checks if there is internet connection
        if (!urlString.equals(DUMMY_WEBLINK) && !hasInternetConnection()) {
            throw new NoInternetException(MESSAGE_NO_INTERNET);
        }

        boolean invalidUrl;
        String trimmedWeblink = urlString;
        if (urlString.startsWith(HTTPS_PREFIX) || urlString.startsWith(HTTP_PREFIX)) {
            invalidUrl = isUrlValid(urlString);
        } else if (!isUrlValid(prependHttps(urlString))) {
            invalidUrl = isUrlValid(prependHttp(urlString));
            trimmedWeblink = prependHttp(urlString);
        } else {
            invalidUrl = isUrlValid(prependHttps(urlString));
            trimmedWeblink = prependHttps(urlString);
        }

        if (!invalidUrl) {
            throw new ParseException(INVALID_URL_MESSAGE);
        } else {
            return trimmedWeblink;
        }
    }
}
