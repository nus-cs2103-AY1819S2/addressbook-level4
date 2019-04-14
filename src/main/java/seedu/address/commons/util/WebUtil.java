package seedu.address.commons.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

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


    /**
     * This method checks if the Url string supplied to it is a valid link
     * @param urlString must be a valid Url formatted string
     * @return true if url is valid
     * @throws NoInternetException if there's no internet connection
     */
    public static boolean isUrlValid(String urlString) throws NoInternetException {
        // checks if there is internet connection
        if (!urlString.equals(DUMMY_WEBLINK) && !hasInternetConnection()) {
            throw new NoInternetException(MESSAGE_NO_INTERNET);
        }

        try {
            final URL url = new URL(urlString);
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

    /**
     * This method validates the Url string supplied to it by calling isUrlValid on it.
     * If the protocol of the Url is absent, test whether its https or http and append to it.
     *
     * @param urlString
     * @return a proper Url a string that is valid and has either https/http protocol appended to it
     * @throws NoInternetException if there's no internet connection
     * @throws ParseException If Url is invalid even after appending the protocol
     */
    public static String validateAndAppend(String urlString) throws NoInternetException, ParseException {

        boolean isValidUrl;

        // Original form of Weblink for returning error message
        String originalWeblink = urlString;

        // To append https or http protocol and to be returned
        String trimmedWeblink = urlString;

        if (urlString.startsWith(HTTPS_PREFIX) || urlString.startsWith(HTTP_PREFIX)) {
            isValidUrl = isUrlValid(urlString);
        } else if (!isUrlValid(prependHttps(urlString))) {
            isValidUrl = isUrlValid(prependHttp(urlString));
            trimmedWeblink = prependHttp(urlString);
        } else {
            isValidUrl = isUrlValid(prependHttps(urlString));
            trimmedWeblink = prependHttps(urlString);
        }

        if (!isValidUrl) {
            throw new ParseException(String.format(INVALID_URL_MESSAGE, originalWeblink));
        } else {
            return trimmedWeblink;
        }
    }
}
