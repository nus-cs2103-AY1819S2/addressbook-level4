package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Record> PREDICATE_SHOW_ALL_RECORDS = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;
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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    //=========== Methods for interacting with Person Object ===========================================================

    /**
     * Replaces the person list wih the data in {@code persons}.
     */
    void setPatientList(List<Person> persons);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Sorts the address book according to the given comparator
     */
    void sortAddressBook(Comparator<Patient> patientComparator, boolean isReverse);

    void sortTasks(Comparator<Task> c);

    /**
     * Sorts the records within address book according to the given comparator
     */
    void sortRecordsBook(Comparator<Record> recordComparator, boolean isReverse);

    /**
     * Returns true if the model has previous address book states to restore.
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    Person getSelectedPerson();

    /**
     * Sets the selected person in the filtered person list.
     */
    void setSelectedPerson(Person person);

    boolean checkNoCopy();

    //=========== Methods for interacting with Task Object ===========================================================

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns true if a task with the same identity as {@code task} exists in the address book.
     */
    boolean hasTask(Task task);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the address book.
     */
    void addTask(Task task);
    /**
     * Deletes the given task.
     * The task must exist in the address book.
     */
    void deleteTask(Task task);
    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedTask} must not be the same as another existing task in the address book.
     */
    void setTask(Task target, Task editedTask);

    //=========== Methods for interacting with Record Object ===========================================================

    /** Returns an unmodifiable view of the filtered record list */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Updates the filtered records list.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);

    /**
     * Returns true if a record with the same identity as {@code record} exists in the address book.
     */
    boolean hasRecord(Record record);

    /**
     * Adds the given record.
     * {@code record} must not already exist in the address book.
     */
    void addRecord(Record record);

    /**
     * Deletes the given record.
     * The record must exist in the address book.
     */
    void deleteRecord(Record record);

    /**
     * Returns true if the model has previous address book states to restore.
     * Selected record in the filtered record list.
     * null if no record is selected.
     */
    ReadOnlyProperty<Record> selectedRecordProperty();

    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedRecord} must not be the same as another existing record in the address book.
     */
    void setRecord(Record target, Record editedRecord);

    /**
     * Returns the selected record in the filtered records list.
     * null if no record is selected.
     */
    Record getSelectedRecord();

    /**
     * Sets the selected record in the filtered record list.
     */
    void setSelectedRecord(Record record);

    //=========== Tags =================================================================================

    /**
     * Update tags based on teeth data.
     * @param patient the patient to update tags.
     */
    void updateTags(Patient patient);

    //=========== Undo/Redo/Commit =================================================================================

    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

}
