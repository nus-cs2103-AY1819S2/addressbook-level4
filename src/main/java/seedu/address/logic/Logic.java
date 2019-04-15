package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

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

    /**
     * Returns the ArchiveBook.
     *
     * @see seedu.address.model.Model#getArchiveBook()
     */
    ReadOnlyAddressBook getArchiveBook();

    /**
     * Returns the PinBook.
     *
     * @see seedu.address.model.Model#getPinBook()
     */
    ReadOnlyAddressBook getPinBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of archived persons */
    ObservableList<Person> getFilteredArchivedPersonList();

    /** Returns an unmodifiable view of the filtered list of pinned persons */
    ObservableList<Person> getFilteredPinList();

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
     * Returns the user prefs' archive book file path.
     */
    Path getArchiveBookFilePath();

    /**
     * Returns the user prefs' pin book file path.
     */
    Path getPinBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     *
     * @see seedu.address.model.Model#selectedPersonProperty()
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Sets the selected person in the filtered person list.
     *
     * @see seedu.address.model.Model#setSelectedPerson(Person)
     */
    void setSelectedPerson(Person person);

    /**
     * Selected pin person in the filtered pin list.
     * null if no person is selected.
     *
     * @see seedu.address.model.Model#selectedPinPersonProperty()
     */
    ReadOnlyProperty<Person> selectedPinPersonProperty();

    /**
     * Sets the selected person in the filtered pin list.
     *
     * @see seedu.address.model.Model#setSelectedPinPerson(Person)
     */
    void setSelectedPinPerson(Person person);

    /**
     * Selected archived person in the filtered archived person list.
     * null if no archived person is selected.
     *
     * @see seedu.address.model.Model#selectedArchivedPersonProperty()
     */
    ReadOnlyProperty<Person> selectedArchivedPersonProperty();

    /**
     * Sets the selected archived person in the filtered archived person list.
     *
     * @see seedu.address.model.Model#setSelectedArchivedPerson(Person)
     */
    void setSelectedArchivedPerson(Person person);

    /**
     * Removes the selection of person in the filtered person list.
     */
    void removeSelectedPerson();

    /**
     * Removes the selection of archived person in the filtered archived person list.
     */
    void removeSelectedArchivedPerson();

}
