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
import seedu.address.model.record.Record;
import seedu.address.model.record.exceptions.RecordNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Record> filteredPeople;
    private final SimpleObjectProperty<Record> selectedRecord = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPeople = new FilteredList<>(versionedAddressBook.getRecordList());
        filteredPeople.addListener(this::ensureSelectedRecordIsValid);
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

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return versionedAddressBook.hasRecord(record);
    }

    @Override
    public void deleteRecord(Record target) {
        versionedAddressBook.removeRecord(target);
    }

    @Override
    public void addRecord(Record record) {
        versionedAddressBook.addRecord(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
    }

    @Override
    public void setRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        versionedAddressBook.setRecord(target, editedRecord);
    }

    //=========== Filtered Record List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Record} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Record> getFilteredRecordList() {
        return filteredPeople;
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        requireNonNull(predicate);
        filteredPeople.setPredicate(predicate);
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

    //=========== Selected record ===========================================================================

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
        if (record != null && !filteredPeople.contains(record)) {
            throw new RecordNotFoundException();
        }
        selectedRecord.setValue(record);
    }

    /**
     * Ensures {@code selectedRecord} is a valid record in {@code filteredPeople}.
     * @param change
     */
    private void ensureSelectedRecordIsValid(ListChangeListener.Change<? extends Record> change) {
        while (change.next()) {
            if (selectedRecord.getValue() == null) {
                // null is always a valid selected record, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedRecordReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedRecord.getValue());
            if (wasSelectedRecordReplaced) {
                // Update selectedRecord to its new value.
                int index = change.getRemoved().indexOf(selectedRecord.getValue());
                selectedRecord.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedRecordRemoved = change.getRemoved().stream()
                    .anyMatch(removedRecord -> selectedRecord.getValue().isSameRecord(removedRecord));
            if (wasSelectedRecordRemoved) {
                // Select the record that came before it in the list,
                // or clear the selection if there is no such record.
                selectedRecord.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
                && userPrefs.equals(other.userPrefs)
                && filteredPeople.equals(other.filteredPeople)
                && Objects.equals(selectedRecord.get(), other.selectedRecord.get());
    }

}
