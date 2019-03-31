package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Patient;

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
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of patients */
    ObservableList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered list of medical histories */
    ObservableList<MedicalHistory> getFilteredMedHistList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected patient in the filtered patient list.
     * null if no patient is selected.
     *
     * @see seedu.address.model.Model#selectedPatientProperty()
     */
    ReadOnlyProperty<Patient> selectedPatientProperty();

    /**
     * Selected medical history in the filtered medHist list.
     * null if no medHist is selected.
     *
     * @see seedu.address.model.Model#selectedMedHistProperty()
     */
    ReadOnlyProperty<MedicalHistory> selectedMedHistProperty();

    /**
     * Sets the selected patient in the filtered patient list.
     *
     * @see seedu.address.model.Model#setSelectedPatient(Patient)
     */
    void setSelectedPatient(Patient patient);

    /**
     * Sets the selected medical history in the filtered medHist list.
     *
     * @see seedu.address.model.Model#setSelectedMedHist(MedicalHistory)
     */
    void setSelectedMedHist(MedicalHistory medHist);
}
