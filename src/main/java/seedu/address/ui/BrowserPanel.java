package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String SEARCH_PAGE_URL = "https://www.google.com/maps/search/?api=1&query=";

    private static final String FXML = "BrowserPanel.fxml";
    private static final String NO_CONNECTION = "No Internet connection! Restart app if connection is established";
    private static final String NO_CONTACT_SELECTED = "No contact selected";
    private static final String NO_ADDRESS_AVAILABLE = "No map location to display!";

    private static boolean isInternetAvailable;

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private Label browserLabel;

    public BrowserPanel(ObservableValue<Person> selectedPerson) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Test for internet connectivity
        try {
            URL url = new URL("https://www.google.com/maps/");
            URLConnection connection = url.openConnection();
            connection.connect();
            isInternetAvailable = true;
        } catch (Exception e) {
            isInternetAvailable = false;
        }

        browserLabel.setText(NO_CONTACT_SELECTED);

        // Load person page when selected person changes.
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                browserLabel.setText(NO_CONTACT_SELECTED);
                loadDefaultPage();
                return;
            } else if (newValue.getAddress().value == null) {
                browserLabel.setText(NO_ADDRESS_AVAILABLE);
                loadDefaultPage();
                return;
            } else if (!isInternetAvailable) {
                browserLabel.setText(NO_CONNECTION);
                loadDefaultPage();
                return;
            }
            browserLabel.setText(newValue.getName().fullName + "'s Map Location:");
            loadMapsPage(newValue);
        });

        loadDefaultPage();
    }

    private void loadMapsPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getAddress().value);
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
