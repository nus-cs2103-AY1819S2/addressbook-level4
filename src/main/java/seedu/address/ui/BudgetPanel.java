package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.epiggy.Budget;

/**
 * Panel containing the budget status.
 */
public class BudgetPanel extends UiPart<Region> {
    private static final String FXML = "BudgetPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BudgetPanel.class);

    @FXML
    private ListView<Budget> budgetView;

    public BudgetPanel(ObservableList<Budget> budgetList, Consumer<Budget> setCurrentBudget) {
        super(FXML);
        budgetView.setItems(budgetList);
        budgetView.setCellFactory(listView -> new BudgetListViewCell());
        //        budget.addListener((observable, oldValue, newValue) -> {
        //            logger.fine("Budget changed to: " + newValue);
        //            // Don't modify selection if we are already selecting the selected expense,
        //            // otherwise we would have an infinite loop.
        //            if (Objects.equals(budgetView.getSelectionModel().getSelectedItem(), newValue)) {
        //                return;
        //            }
        //
        //            if (newValue == null) {
        //                budgetView.getSelectionModel().clearSelection();
        //            } else {
        //                int index = budgetView.getItems().indexOf(newValue);
        //                budgetView.scrollTo(index);
        //                budgetView.getSelectionModel().clearAndSelect(index);
        //            }
        //        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Budget} using a {@code ExpenseCard}.
     */
    class BudgetListViewCell extends ListCell<Budget> {
        @Override
        protected void updateItem(Budget budget, boolean empty) {
            super.updateItem(budget, empty);

            if (empty || budget == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BudgetCard(getIndex() + 1, budget).getRoot());
            }
        }
    }

}
