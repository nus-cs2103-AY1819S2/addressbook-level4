package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.request.Request;
import seedu.address.model.tag.Condition;

/**
 * The Info Panel of the App.
 * Responsible for displaying the request details when selected.
 * Map is rendered via URL request to gothere.sg.
 * For more info, visit https://gothere.sg/api/maps/staticmaps.html.
 * @author Hui Chun
 */
public class InfoPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final URL STYLESHEET =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "WhiteTheme.css"));
    public static final URL FA_STYLESHEET =
            requireNonNull(MainApp.class.getResource(FA_FILE_FOLDER + "css/all.min.css"));

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView webView;

    public InfoPanel(ObservableValue<Request> selectedRequest) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();

        // Attaches a listener that displays the request details when selected.
        selectedRequest.addListener((observable, oldValue, newValue) -> {
            logger.info("InfoPanel triggered on request selection.");

            Request request = selectedRequest.getValue();
            String htmlContent = generateHtml(request);
            loadContent(htmlContent);

            if (newValue == null) {
                return;
            }
        });
    }

    /**
     * Loads a default HTML file that displays the welcome message and starter tips.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

    /**
     * Loads a normal web page.
     * @param url
     */
    public void loadPage(String url) {
        Platform.runLater(() -> webView.getEngine().load(url));
    }

    /**
     * Loads the given HTML content directly into the web engine.
     * @param htmlContent
     */
    public void loadContent(String htmlContent) {
        Platform.runLater(() -> webView.getEngine().loadContent(htmlContent));
    }

    /**
     * Generates the entire HTML structure via this method.
     * Takes in a {@code request} object and the StringBuilder appends its properties.
     * @param request
     * @return a string of HTML content
     */
    public String generateHtml(Request request) {

        String url = constructMapUrl(request.getAddress().toString());
        String name = request.getName().toString();
        String nric = request.getNric().toString();
        String phone = request.getPhone().toString();
        String address = request.getAddress().toString();
        String conditions = request.getConditions().stream().map(Condition::toString).collect(
                Collectors.joining(", "));
        String healthStaff = request.getHealthStaff();
        String date = request.getRequestDate().getFormattedDate();
        String status = request.getRequestStatus().toString();

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html><html><head>");
        htmlBuilder.append("<link href=\"" + STYLESHEET + "\"" + " rel=\"stylesheet\"/>");
        htmlBuilder.append("<link href=\"" + FA_STYLESHEET + "\"" + " rel=\"stylesheet\"/></head>");
        htmlBuilder.append("<body class=\"request-details\"></br>");
        htmlBuilder.append("<i class=\"fas fa-user\"></i> Request Patient: " + name + "</br>");
        htmlBuilder.append("<i class=\"fas fa-id-card\"></i> Patient NRIC: " + nric + "</br>");
        htmlBuilder.append("<i class=\"fas fa-phone\"></i> Patient Contact: " + phone + "</br>");
        htmlBuilder.append("<i class=\"fas fa-map-marker-alt\"></i> Patient Address: " + address + "</br>");
        htmlBuilder.append("<i class=\"fas fa-notes-medical\"></i> Patient Conditions: " + conditions + "</br>");
        if (request.getHealthStaff() != null) { // show staff name
            htmlBuilder.append("<i class=\"fas fa-user-nurse\"></i> Assigned Staff: " + healthStaff + "</br>");
        } else {
            htmlBuilder.append("<i class=\"fas fa-user-nurse\"></i> Assigned Staff: Not Assigned </br>");
        }
        htmlBuilder.append("<i class=\"fas fa-calendar-alt\"></i> Appt. Date: " + date + "</br>");
        if (status == "PENDING") {
            htmlBuilder.append("<i class=\"fas fa-times\"></i> Request Status: " + status + "</br></br>");
        } else { // if ongoing or completed
            htmlBuilder.append("<i class=\"fas fa-check\"></i> Request Status: " + status + "</br></br>");
        }

        try {
            htmlBuilder.append(getEncodedImage(url));
        } catch (IOException e) {
            htmlBuilder.append("Unable to view address location in map.");
        }
        htmlBuilder.append("</body></html>");

        return htmlBuilder.toString();
    }

    /**
     * Constructs the URL for accessing the static map.
     * Takes in an {@code address} string and extracts only the street details.
     * @param address
     * @return a url string to access the map
     */
    private String constructMapUrl(String address) {

        String street = address.substring(0, address.indexOf(","));
        street = street.replaceAll("\\s", "%20");
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://gothere.sg/maps/staticmap?center=%22");
        urlBuilder.append(street + "%22&zoom=16&size=400x200&markers=%22");
        urlBuilder.append(street + "%22,orange&sensor=false");
        //logger.info(urlBuilder.toString());
        return urlBuilder.toString();
    }

    /**
     * This method opens a {@code HTTPURLConnection} object by supplying in the url.
     * Sets the request method as "GET" and retrieves the {@code BufferedInputStream} object
     * from the connection, then encodes the bytes to a string to display in the HTML content.
     * Reference: https://dzone.com/articles/how-to-implement-get-and-post-request-through-simp
     * @param url
     * @return a base64 representation of an image
     * @throws IOException
     */
    private String getEncodedImage(String url) throws IOException {
        URL urlForGetRequest = new URL(url);
        byte[] imageBytes = new byte[0];

        try {
            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                //logger.info("AVAILABLE BYTES: " + bis.available());
                if (bis.available() != 0) { // if there are bytes to read from InputStream
                    for (byte[] byteArray = new byte[bis.available()];
                         bis.read(byteArray) != -1; ) {
                        byte[] temp = new byte[imageBytes.length + byteArray.length];
                        System.arraycopy(imageBytes, 0, temp, 0, imageBytes.length);
                        System.arraycopy(byteArray, 0, temp, imageBytes.length, byteArray.length);
                        imageBytes = temp;
                    }
                } else {
                    return "Unable to view address location in map.";
                }
            }
        } catch (IOException io) {
            throw new IOException(io.getMessage());
        }
        return "<img src='data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes) + "' />";
    }
}
