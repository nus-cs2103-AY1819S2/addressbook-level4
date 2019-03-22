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
import seedu.address.model.person.Person;

/**
 * The Map Panel of the App.
 * Responsible for rendering the map in JavaFX's StackPane.
 * Map is rendered via URL request to gothere.sg.
 * For more info, visit https://gothere.sg/api/maps/staticmaps.html.
 * @author Hui Chun
 */
public class MapPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String MAP_URL = "https://gothere.sg/maps/staticmap?center=%22";

    private static final String FXML = "MapPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView map;

    public MapPanel(ObservableValue<Person> selectedPatient) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Zooms in to the patient's location when the selected person changes.
        selectedPatient.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            // Removed loadPatientLocation method
        });

        loadDefaultPage();
    }

    /**
     * Loads a default HTML file that displays the welcome message and starter tips.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }


    public void loadPage(String url) {
        Platform.runLater(() -> map.getEngine().load(url));
    }


    /**
     * Constructs a URL from the mapAddress input by concatenating additional URL parameters.
     * @param mapAddress the street address
     * @return a URL to render the map
     */
    private String constructLink(String mapAddress) {
        // https://gothere.sg/maps/staticmap?center=%22bedok%20north%20street%203%22&zoom=15&
        // size=400x300&markers=%22bedok%20north%20street%203%22,red&sensor=false;
        StringBuilder builder = new StringBuilder(MAP_URL);
        builder.append(mapAddress + "%22&zoom=16&size=640x395&markers=%22");
        builder.append(mapAddress + ",red&sensor=false");
        logger.info(builder.toString());
        return builder.toString();

    }
}
