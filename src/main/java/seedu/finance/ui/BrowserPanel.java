package seedu.finance.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.finance.MainApp;
import seedu.finance.commons.core.LogsCenter;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Record;

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

    @FXML
    private Label budget;

    public BrowserPanel(ObservableValue<Record> selectedRecord, ObjectProperty<Amount> budgetData) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        //this.budget.textProperty().setValue("No Budget Yet");
        /*budgetData.addListener((observable, oldValue, newValue) -> {
            this.budget.textProperty().setValue(newValue.toString());
        });*/

        // Load record page when selected record changes.
        selectedRecord.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadRecordPage(newValue);
        });

        loadDefaultPage();
    }

    private void loadRecordPage(Record record) {
        loadPage(SEARCH_PAGE_URL + record.getName().fullName);
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
