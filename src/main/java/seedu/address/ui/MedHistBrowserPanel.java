package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javax.swing.plaf.synth.Region;
import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.medicalhistory.MedicalHistory;

public class MedHistBrowserPanel extends UiPart<Region>{
    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";

    private static final String FXML = "BrowserMedHistPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public MedHistBrowserPanel(ObservableValue<MedicalHistory> selectedMedHist) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load patient page when selected patient changes.
        selectedMedHist.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadMedHistPage(newValue);
        });

        loadDefaultPage();
    }

    private void loadMedHistPage(MedicalHistory medHist) {
        loadPage(SEARCH_PAGE_URL + medHist.getName().fullName);
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
