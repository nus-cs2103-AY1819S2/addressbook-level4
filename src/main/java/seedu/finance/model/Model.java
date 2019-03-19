package seedu.finance.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.finance.commons.core.GuiSettings;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Record;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Record> PREDICATE_SHOW_ALL_RECORD = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' finance tracker file path.
     */
    Path getFinanceTrackerFilePath();

    /**
     * Sets the user prefs' finance tracker file path.
     */
    void setFinanceTrackerFilePath(Path financeTrackerFilePath);

    /**
     * Replaces finance tracker data with the data in {@code financeTracker}.
     */
    void setFinanceTracker(ReadOnlyFinanceTracker financeTracker);

    /** Returns the FinanceTracker */
    ReadOnlyFinanceTracker getFinanceTracker();

    /**
     * Returns true if a record with the same identity as {@code record} exists in the finance tracker.
     */
    boolean hasRecord(Record record);

    /**
     * Deletes the given record.
     * The record must exist in the finance tracker.
     */
    void deleteRecord(Record target);

    /**
     * Adds the given record.
     * {@code record} must not already exist in the finance tracker.
     */
    void addRecord(Record record);

    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     * {@code target} must exist in the finance tracker.
     * The record identity of {@code editedRecord} must not be the same as
     * another existing record in the finance tracker.
     */
    void setRecord(Record target, Record editedRecord);

    /**
     * Returns true if a {@code budget} exists in the finance tracker.
     */
    boolean hasBudget();

    /**
     * Sets the given amount to budget.
     * {@code budget} must not already exist in the finance tracker.
     */
    void addBudget(Amount amount);

    /** Returns the amount value of {@code budget} in a ObjectProperty wrapper */
    ObjectProperty<Amount> getBudget();

    /** Returns an unmodifiable view of the filtered record list */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);

    /**
     * Returns true if the model has previous finance tracker states to restore.
     */
    boolean canUndoFinanceTracker();

    /**
     * Returns true if the model has undone finance tracker states to restore.
     */
    boolean canRedoFinanceTracker();

    /**
     * Restores the model's finance tracker to its previous state.
     */
    void undoFinanceTracker();

    /**
     * Restores the model's finance tracker to its previously undone state.
     */
    void redoFinanceTracker();

    /**
     * Saves the current finance tracker state for undo/redo.
     */
    void commitFinanceTracker();

    /**
     * Selected record in the filtered record list.
     * null if no record is selected.
     */
    ReadOnlyProperty<Record> selectedRecordProperty();

    /**
     * Returns the selected record in the filtered record list.
     * null if no record is selected.
     */
    Record getSelectedRecord();

    /**
     * Sets the selected record in the filtered record list.
     */
    void setSelectedRecord(Record record);
}
