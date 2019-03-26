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
import seedu.address.model.request.Request;

/**
 * The Map Panel of the App.
 * Responsible for rendering the map in JavaFX's StackPane.
 * Map is rendered via URL request to gothere.sg.
 * For more info, visit https://gothere.sg/api/maps/staticmaps.html.
 * @author Hui Chun
 */
public class InfoPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final URL DISPLAY_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "DisplayPage.fxml"));

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

            if (newValue == null) {
                loadDefaultPage();
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


    public void loadPage(String url) {
        Platform.runLater(() -> webView.getEngine().load(url));
    }

}
