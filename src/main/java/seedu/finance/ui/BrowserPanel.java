package seedu.finance.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.finance.MainApp;
import seedu.finance.commons.core.LogsCenter;
import seedu.finance.model.budget.Budget;
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
    private Label totalBudget;

    @FXML
    private Label currentBudget;

    @FXML
    private Label currentSpending;

    public BrowserPanel(ObservableValue<Record> selectedRecord, Budget budget) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        updateBudget(budget);

        // Load record page when selected record changes.
        selectedRecord.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                //loadDefaultPage();
                return;
            }
            //loadRecordPage(newValue);
        });
    }

    /**
     * Method to update Budget
     * @param budget the budget to be updated to.
     */
    public void updateBudget(Budget budget) {
        String totalBudgetString = String.format("%.2f", budget.getTotalBudget());
        String currentBudgetString = String.format("%.2f", budget.getCurrentBudget());
        String currentSpendingString = String.format("%.2f", budget.getCurrentSpendings());
        this.totalBudget.textProperty().setValue("Total Budget: " + totalBudgetString);
        this.currentBudget.textProperty().setValue("Current Budget: " + currentBudgetString);
        this.currentSpending.textProperty().setValue("Current Spendings: " + currentSpendingString);
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
