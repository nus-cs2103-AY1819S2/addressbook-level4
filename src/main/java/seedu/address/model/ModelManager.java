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
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Patient;
import seedu.address.model.person.exceptions.PatientNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Patient> filteredPatients;
    private final SimpleObjectProperty<Patient> selectedPatient = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatients = new FilteredList<>(versionedAddressBook.getPatientList());
        filteredPatients.addListener(this::ensureSelectedPatientIsValid);
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
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return versionedAddressBook.hasPatient(patient);
    }

    @Override
    public void deletePatient(Patient target) {
        versionedAddressBook.removePatient(target);
    }

    @Override
    public void addPatient(Patient patient) {
        versionedAddressBook.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    // Needed to be implemented later
    @Override
    public boolean hasMedHist(MedicalHistory medicalHistory) {
        return false;
    }

    // Needed to be implemented later
    @Override
    public void addMedHist(MedicalHistory medicalHistory) {
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);

        versionedAddressBook.setPatient(target, editedPatient);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return filteredPatients;
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
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

    //=========== Selected patient ===========================================================================

    @Override
    public ReadOnlyProperty<Patient> selectedPatientProperty() {
        return selectedPatient;
    }

    @Override
    public Patient getSelectedPatient() {
        return selectedPatient.getValue();
    }

    @Override
    public void setSelectedPatient(Patient patient) {
        if (patient != null && !filteredPatients.contains(patient)) {
            throw new PatientNotFoundException();
        }
        selectedPatient.setValue(patient);
    }

    /**
     * Ensures {@code selectedPatient} is a valid patient in {@code filteredPatients}.
     */
    private void ensureSelectedPatientIsValid(ListChangeListener.Change<? extends Patient> change) {
        while (change.next()) {
            if (selectedPatient.getValue() == null) {
                // null is always a valid selected patient, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPatientReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPatient.getValue());
            if (wasSelectedPatientReplaced) {
                // Update selectedPatient to its new value.
                int index = change.getRemoved().indexOf(selectedPatient.getValue());
                selectedPatient.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPatientRemoved = change.getRemoved().stream()
                    .anyMatch(removedPatient -> selectedPatient.getValue().isSamePatient(removedPatient));
            if (wasSelectedPatientRemoved) {
                // Select the patient that came before it in the list,
                // or clear the selection if there is no such patient.
                selectedPatient.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
                && filteredPatients.equals(other.filteredPatients)
                && Objects.equals(selectedPatient.get(), other.selectedPatient.get());
    }

}
