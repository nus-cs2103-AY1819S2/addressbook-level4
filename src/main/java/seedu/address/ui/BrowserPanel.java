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
import seedu.address.model.ClassForPrinting;

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

    public BrowserPanel(ObservableValue<ClassForPrinting> selectedPerson) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load moduleTaken page when selected moduleTaken changes.
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadBrowerPanelPage(newValue);
        });

        loadDefaultPage();
    }

    /**
     * Loads a class for printing into the browser panel page.
     * If the class for printing is a ModuleTaken, it will print the details which includes the workload information
     * that is not shown in the person card.
     * If the class is a LimitChecker, it will print the generated checked report.
     */
    private void loadBrowerPanelPage(ClassForPrinting classForPrinting) {
        loadContent(classForPrinting.getPrintable());
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads the given String to be printed on the BrowserPanel.
     */
    public void loadContent(String toPrint) {
        Platform.runLater(() -> browser.getEngine().loadContent("<html><span style='white-space: pre-line'>" + toPrint
                + "</span></html>"));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

}
