package seedu.finance.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.finance.commons.core.GuiSettings;
import seedu.finance.logic.commands.SummaryCommand.SummaryPeriod;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.finance.model.exceptions.SpendingInCategoryBudgetExceededException;
import seedu.finance.model.record.Record;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
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

    /**
     * Returns the FinanceTracker
     */
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
     */
    boolean addRecord(Record record);

    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     */
    void setRecord(Record target, Record editedRecord);

    /**
     * Sets the given amount to budget.
     * {@code budget} must not already exist in the finance tracker.
     */
    void addBudget(Budget budget) throws CategoryBudgetExceedTotalBudgetException;

    /**
     * Sets the given amount to the category budget
     */
    void addCategoryBudget(CategoryBudget budget) throws CategoryBudgetExceedTotalBudgetException,
            SpendingInCategoryBudgetExceededException;

    /**
     * Returns the hashset of category budgets
     */
    HashSet<CategoryBudget> getCatBudget();
    /**
     * Returns an unmodifiable view of the filtered record list in reverse order.
     */
    void reverseFilteredRecordList();

    /**
     * Sorts the filtered record list according to comparison function in {@code comparator}.
     */
    void sortFilteredRecordList(Comparator<Record> comparator);

    /**
     * Returns the amount value of {@code budget} in a ObjectProperty wrapper
     */
    TotalBudget getBudget();

    /**
     * Returns an unmodifiable view of the filtered record list
     */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);

    /**
     * Adds the path to the data file before it is switched out by {@code SetFileCommand}.
     */
    void addPreviousDataFile(Path path);

    /**
     * Removes the most recently used previous data file.
     *
     * @return Path of most recently used data file.
     */
    Path removePreviousDataFile();

    /**
     * Adds the path to the data file before it is switched out by {@code UndoCommand}.
     *
     * @param path the most recent data file before undo.
     */
    void addUndoPreviousDataFile(Path path);

    /**
     * Removes the most recently undo-ed previous data file.
     *
     * @return Path of most recently undo-ed data file.
     */
    Path removeUndoPreviousDataFile();

    /**
     * Changes the FinanceTracker data to the one stored in the specified path.
     *
     * @param path The path to the data file to revert to.
     */
    void changeFinanceTrackerFile(Path path);

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
     * Saves the current finance tracker state for undo/redo.
     * Checks if command is a setFile command.
     */
    void commitFinanceTracker(boolean isSetFile);

    /**
     * Returns the boolean for whether the previous command is a {@code setFileCommand}.
     */
    boolean isSetFile();

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

    /**
     * Updates summaryPeriod to the given {@code period}.
     */
    void updateSummaryPeriod(SummaryPeriod period);

    /**
     * Returns summaryPeriod.
     */
    SummaryPeriod getSummaryPeriod();

    /**
     * Updates summaryNoOfDays to the given {@code periodAmount}.
     */
    void updatePeriodAmount(int periodAmount);

    /**
     * Returns summaryNoOfDaysOrMonths.
     */
    int getPeriodAmount();

    /**
     * Returns an unmodifiable view of expenses which fulfill the statistics filter
     *
     * @return {@code ObservableList<Record>} of expenses which fulfill summary filter
     */
    ObservableList<Record> getRecordSummary();

    /**
     * Updates the predicate used for record summary
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateRecordSummaryPredicate (Predicate<Record> predicate) throws NullPointerException;
}
