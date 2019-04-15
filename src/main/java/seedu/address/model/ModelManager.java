package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final VersionedAddressBook versionedArchiveBook;
    private final VersionedAddressBook versionedPinBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Person> filteredArchivedPersons;
    private final FilteredList<Person> filteredPinnedPersons;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Person> selectedPinPerson = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Person> selectedArchivedPerson = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook, archiveBook, pinBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook archiveBook,
                        ReadOnlyAddressBook pinBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, archiveBook, pinBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + "Initializing with archive book: " + archiveBook
                + "Initializing with pin book" + pinBook
                + "and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedArchiveBook = new VersionedAddressBook(archiveBook);
        versionedPinBook = new VersionedAddressBook(pinBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);
        filteredArchivedPersons = new FilteredList<>(versionedArchiveBook.getPersonList());
        filteredArchivedPersons.addListener(this::ensureSelectedArchivedPersonIsValid);
        filteredPinnedPersons = new FilteredList<>(versionedPinBook.getPersonList());
        filteredPinnedPersons.addListener(this::ensureSelectedPinPersonIsValid);
    }

    public ModelManager() {
        this(new AddressBook(), new AddressBook(), new AddressBook(), new UserPrefs());
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

    @Override
    public Path getArchiveBookFilePath() {
        return userPrefs.getArchiveBookFilePath();
    }

    @Override
    public void setArchiveBookFilePath(Path archiveBookFilePath) {
        requireNonNull(archiveBookFilePath);
        userPrefs.setArchiveBookFilePath(archiveBookFilePath);
    }

    @Override
    public Path getPinBookFilePath() {
        return userPrefs.getPinBookFilePath();
    }

    @Override
    public void setPinBookFilePath(Path pinBookFilePath) {
        requireNonNull(pinBookFilePath);
        userPrefs.setPinBookFilePath(pinBookFilePath);
    }

    //=========== AddressBook ================================================================================

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
    public boolean hasPersonArchive(Person person) {
        requireNonNull(person);
        return versionedArchiveBook.hasPerson(person);
    }

    @Override
    public boolean hasPersonPin(Person person) {
        requireNonNull(person);
        return versionedPinBook.hasPerson(person);
    }

    @Override
    public boolean hasEditedPerson(Person referencePerson, Person editedPerson) {
        requireNonNull(referencePerson);
        requireNonNull(editedPerson);
        return versionedAddressBook.hasEditedPerson(referencePerson, editedPerson);
    }

    @Override
    public boolean hasEditedPersonArchive(Person referencePerson, Person editedPerson) {
        requireNonNull(referencePerson);
        requireNonNull(editedPerson);
        return versionedArchiveBook.hasEditedPerson(referencePerson, editedPerson);
    }

    @Override
    public boolean hasEditedPersonPin(Person referencePerson, Person editedPerson) {
        requireNonNull(referencePerson);
        requireNonNull(editedPerson);
        return versionedPinBook.hasEditedPerson(referencePerson, editedPerson);
    }

    @Override
    public boolean hasSameIdentityField(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasSameIdentityField(person);
    }

    @Override
    public boolean hasSameIdentityFieldArchive(Person person) {
        requireNonNull(person);
        return versionedArchiveBook.hasSameIdentityField(person);
    }

    @Override
    public boolean hasSameIdentityFieldPin(Person person) {
        requireNonNull(person);
        return versionedPinBook.hasSameIdentityField(person);
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

    //=========== ArchiveBook ================================================================================

    @Override
    public void setArchiveBook(ReadOnlyAddressBook archiveBook) {
        versionedArchiveBook.resetData(archiveBook);
    }

    @Override
    public ReadOnlyAddressBook getArchiveBook() {
        return versionedArchiveBook;
    }

    @Override
    public void archivePerson(Person target) {
        versionedArchiveBook.addPerson(target);
        versionedAddressBook.removePerson(target);
    }

    @Override
    public void unarchivePerson(Person target) {
        versionedAddressBook.addPerson(target);
        versionedArchiveBook.removePerson(target);
    }

    //=========== PinBook ====================================================================================

    @Override
    public void setPinBook(ReadOnlyAddressBook pinBook) {
        versionedPinBook.resetData(pinBook);
    }

    @Override
    public ReadOnlyAddressBook getPinBook() {
        return versionedPinBook;
    }

    @Override
    public void pinPerson(Person target) {
        versionedPinBook.addPerson(target);
        versionedAddressBook.removePerson(target);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void unpinPerson(Person target) {
        versionedPinBook.removePerson(target);
        versionedAddressBook.addPerson(target);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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

    //=========== Filtered Archived Person List Accessors ===================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedArchiveBook}
     */
    @Override
    public ObservableList<Person> getFilteredArchivedPersonList() {
        return filteredArchivedPersons;
    }

    @Override
    public void updateFilteredArchivedPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredArchivedPersons.setPredicate(predicate);
    }

    //=========== Filtered Pin List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedPinBook}
     */
    @Override
    public ObservableList<Person> getFilteredPinnedPersonList() {
        return filteredPinnedPersons;
    }

    @Override
    public void updateFilteredPinnedPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPinnedPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoBooks() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoBooks() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoBooks() {
        versionedAddressBook.undo();
        versionedArchiveBook.undo();
        versionedPinBook.undo();
    }

    @Override
    public void redoBooks() {
        versionedAddressBook.redo();
        versionedArchiveBook.redo();
        versionedPinBook.redo();
    }

    @Override
    public void commitBooks() {
        versionedAddressBook.commit();
        versionedArchiveBook.commit();
        versionedPinBook.commit();
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

    //=========== Selected Pin person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPinPersonProperty() {
        return selectedPinPerson;
    }

    @Override
    public Person getSelectedPinPerson() {
        return selectedPinPerson.getValue();
    }

    @Override
    public void setSelectedPinPerson(Person person) {
        if (person != null && !filteredPinnedPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPinPerson.setValue(person);
    }

    /**
     * Ensures {@code selectedPinPerson} is a valid person in {@code filteredPinnedPersons}.
     */
    private void ensureSelectedPinPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPinPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPinPersonReplaced = change.wasReplaced()
                && change.getAddedSize() == change.getRemovedSize()
                && change.getRemoved().contains(selectedPinPerson.getValue());
            if (wasSelectedPinPersonReplaced) {
                // Update selectedPinPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPinPerson.getValue());
                selectedPinPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPinPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPinPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPinPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPinPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    //=========== Selected archived person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedArchivedPersonProperty() {
        return selectedArchivedPerson;
    }

    @Override
    public Person getSelectedArchivedPerson() {
        return selectedArchivedPerson.getValue();
    }

    @Override
    public void setSelectedArchivedPerson(Person person) {
        if (person != null && !filteredArchivedPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedArchivedPerson.setValue(person);
    }

    /**
     * Ensures {@code selectedArchivedPerson} is a valid person in {@code filteredArchivedPersons}.
     */
    private void ensureSelectedArchivedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedArchivedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedArchivedPersonReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedArchivedPerson.getValue());
            if (wasSelectedArchivedPersonReplaced) {
                // Update selectedArchivedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedArchivedPerson.getValue());
                selectedArchivedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedArchivedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedArchivedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedArchivedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedArchivedPerson.setValue(change.getFrom() > 0
                        ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
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
                && versionedArchiveBook.equals(other.versionedArchiveBook)
                && versionedPinBook.equals(other.versionedPinBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredArchivedPersons.equals(other.filteredArchivedPersons)
                && filteredPinnedPersons.equals(other.filteredPinnedPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get())
                && Objects.equals(selectedPinPerson.get(), other.selectedPinPerson.get())
                && Objects.equals(selectedArchivedPerson.get(), other.selectedArchivedPerson.get());
    }

}
