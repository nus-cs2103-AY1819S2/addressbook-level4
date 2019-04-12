package seedu.finance.model;

import java.util.HashSet;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.record.Record;

/**
 * Unmodifiable view of an finance tracker
 */
public interface ReadOnlyFinanceTracker extends Observable {

    /**
     * Returns an unmodifiable view of the records list.
     * This list will not contain any duplicate records.
     */
    ObservableList<Record> getRecordList();

    TotalBudget getBudget();

    HashSet<CategoryBudget> getCategoryBudget();

    boolean isSetFile();

}
