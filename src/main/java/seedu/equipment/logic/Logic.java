package seedu.equipment.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.equipment.commons.core.GuiSettings;
import seedu.equipment.logic.commands.CommandResult;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.Model;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.equipment.Equipment;

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
     * Returns the EquipmentManager.
     *
     * @see Model#getEquipmentManager()
     */
    ReadOnlyEquipmentManager getEquipmentManager();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Equipment> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' equipment book file path.
     */
    Path getEquipmentManagerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected equipment in the filtered equipment list.
     * null if no equipment is selected.
     *
     * @see Model#selectedEquipmentProperty()
     */
    ReadOnlyProperty<Equipment> selectedEquipmentProperty();

    /**
     * Sets the selected equipment in the filtered equipment list.
     *
     * @see Model#setSelectedEquipment(Equipment)
     */
    void setSelectedPerson(Equipment equipment);
}
