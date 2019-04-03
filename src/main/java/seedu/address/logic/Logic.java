package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.MapGrid;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.cell.Cell;

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
     * Returns the MapGrid.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

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

    /**
     * Used for the Ui to listen to and trigger changes.
     */
    ObservableBooleanValue getHumanMapObservable();

    /**
     * Used for the Ui to listen to and trigger changes.
     */
    ObservableBooleanValue getEnemyMapObservable();

    /**
     * Returns the player map grid
     */
    MapGrid getHumanMapGrid();

    /**
     * Returns the enemy map grid
     */
    MapGrid getEnemyMapGrid();

    /**
     * Sets the selected cell in the filtered cell list.
     *
     * @see seedu.address.model.Model#setSelectedPerson(Cell)
     */
    void setSelectedPerson(Cell cell);
}
