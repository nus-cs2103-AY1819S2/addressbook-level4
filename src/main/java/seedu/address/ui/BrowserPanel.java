package seedu.address.ui;

import static java.util.Objects.requireNonNull;
import static seedu.address.MainApp.GOOGLE_API_KEY;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.place.Place;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String GOOGLE_SEARCH_URL = "http://www.google.com.sg";
    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";
    public static final String GOOGLE_MAPS_COLLAPSE = "http://maps.google.com/maps?iframe&output=embed&q=";
    public static final String GOOGLE_MAPS_URL = "https://www.google.com/maps/search/?api=1&query=";
    public static final String GOOGLE_MAPS_EMBED = "https://www.google.com/maps/embed/v1/place?key="
            + GOOGLE_API_KEY + "&q=";
    private static final String FXML = "BrowserPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(ObservableValue<Place> selectedPlace) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load place page when selected place changes.
        selectedPlace.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadPlacePage(newValue);
        });

        loadDefaultPage();
        //loadPage(DUMMY_PAGE_URL);
    }

    /**
     * Loads the Google Maps with the given address.
     */
    private void loadMapsPage(String address) {
        //try {
        //GeocodingResult[] results =  GeocodingApi.geocode(CONTEXT, address)..await();
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(results[0].addressComponents));
        String url = GOOGLE_MAPS_URL + address;
        System.out.println(url);
        Platform.runLater(() -> browser.getEngine().load(url));
        //Platform.runLater(() -> browser.getEngine().load(DEFAULT_PAGE.toExternalForm()));
        //System.out.println(results.toString());
        //System.out.println(gson.toJson(results[0].formattedAddress));
        //}
        //catch (InterruptedException | IOException | ApiException e) {
        //e.printStackTrace();
        //}
    }

    /**
     * Redirects to the loadsMapPage() with a specified address.
     */
    private void loadPlacePage(Place place) {
        //try {
        //String url = java.net.URLEncoder.encode(place.getAddress().toString(), "ISO-8859-1");
        //loadMapsPage(url);
        //} catch (UnsupportedEncodingException e) {
        //e.printStackTrace();
        //}
        loadPage(SEARCH_PAGE_URL + place.getName().fullName);
    }

    private void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

}
