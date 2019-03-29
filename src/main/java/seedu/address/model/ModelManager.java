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
import seedu.address.model.medicalhistory.exceptions.MedHistNotFoundException;
import seedu.address.model.person.Doctor;
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
    private final FilteredList<Doctor> filteredDoctors;
    private final SimpleObjectProperty<Doctor> selectedDoctor = new SimpleObjectProperty<>();
    private final FilteredList<MedicalHistory> filteredMedHists;
    private final SimpleObjectProperty<MedicalHistory> selectedMedHist = new SimpleObjectProperty<>();

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
        filteredDoctors = new FilteredList<>(versionedAddressBook.getDoctorList());
        filteredDoctors.addListener(this::ensureSelectedDoctorIsValid);
        filteredMedHists = new FilteredList<>(versionedAddressBook.getMedHistList());
        filteredMedHists.addListener(this::ensureSelectedMedHistIsValid);
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

    @Override
    public boolean hasDoctor(Doctor doctor) {
        requireNonNull(doctor);
        return versionedAddressBook.hasDoctor(doctor);
    }

    @Override
    public void addDoctor(Doctor doctor) {
        versionedAddressBook.addDoctor(doctor);
        updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);
    }

    @Override
    public boolean hasMedHist(MedicalHistory medicalHistory) {
        requireAllNonNull(medicalHistory);
        return versionedAddressBook.hasMedHist(medicalHistory);
    }

    @Override
    public void addMedHist(MedicalHistory medicalHistory) {
        versionedAddressBook.addMedHist(medicalHistory);
        updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
    }

    @Override
    public void deleteMedHist(MedicalHistory target) {
        versionedAddressBook.removeMedHist(target);
    }

    @Override
    public void setMedHist(MedicalHistory target, MedicalHistory editedMedHist) {
        requireAllNonNull(target, editedMedHist);

        versionedAddressBook.setMedHist(target, editedMedHist);
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

    //=========== Filtered Doctor List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Doctor} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Doctor> getFilteredDoctorList() {
        return filteredDoctors;
    }

    @Override
    public void updateFilteredDoctorList(Predicate<Doctor> predicate) {
        requireNonNull(predicate);
        filteredDoctors.setPredicate(predicate);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code MedicalHistory} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<MedicalHistory> getFilteredMedHistList() {
        return filteredMedHists;
    }

    @Override
    public void updateFilteredMedHistList(Predicate<MedicalHistory> predicate) {
        requireNonNull(predicate);
        filteredMedHists.setPredicate(predicate);
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

            boolean wasSelectedPatientReplaced =
                    change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
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

    /**
     * Ensures {@code selectedDoctor} is a valid doctor in {@code filteredDoctors}.
     */
    private void ensureSelectedDoctorIsValid(ListChangeListener.Change<? extends Doctor> change) {
        while (change.next()) {
            if (selectedDoctor.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedDoctorReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedDoctor.getValue());
            if (wasSelectedDoctorReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedDoctor.getValue());
                selectedDoctor.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedDoctorRemoved = change.getRemoved().stream()
                    .anyMatch(removedDoctor -> selectedDoctor.getValue().isSameDoctor(removedDoctor));
            if (wasSelectedDoctorRemoved) {
                // Select the doctor that came before it in the list,
                // or clear the selection if there is no such doctor.
                selectedDoctor.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    // Selected Medical History
    @Override
    public ReadOnlyProperty<MedicalHistory> selectedMedHistProperty() {
        return selectedMedHist;
    }

    @Override
    public MedicalHistory getSelectedMedHist() {
        return selectedMedHist.getValue();
    }

    @Override
    public void setSelectedMedHist(MedicalHistory medicalHistory) {
        if (medicalHistory != null && !filteredMedHists.contains(medicalHistory)) {
            throw new MedHistNotFoundException();
        }
        selectedMedHist.setValue(medicalHistory);
    }

    /**
     * Ensures {@code selectedMedHist} is a valid medical history in {@code filteredMedHists}.
     */
    private void ensureSelectedMedHistIsValid(ListChangeListener.Change<? extends MedicalHistory> change) {
        while (change.next()) {
            if (selectedMedHist.getValue() == null) {
                // null is always a valid selected patient, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedMedHistReplaced =
                    change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                            && change.getRemoved().contains(selectedMedHist.getValue());
            if (wasSelectedMedHistReplaced) {
                // Update selectedMedHist to its new value.
                int index = change.getRemoved().indexOf(selectedMedHist.getValue());
                selectedMedHist.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedMedHistRemoved = change.getRemoved().stream()
                    .anyMatch(removedMedHist -> selectedMedHist.getValue().isSameMedHist(removedMedHist));
            if (wasSelectedMedHistRemoved) {
                // Select the medical history that came before it in the list,
                // or clear the selection if there is no such medical history.
                selectedMedHist.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
