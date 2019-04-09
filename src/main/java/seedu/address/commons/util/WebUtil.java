package seedu.address.commons.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import seedu.address.commons.exceptions.NoInternetException;
import seedu.address.model.restaurant.Weblink;

/**
 * Validates Weblink and whether there is internet connection
 */
public class WebUtil {
    private static final String HTTPS_PREFIX = "https://";
    private static final String MESSAGE_NO_INTERNET = "Internet connection is not available."
            + " Please check your connections.";
    private static final String DUMMY_WEBLINK = "https://www.google.com.sg";

    /**
     * Checks if a given string is a valid weblink URL, ie. HTTP response code should not be 400 and above
     * The only acceptable malformed Url is the default placeholder for no weblinks, NO_WEBLINK_STRING
     */
    public static boolean isNotValidWeblinkUrl(String urlString) throws NoInternetException {
        // checks if there is internet connection
        if (!urlString.equals(DUMMY_WEBLINK) && !hasInternetConnection()) {
            throw new NoInternetException(MESSAGE_NO_INTERNET);
        }
        try {
            final URL url = new URL(prependHttps(urlString));
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    /**
     * To check internet connection against a reliable weblink, ie. google
     * @return false if fails to connect to google
     * @throws NoInternetException
     */
    private static boolean hasInternetConnection() throws NoInternetException {
        return !isNotValidWeblinkUrl(DUMMY_WEBLINK);
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

        // if url already starts with https prefix, return url
        if (url.startsWith(HTTPS_PREFIX)) {
            return url;
        }

        // return url with https prefix prepended to it
        return HTTPS_PREFIX.concat(url);
    }
}
