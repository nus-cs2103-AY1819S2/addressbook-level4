package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.medicine.Medicine;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;

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
     * Returns the Inventory.
     *
     * @see seedu.address.model.Model#getInventory()
     */
    ReadOnlyInventory getInventory();

    /** Return the accessor to predicates used in the warning pane*/
    WarningPanelPredicateAccessor getWarningPanelPredicateAccessor();

    /** Returns an unmodifiable view of the filtered list of medicines for medicine pane*/
    ObservableList<Medicine> getFilteredMedicineList();

    /** Returns an unmodifiable view of the filtered list of medicines that are expiring for warning pane*/
    ObservableList<Medicine> getExpiringMedicinesList();

    /** Returns an unmodifiable view of the filtered list of medicines with low quantity for warning pane*/
    ObservableList<Medicine> getLowQuantityMedicinesList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' inventory file path.
     */
    Path getInventoryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected medicine in the filtered medicine list.
     * null if no medicine is selected.
     *
     * @see seedu.address.model.Model#selectedMedicineProperty()
     */
    ReadOnlyProperty<Medicine> selectedMedicineProperty();

    /**
     * Sets the selected medicine in the filtered medicine list.
     *
     * @see seedu.address.model.Model#setSelectedMedicine(Medicine)
     */
    void setSelectedMedicine(Medicine medicine);
}
