package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.request.Request;

/**
 * The Info Panel of the App.
 * Responsible for displaying the request details when selected.
 * Map is rendered via URL request to gothere.sg.
 * For more info, visit https://gothere.sg/api/maps/staticmaps.html.
 * @@author Hui Chun
 */
public class InfoPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final URL STYLESHEET =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "WhiteTheme.css"));

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView webView;

    public InfoPanel(ObservableValue<Request> selectedRequest) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Displays the request details when selected.
        selectedRequest.addListener((observable, oldValue, newValue) -> {
            logger.info("InfoPanel triggered on request selection.");

            Request request = selectedRequest.getValue();
            String htmlContent = generateHTML(request);
            loadContent("");
            loadContent(htmlContent);

            if (newValue == null) {
                return;
            }
        });

        loadDefaultPage();
    }

    /**
     * Loads a default HTML file that displays the welcome message and starter tips.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

    public void loadContent(String htmlContent) {
        Platform.runLater(() -> webView.getEngine().loadContent(htmlContent));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> webView.getEngine().load(url));
    }

    private String generateHTML(Request request){

        String url = constructMapURL(request.getAddress().toString());

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html><html><head>");
        htmlBuilder.append("<link href='../../../../resources/view/WhiteTheme.css' rel='stylesheet'></head>");
        htmlBuilder.append("<body class='request-details'></br>");
        htmlBuilder.append("Request Patient: " + request.getName().toString() + "</br>");
        htmlBuilder.append("Patient NRIC: " + request.getNric().toString() + "</br>");
        htmlBuilder.append("Patient Contact: " + request.getPhone().toString() + "</br>");
        htmlBuilder.append("Patient Address: " + request.getAddress().toString() + "</br>");
        htmlBuilder.append("Patient Conditions: " + request.getConditions().toString() + "</br>");
        if (request.getHealthStaff() != null) {
            htmlBuilder.append("Assigned Staff: " + request.getHealthStaff() + "</br>");
        } else {
            htmlBuilder.append("Assigned Staff: Not Assigned </br>");
        }
        htmlBuilder.append("Appt. Date: " + request.getRequestDate().getFormattedDate() + "</br>");
        htmlBuilder.append("Request Status: " + request.getRequestStatus().toString() + "</br></br>");

        try {
            htmlBuilder.append(getEncodedImage(url));
        } catch(IOException e) {
            logger.info(e.getMessage());
        }
        htmlBuilder.append("</body></html>");

        return htmlBuilder.toString();
    }

    private String constructMapURL(String address) {

        String street = address.substring(0, address.indexOf(","));
        street = street.replaceAll("\\s", "%20");
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://gothere.sg/maps/staticmap?center=%22");
        urlBuilder.append(street + "%22&zoom=16&size=400x200&markers=%22");
        urlBuilder.append(street + "%22,green&sensor=false");
        //logger.info(urlBuilder.toString());
        return urlBuilder.toString();
    }

    // Reference: https://dzone.com/articles/how-to-implement-get-and-post-request-through-simp
    private String getEncodedImage(String url) throws IOException {
        URL urlForGetRequest = new URL(url);
        byte[] imageBytes = new byte[0];

        try {
            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                logger.info("AVAILABLE BYTES: " + bis.available());
                if (bis.available() != 0) {
                    for (byte[] byteArray = new byte[bis.available()];
                         bis.read(byteArray) != -1; ) {
                        byte[] temp = new byte[imageBytes.length + byteArray.length];
                        System.arraycopy(imageBytes, 0, temp, 0, imageBytes.length);
                        System.arraycopy(byteArray, 0, temp, imageBytes.length, byteArray.length);
                        imageBytes = temp;
                    }
                } else {
                    return "Unable to load address location in map.";
                }
            }
        } catch(IOException io) {
            throw new IOException(io.getMessage());
        }
        return "<img src='data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes) + "' />";
    }
}
