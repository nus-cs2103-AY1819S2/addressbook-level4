package seedu.address.commons.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.NoInternetException;
import seedu.address.model.restaurant.Weblink;

/**
 * Validates Weblink and whether there is internet connection
 */
public class WebUtil {
    private static final String HTTPS_PREFIX = "https://";
    private static final String FILE_PREFIX = "file:/";

    /**
     * Checks if a given string is a valid weblink URL, ie. HTTP response code should not be 400 and above
     * The only acceptable malformed Url is the default placeholder for no weblinks, NO_WEBLINK_STRING
     */
    public static boolean isNotValidWeblinkUrl(String urlString) throws NoInternetException {
        try {
            urlString = prependHttps(urlString);
            URL u = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("HEAD");
            huc.connect();
            return huc.getResponseCode() >= 400;
        } catch (MalformedURLException e) {
            return !urlString.equals(Weblink.NO_WEBLINK_STRING);
        } catch (IOException e) {
            throw new NoInternetException(Messages.MESSAGE_NO_INTERNET);
        }
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

        // if url is a local path, return url
        if (url.startsWith(FILE_PREFIX)) {
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
