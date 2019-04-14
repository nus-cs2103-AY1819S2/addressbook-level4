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
import seedu.equipment.model.WorkList;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;

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

    /** Returns an unmodifiable view of the filtered list of equipment */
    ObservableList<Equipment> getFilteredEquipment();

    /** Returns an unmodifiable view of the filtered list of WorkLists */
    ObservableList<WorkList> getFilteredWorkListList();

    /** Returns an unmodifiable view of the filtered list of client */
    ObservableList<Name> getFilteredClientList();

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
     * Selected WorkList in the filtered WorkList list.
     * null if no WorkList is selected.
     *
     * @see Model#selectedWorkListProperty()
     */
    ReadOnlyProperty<WorkList> selectedWorkListProperty();

    /**
     * Selected client in the filtered client list.
     * null if no client is selected.
     *
     * @see Model#selectedClientProperty()
     */
    ReadOnlyProperty<Name> selectedClientProperty();


    /**
     * Sets the selected equipment in the filtered equipment list.
     *
     * @see Model#setSelectedEquipment(Equipment)
     */
    void setSelectedPerson(Equipment equipment);

    /**
     * Sets the selected client in the filtered client list.
     *
     * @see Model#setSelectedClient(Name)
     */
    void setSelectedClient(Name name);


    /**
     * Gets the selected client in the filtered client list.
     *
     * @see Logic#getSelectedClient()
     */
    Name getSelectedClient();

    /**
     * Sets the selected WorkList in the filtered WorkList list.
     *
     * @see Model#setSelectedWorkList(WorkList)
     */
    void setSelectedWorkList(WorkList workList);
}
