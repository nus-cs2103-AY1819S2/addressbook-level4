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
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String SEARCH_PAGE_URL = "https://www.google.com/maps/search/?api=1&query=";
    public static final String TEST_URL = "https://www.google.com/maps/";

    private static final String FXML = "BrowserPanel.fxml";
    private static final String NO_CONNECTION = "No Internet connection! Establish connection to display map";
    private static final String NO_CONTACT_SELECTED = "No contact selected";
    private static final String NO_ADDRESS_AVAILABLE = "No map location to display";


    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    @FXML
    private Label browserLabel;

    public BrowserPanel(ObservableValue<Person> selectedPerson, ObservableValue<Person> selectedArchivedPerson,
                        ObservableValue<Person> selectedPinPerson) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        browserLabel.setText(NO_CONTACT_SELECTED);

        // Load page when selected pinned person changes.
        selectedPinPerson.addListener((observable, oldValue, newValue) -> {
            // Test for internet connectivity
            try {
                URL url = new URL(TEST_URL);
                URLConnection connection = url.openConnection();
                connection.connect();

                if (newValue == null) {
                    browserLabel.setText(NO_CONTACT_SELECTED);
                    loadDefaultPage();
                    return;
                } else if (personWontLoadMaps(newValue)) {
                    browserLabel.setText(NO_ADDRESS_AVAILABLE);
                    loadDefaultPage();
                    return;
                }

                browserLabel.setText(newValue.getName().fullName + "'s Map Location:");
                loadMapsPage(newValue);

            } catch (Exception e) {
                browserLabel.setText(NO_CONNECTION);
                loadDefaultPage();
            }
        });

        // Load page when selected person changes.
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            // Test for internet connectivity
            try {
                URL url = new URL(TEST_URL);
                URLConnection connection = url.openConnection();
                connection.connect();

                if (newValue == null) {
                    browserLabel.setText(NO_CONTACT_SELECTED);
                    loadDefaultPage();
                    return;
                } else if (personWontLoadMaps(newValue)) {
                    browserLabel.setText(NO_ADDRESS_AVAILABLE);
                    loadDefaultPage();
                    return;
                }

                browserLabel.setText(newValue.getName().fullName + "'s Map Location:");
                loadMapsPage(newValue);

            } catch (Exception e) {
                browserLabel.setText(NO_CONNECTION);
                loadDefaultPage();
            }
        });

        // Load page when selected archived person changes.
        selectedArchivedPerson.addListener((observable, oldValue, newValue) -> {
            // Test for internet connectivity
            try {
                URL url = new URL(TEST_URL);
                URLConnection connection = url.openConnection();
                connection.connect();

                if (newValue == null) {
                    browserLabel.setText(NO_CONTACT_SELECTED);
                    loadDefaultPage();
                    return;
                } else if (personWontLoadMaps(newValue)) {
                    browserLabel.setText(NO_ADDRESS_AVAILABLE);
                    loadDefaultPage();
                    return;
                }

                browserLabel.setText(newValue.getName().fullName + "'s Map Location:");
                loadMapsPage(newValue);

            } catch (Exception e) {
                browserLabel.setText(NO_CONNECTION);
                loadDefaultPage();
            }
        });

        loadDefaultPage();
    }

    /**
     * Returns true if person cannot load Google Map due to null address.
     */
    private boolean personWontLoadMaps(Person newValue) {
        return newValue instanceof Buyer || newValue instanceof Tenant
                || (newValue instanceof Seller && ((Seller) newValue).getAddress().value == null)
                || (newValue instanceof Landlord && ((Landlord) newValue).getAddress().value == null);
    }

    /**
     * Loads the Google Map search page with the person's address as input.
     */
    private void loadMapsPage(Person person) {
        if (person instanceof Seller) {
            loadPage(SEARCH_PAGE_URL + ((Seller) person).getAddress().value);
        } else if (person instanceof Landlord) {
            loadPage(SEARCH_PAGE_URL + ((Landlord) person).getAddress().value);
        } else {
            browserLabel.setText(NO_ADDRESS_AVAILABLE);
            loadDefaultPage();
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
