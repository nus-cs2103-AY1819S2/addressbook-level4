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
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Medicine> filteredMedicines;
    private final SimpleObjectProperty<Medicine> selectedMedicine = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMedicines = new FilteredList<>(versionedAddressBook.getMedicineList());
        filteredMedicines.addListener(this::ensureSelectedMedicineIsValid);
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
    public boolean hasMedicine(Medicine medicine) {
        requireNonNull(medicine);
        return versionedAddressBook.hasMedicine(medicine);
    }

    @Override
    public void deleteMedicine(Medicine target) {
        versionedAddressBook.removeMedicine(target);
    }

    @Override
    public void addMedicine(Medicine medicine) {
        versionedAddressBook.addMedicine(medicine);
        updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
    }

    @Override
    public void setMedicine(Medicine target, Medicine editedMedicine) {
        requireAllNonNull(target, editedMedicine);

        versionedAddressBook.setMedicine(target, editedMedicine);
    }

    //=========== Filtered Medicine List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Medicine} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Medicine> getFilteredMedicineList() {
        return filteredMedicines;
    }

    @Override
    public void updateFilteredMedicineList(Predicate<Medicine> predicate) {
        requireNonNull(predicate);
        filteredMedicines.setPredicate(predicate);
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

    //=========== Selected medicine ===========================================================================

    @Override
    public ReadOnlyProperty<Medicine> selectedMedicineProperty() {
        return selectedMedicine;
    }

    @Override
    public Medicine getSelectedMedicine() {
        return selectedMedicine.getValue();
    }

    @Override
    public void setSelectedMedicine(Medicine medicine) {
        if (medicine != null && !filteredMedicines.contains(medicine)) {
            throw new MedicineNotFoundException();
        }
        selectedMedicine.setValue(medicine);
    }

    /**
     * Ensures {@code selectedMedicine} is a valid medicine in {@code filteredMedicines}.
     */
    private void ensureSelectedMedicineIsValid(ListChangeListener.Change<? extends Medicine> change) {
        while (change.next()) {
            if (selectedMedicine.getValue() == null) {
                // null is always a valid selected medicine, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedMedicineReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedMedicine.getValue());
            if (wasSelectedMedicineReplaced) {
                // Update selectedMedicine to its new value.
                int index = change.getRemoved().indexOf(selectedMedicine.getValue());
                selectedMedicine.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedMedicineRemoved = change.getRemoved().stream()
                    .anyMatch(removedMedicine -> selectedMedicine.getValue().isSameMedicine(removedMedicine));
            if (wasSelectedMedicineRemoved) {
                // Select the medicine that came before it in the list,
                // or clear the selection if there is no such medicine.
                selectedMedicine.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
                && filteredMedicines.equals(other.filteredMedicines)
                && Objects.equals(selectedMedicine.get(), other.selectedMedicine.get());
    }

}
