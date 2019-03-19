package seedu.hms.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.hms.MainApp;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.model.customer.Customer;

/**
 * The Browser Panel of the App.
 */
public class ServiceTypeAndRoomType extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
        requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";

    private static final String FXML = "BookingTypeAndRoomTypePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public ServiceTypeAndRoomType(ObservableValue<Customer> selectedCustomer) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load customer page when selected customer changes.
        selectedCustomer.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadCustomerPage(newValue);
        });

        loadDefaultPage();
    }

    private void loadCustomerPage(Customer customer) {
        loadPage(SEARCH_PAGE_URL + customer.getName().fullName);
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
