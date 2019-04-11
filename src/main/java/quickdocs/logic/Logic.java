package quickdocs.logic;

import java.util.ArrayList;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import quickdocs.commons.core.GuiSettings;
import quickdocs.logic.commands.CommandResult;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.reminder.Reminder;


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
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();
    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    ObservableList<Reminder> getFilteredReminderList();

    ReadOnlyProperty<Reminder> selectedReminderProperty();

    void setSelectedReminder(Reminder reminder);

    ArrayList<String> getDirectorySuggestions(String path);

    ArrayList<String> getMedicineSuggestions(String path);

    boolean isDirectoryFormat(String rawArgs);

    boolean isMedicineAllowed(String rawArgs);
}
