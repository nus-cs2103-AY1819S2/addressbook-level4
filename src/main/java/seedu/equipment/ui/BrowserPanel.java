package seedu.equipment.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.equipment.commons.core.LogsCenter;
import seedu.equipment.model.equipment.Equipment;

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
        String url = MAP_PAGE_BASE_URL;
        double[] coordiantes = equipment.getCoordiantes();
        if (coordiantes != null) {
            url = MAP_PAGE_BASE_URL + "?coordinates=[[" + coordiantes[0] + ","
                    + coordiantes[1] + "]]&title=[\"" + equipment.getName()
                    + "\"]&icon=[\"monument\"]";
        }
        System.out.println("Loading page: " + url);
        loadPage(url);
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
