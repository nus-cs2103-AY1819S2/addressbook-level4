package seedu.finance.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.finance.commons.util.InvalidationListenerManager;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Record;
import seedu.finance.model.record.UniqueRecordList;

/**
 * Wraps all data at the finance-tracker level
 * Duplicates are not allowed (by .isSameRecord comparison)
 */
public class FinanceTracker implements ReadOnlyFinanceTracker {

    private final UniqueRecordList records;
    private final Budget budget;
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
        budget = new Budget();
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
     * {@code records} must not contain duplicate records.
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
     * The record must not already exist in the finance tracker.
     */
    public void addRecord(Record r) {
        records.add(r);
        indicateModified();
    }

    /**
     * Replaces the given record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the finance tracker.
     * The record identity of {@code editedRecord} must not be the same as
     * another existing record in the finance tracker.
     */
    public void setRecord(Record target, Record editedRecord) {
        requireNonNull(editedRecord);

        records.setRecord(target, editedRecord);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the finance tracker.
     */
    public void removeRecord(Record key) {
        records.remove(key);
        indicateModified();
    }

    /// budget-level operations

    /**
     * Returns true if a {@code budget} exists in the finance tracker.
     */
    public boolean hasBudget() {
        return this.budget.isSet();
    }

    /**
     * Adds a budget to the finance tracker.
     * The budget must not already exist in the finance tracker.
     */
    public void addBudget(Amount amount) {
        this.budget.set(amount);
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

    //// util methods

    @Override
    public String toString() {
        return records.asUnmodifiableObservableList().size() + " records";
        // TODO: refine later
    }

    public ObjectProperty<Amount> getBudget() {
        return budget.valueProperty();
    }

    @Override
    public ObservableList<Record> getRecordList() {
        return records.asUnmodifiableObservableList();
    }


    public void reverseRecordList(){
        records.reverseList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinanceTracker // instanceof handles nulls
                && records.equals(((FinanceTracker) other).records));
    }

    @Override
    public int hashCode() {
        return records.hashCode();
    }
}
