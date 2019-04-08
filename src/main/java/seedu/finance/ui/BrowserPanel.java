package seedu.finance.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.finance.commons.core.LogsCenter;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.record.Record;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

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

}
