package seedu.finance.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.finance.commons.util.InvalidationListenerManager;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.finance.model.exceptions.SpendingInCategoryBudgetExceededException;
import seedu.finance.model.record.Record;
import seedu.finance.model.record.UniqueRecordList;

/**
 * Wraps all data at the finance-tracker level
 */
public class FinanceTracker implements ReadOnlyFinanceTracker {

    private final UniqueRecordList records;
    private final TotalBudget budget;
    private boolean isSetFile;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        records = new UniqueRecordList();
        budget = new TotalBudget();
        isSetFile = false;
    }

    public FinanceTracker() {}

    /**
     * Creates an FinanceTracker using the Records in the {@code toBeCopied}
     */
    public FinanceTracker(ReadOnlyFinanceTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the record list with {@code records}.
     * {@code records} can contain duplicate records.
     */
    public void setRecords(List<Record> records) {
        this.records.setRecords(records);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code FinanceTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyFinanceTracker newData) {
        requireNonNull(newData);

        setRecords(newData.getRecordList());
        this.budget.set(newData.getBudget());
        setIsSetFile(newData.isSetFile());
    }

    //// record-level operations

    /**
     * Returns true if a record with the same identity as {@code record} exists in the finance tracker.
     */
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return records.contains(record);
    }

    /**
     * Adds a record to the finance tracker.
     */
    public boolean addRecord(Record r) {
        records.add(r);
        boolean budgetNotExceeded = budget.addRecord(r);
        indicateModified();
        return budgetNotExceeded;
    }

    /**
     * Replaces the given record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the finance tracker.
     */
    public void setRecord(Record target, Record editedRecord) {
        requireNonNull(editedRecord);
        records.setRecord(target, editedRecord);
        budget.editRecord(target, editedRecord);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the finance tracker.
     */
    public void removeRecord(Record key) {
        records.remove(key);
        budget.removeRecord(key);
        indicateModified();
    }

    /// budget-level operations

    /**
     * Adds a budget to the finance tracker.
     * The budget must not be less than the allocated budgets.
     */
    public void addBudget(Budget budget) throws CategoryBudgetExceedTotalBudgetException {
        this.budget.updateBudget(budget, this.records.asUnmodifiableObservableList());
        indicateModified();
    }

    // =============================== Set File Operations =========================================================

    public void setIsSetFile(boolean isSetFile) {
        this.isSetFile = isSetFile;
    }

    public boolean isSetFile() {
        return isSetFile;
    }

    // =============================== Category Budget =============================================================
    //@author Jackimaru96

    public TotalBudget getBudget() {
        return budget;
    }

    public HashSet<CategoryBudget> getCategoryBudget() {
        return this.budget.getCategoryBudgets();
    }

    /**
     * Adds category budget
     * @param catBudget the category budget to be added
     * @throws CategoryBudgetExceedTotalBudgetException if adding this cat budget will exceed total budget
     * @throws SpendingInCategoryBudgetExceededException if the spending in this category is more than allocated
     *                                                   cat budget
     */
    public void addCategoryBudget(CategoryBudget catBudget) throws CategoryBudgetExceedTotalBudgetException,
            SpendingInCategoryBudgetExceededException {
        this.budget.setNewCategoryBudget(catBudget, records);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the finance tracker has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// comparator methods

    @Override
    public String toString() {
        return records.asUnmodifiableObservableList().size() + " records"
                + "budget: " + budget;
    }

    @Override
    public ObservableList<Record> getRecordList() {
        return records.asUnmodifiableObservableList();
    }


    public void reverseRecordList() {
        records.reverseList();
    }

    public void sortRecordList(Comparator<Record> comparator) {
        records.sortList(comparator);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinanceTracker // instanceof handles nulls
                && records.equals(((FinanceTracker) other).records)
                && budget.equals(((FinanceTracker) other).budget));
    }

    @Override
    public int hashCode() {
        return Objects.hash(records, budget);
    }
}
