package seedu.address.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.equipment.Equipment;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String MAP_PAGE_BASE_URL = "https://cs2103-ay1819s2-w10-3.github.io/main/DisplayGmap";
    public static final URL DEFAULT_PAGE = processDefaultPage(MAP_PAGE_BASE_URL);
    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(ObservableValue<Equipment> selectedPerson) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load equipment page when selected equipment changes.
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadEquipmentPage(newValue);
        });
        loadDefaultPage();
    }

    /**
     * Load the equipment page of a equipment.
     * @param equipment The equipment to load.
     */
    private void loadEquipmentPage(Equipment equipment) {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBQ5YiOpupDO8JnZqmqYTujAwP9U4R5JBA")
                .build();
        String url = MAP_PAGE_BASE_URL;
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    equipment.getAddress().toString()).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (results.length > 0) {
                System.out.println();
                url = MAP_PAGE_BASE_URL + "?coordinates=[[" + results[0].geometry.location.lng + ","
                        + results[0].geometry.location.lat + "]]&title=[\"" + equipment.getName()
                        + "\"]&icon=[\"monument\"]";
            }
        } catch (ApiException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println("Loading page: " + url);
            loadPage(url);
        }
    }

    /**
     * Process the default url String.
     * @param urlString the default url string
     * @return the URL object
     */
    private static URL processDefaultPage(String urlString) {
        try {
            URL url = new URL(MAP_PAGE_BASE_URL);
            return url;
        } catch (MalformedURLException mue) {
            System.err.println("Fatal error: Default url cannot be formatted.");
            return null;
        }
    }
    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(MAP_PAGE_BASE_URL);
    }

}
