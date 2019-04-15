package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.record.Record;
import seedu.address.model.record.exceptions.RecordNotFoundException;
import seedu.address.model.task.Task;
import seedu.address.ui.MainWindow;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Task> filteredTasks;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Record> selectedRecord = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Task> selectedTask = new SimpleObjectProperty<>();

    private FilteredList<Record> filteredRecords;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredTasks = new FilteredList<>(versionedAddressBook.getTaskList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);
        filteredTasks.addListener(this::ensureSelectedTaskIsValid);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    ////Person object specific methods
    @Override
    public void setPatientList(List<Person> persons) {
        versionedAddressBook.setPersons(persons);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.setPerson(target, editedPerson);
    }

    ////Task Object specific methods
    @Override
    public boolean hasTask(Task task) {
        requireAllNonNull(task);
        return versionedAddressBook.hasTask(task);
    }

    @Override
    public void addTask(Task task) {
        versionedAddressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void deleteTask(Task task) {
        versionedAddressBook.removeTask(task);
    }

    @Override
    public void setTask(Task task, Task editedTask) {
        versionedAddressBook.setTask(task, editedTask);
    }

    // For records manipulation methods.

    /**
     * Returns true if a record with the same identity as {@code record} exists in the address book.
     *
     * @param record the record to be checked whether exist.
     */
    @Override
    public boolean hasRecord(Record record) {
        requireAllNonNull(record);
        return versionedAddressBook.hasRecord(record);
    }

    /**
     * Adds the given record.
     * {@code record} must not already exist in the address book.
     *
     * @param record the record to be added.
     */
    @Override
    public void addRecord(Record record) {
        if (MainWindow.getRecordPatient() != null) {
            versionedAddressBook.addRecord(record);
        }
    }

    /**
     * Deletes the given record.
     * The record must exist in the address book.
     *
     * @param record the record to be deleted.
     */
    @Override
    public void deleteRecord(Record record) {
        if (MainWindow.getRecordPatient() != null) {
            versionedAddressBook.removeRecord(record);
        }
    }

    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedRecord} must not be the same as another existing record in the address book.
     *
     * @param target the target to be replaced.
     * @param editedRecord the one which is edited.
     */
    @Override
    public void setRecord(Record target, Record editedRecord) {
        versionedAddressBook.setRecord(target, editedRecord);
    }


    @Override
    public ReadOnlyProperty<Record> selectedRecordProperty() {
        return selectedRecord;
    }

    @Override
    public Record getSelectedRecord() {
        return selectedRecord.getValue();
    }

    @Override
    public void setSelectedRecord(Record record) {
        if (record != null && !filteredRecords.contains(record)) {
            throw new RecordNotFoundException();
        }
        selectedRecord.set(record);
    }

    /**
     * Update tags based on teeth data.
     *
     * @param target the patient to update tags.
     */
    @Override
    public void updateTags(Patient target) {
        Patient editedTarget = target;
        versionedAddressBook.setPerson(target, editedTarget);
        MainWindow.setRecordPatient(editedTarget);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Sorting Methods ===========================================================================

    /**
     * Sorts the patients within address book according to the given comparator
     */
    @Override
    public void sortAddressBook(Comparator<Patient> compPa, boolean isReverse) {
        requireNonNull(compPa);
        versionedAddressBook.sortPatients(compPa, isReverse);
    }

    @Override
    public void sortTasks(Comparator<Task> c) {
        versionedAddressBook.sortTasks(c);
    }

    /**
     * Sorts the records within address book according to the given comparator
     */
    @Override
    public void sortRecordsBook(Comparator<Record> compRec, boolean isReverse) {
        requireNonNull(compRec);
        versionedAddressBook.sortRecords(compRec, isReverse);
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }


    /**
     * Ensures {@code selectedTask} is a valid task in {@code filteredTasks}.
     */
    private void ensureSelectedTaskIsValid(ListChangeListener.Change<? extends Task> change) {
        while (change.next()) {
            if (selectedTask.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedTaskReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedTask.getValue());
            if (wasSelectedTaskReplaced) {
                // Update selectedTask to its new value.
                int index = change.getRemoved().indexOf(selectedTask.getValue());
                selectedTask.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedTaskRemoved = change.getRemoved().stream()
                    .anyMatch(removedTask -> selectedTask.getValue().isSameTask(removedTask));
            if (wasSelectedTaskRemoved) {
                // Select the task that came before it in the list,
                // or clear the selection if there is no such person.
                selectedTask.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    //=========== Filtered Record List Accessors ============================================================

    /**
     * Returns an unmodifiable view of the filtered record list
     */
    @Override
    public ObservableList<Record> getFilteredRecordList() {
        if (MainWindow.getRecordPatient() != null) {
            versionedAddressBook.setRecords(MainWindow.getRecordPatient().getRecords());
            if (filteredRecords == null) {
                filteredRecords = new FilteredList<>(versionedAddressBook.getRecordList());
            }
            return filteredRecords;
        }
        return null;
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        if (MainWindow.getRecordPatient() != null) {
            requireNonNull(predicate);
            filteredRecords.setPredicate(predicate);
        }
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public Person getSelectedPerson() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedPerson(Person person) {
        if (person != null && !filteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean checkNoCopy() {
        return versionedAddressBook.checkNoCopy();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredTasks.equals(other.filteredTasks)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
