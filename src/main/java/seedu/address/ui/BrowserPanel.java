package seedu.address.ui;

import static java.util.Objects.requireNonNull;

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
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(ObservableValue<Restaurant> selectedRestaurant) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load restaurant page when selected restaurant changes.
        selectedRestaurant.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadRestaurantPage(newValue);
        });

        loadDefaultPage();
    }

    /**
     * Loads restaurant page using weblink. If there's no weblink, load default page.
     */
    private void loadRestaurantPage(Restaurant restaurant) {
        /*loadPage(SEARCH_PAGE_URL + restaurant.getName().fullName);*/
        if (restaurant.getWeblink().value.equalsIgnoreCase(Weblink.NO_WEBLINK_STRING)) {
            loadPage(SEARCH_PAGE_URL + restaurant.getName().fullName);
        } else {
            loadPage(restaurant.getWeblink().value);
        }
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

}
