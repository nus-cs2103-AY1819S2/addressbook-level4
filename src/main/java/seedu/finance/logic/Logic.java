package seedu.finance.logic;

import java.nio.file.Path;
import java.util.LinkedHashMap;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.finance.commons.core.GuiSettings;
import seedu.finance.logic.commands.CommandResult;
import seedu.finance.logic.commands.SummaryCommand.SummaryPeriod;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.record.Record;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the amount value of {@code budget} with an ObjectProperty wrapper.
     *
     * @see seedu.finance.model.Model#getBudget()
     */
    TotalBudget getBudget();

    /**
     * Returns the FinanceTracker.
     *
     * @see seedu.finance.model.Model#getFinanceTracker()
     */
    ReadOnlyFinanceTracker getFinanceTracker();

    /** Returns an unmodifiable view of the filtered list of Records */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' finance tracker file path.
     */
    Path getFinanceTrackerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected record in the filtered record list.
     * null if no record is selected.
     *
     * @see seedu.finance.model.Model#selectedRecordProperty()
     */
    ReadOnlyProperty<Record> selectedRecordProperty();

    /**
     * Sets the selected record in the filtered record list.
     *
     * @see seedu.finance.model.Model#setSelectedRecord(Record)
     */
    void setSelectedRecord(Record record);

    /**
     * Returns a map of records with key and value pair representing data for the summary chart
     */
    LinkedHashMap<String, Double> getRecordSummary();

    /**
     * @return a {@code SummaryPeriod} representing the current period of summary data
     */
    SummaryPeriod getSummaryPeriod();

    /**
     * @return an int representing the user selected number of days or months
     */
    int getPeriodAmount();
}
