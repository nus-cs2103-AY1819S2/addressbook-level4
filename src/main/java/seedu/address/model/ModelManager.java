package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
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
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.exceptions.MedHistNotFoundException;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.exceptions.DoctorNotFoundException;
import seedu.address.model.person.exceptions.PatientNotFoundException;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.prescription.Prescription;
import seedu.address.model.prescription.exceptions.PrescriptionNotFoundException;


/**
 * Represents the in-memory model of the DocX data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedDocX versionedDocX;
    private final UserPrefs userPrefs;
    private final FilteredList<Patient> filteredPatients;
    private final SimpleObjectProperty<Patient> selectedPatient = new SimpleObjectProperty<>();
    private final FilteredList<Doctor> filteredDoctors;
    private final SimpleObjectProperty<Doctor> selectedDoctor = new SimpleObjectProperty<>();
    private final FilteredList<MedicalHistory> filteredMedHists;
    private final SimpleObjectProperty<MedicalHistory> selectedMedHist = new SimpleObjectProperty<>();
    private final FilteredList<Appointment> filteredAppointments;
    private final SimpleObjectProperty<Appointment> selectedAppointment = new SimpleObjectProperty<>();
    private final FilteredList<Prescription> filteredPrescriptions;
    private final SimpleObjectProperty<Prescription> selectedPrescription = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given DocX and userPrefs.
     */
    public ModelManager(ReadOnlyDocX docX, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(docX, userPrefs);

        logger.fine("Initializing with DocX: " + docX + " and user prefs " + userPrefs);

        versionedDocX = new VersionedDocX(docX);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatients = new FilteredList<>(versionedDocX.getPatientList());
        filteredPatients.addListener(this::ensureSelectedPatientIsValid);
        filteredDoctors = new FilteredList<>(versionedDocX.getDoctorList());
        filteredDoctors.addListener(this::ensureSelectedDoctorIsValid);
        filteredMedHists = new FilteredList<>(versionedDocX.getMedHistList());
        filteredMedHists.addListener(this::ensureSelectedMedHistIsValid);
        filteredAppointments = new FilteredList<>(versionedDocX.getAppointmentList());
        filteredAppointments.addListener(this::ensureSelectedAppointmentIsValid);
        filteredPrescriptions = new FilteredList<>(versionedDocX.getPrescriptionList());
        filteredPrescriptions.addListener(this::ensureSelectedPrescriptionIsValid);
    }

    public ModelManager() {
        this(new DocX(), new UserPrefs());
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
    public Path getDocXFilePath() {
        return userPrefs.getDocXFilePath();
    }

    @Override
    public void setDocXFilePath(Path docXFilePath) {
        requireNonNull(docXFilePath);
        userPrefs.setDocXFilePath(docXFilePath);
    }

    //=========== DocX ================================================================================

    @Override
    public void setDocX(ReadOnlyDocX docX) {
        versionedDocX.resetData(docX);
    }

    @Override
    public ReadOnlyDocX getDocX() {
        return versionedDocX;
    }

    @Override
    public Patient getPatientById(PersonId patientId) {
        requireNonNull(patientId);
        return versionedDocX.getPatientById(patientId);
    }

    @Override
    public Doctor getDoctorById(PersonId doctorId) {
        requireNonNull(doctorId);
        return versionedDocX.getDoctorById(doctorId);
    }


    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return versionedDocX.hasPatient(patient);
    }

    @Override
    public void deletePatient(Patient target) {
        versionedDocX.removePatient(target);
        updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
        setSelectedMedHist(null);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        updateFilteredPrescriptionList(PREDICATE_SHOW_ALL_PRESCRIPTIONS);
    }

    @Override
    public void addPatient(Patient patient) {
        versionedDocX.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public boolean hasDoctor(Doctor doctor) {
        requireNonNull(doctor);
        return versionedDocX.hasDoctor(doctor);
    }

    @Override
    public void addDoctor(Doctor doctor) {
        versionedDocX.addDoctor(doctor);
        updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);
    }

    @Override
    public void deleteDoctor(Doctor target) {
        versionedDocX.removeDoctor(target);
        updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
        setSelectedMedHist(null);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        updateFilteredPrescriptionList(PREDICATE_SHOW_ALL_PRESCRIPTIONS);
    }

    @Override
    public boolean hasPrescription(Prescription prescription) {
        requireNonNull(prescription);
        return versionedDocX.hasPrescription(prescription);
    }

    @Override
    public void addPrescription(Prescription prescription) {
        versionedDocX.addPrescription(prescription);
        updateFilteredPrescriptionList(PREDICATE_SHOW_ALL_PRESCRIPTIONS);
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return versionedDocX.hasAppointment(appointment);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        versionedDocX.addAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public boolean hasMedHist(MedicalHistory medicalHistory) {
        requireAllNonNull(medicalHistory);
        return versionedDocX.hasMedHist(medicalHistory);
    }

    @Override
    public void addMedHist(MedicalHistory medicalHistory) {
        versionedDocX.addMedHist(medicalHistory);
        updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
    }

    @Override
    public void setMedHist(MedicalHistory target, MedicalHistory editedMedHist) {
        requireAllNonNull(target, editedMedHist);

        versionedDocX.setMedHist(target, editedMedHist);
    }

    @Override
    public void setAppointment(Appointment target, Appointment changedAppointment) {
        requireAllNonNull(target, changedAppointment);

        versionedDocX.setAppointment(target, changedAppointment);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);

        versionedDocX.setPatient(target, editedPatient);
        updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
        setSelectedMedHist(null);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void setDoctor(Doctor target, Doctor editedDoctor) {
        requireAllNonNull(target, editedDoctor);

        versionedDocX.setDoctor(target, editedDoctor);
        updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
        setSelectedMedHist(null);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void setPrescription(Prescription target, Prescription editedPrescription) {
        requireAllNonNull(target, editedPrescription);

        versionedDocX.setPrescription(target, editedPrescription);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedDocX}
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
     * {@code versionedDocX}
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

    //=========== Filtered Medical History List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code MedicalHistory} backed by the internal list of
     * {@code versionedDocX}
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

    @Override
    public void sortFilteredMedHistList(Comparator<MedicalHistory> medHistComparator) {
        requireNonNull(medHistComparator);
        versionedDocX.sortMedHist(medHistComparator);
    }

    //=========== Filtered Appointment List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code versionedDocX}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return filteredAppointments;
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    //=========== Filtered Prescription List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Prescription} backed by the internal list of
     * {@code versionedDocX}
     */
    @Override
    public ObservableList<Prescription> getFilteredPrescriptionList() {
        return filteredPrescriptions;
    }

    @Override
    public void updateFilteredPrescriptionList(Predicate<Prescription> predicate) {
        requireNonNull(predicate);
        filteredPrescriptions.setPredicate(predicate);
    }

    @Override
    public void sortFilteredPrescriptionList(Comparator<Prescription> prescriptionComparator) {
        requireNonNull(prescriptionComparator);
        versionedDocX.sortPrescription(prescriptionComparator);
    }

    @Override
    public void commitDocX() {
        versionedDocX.commit();
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

    //=========== Selected doctor ===========================================================================

    @Override
    public ReadOnlyProperty<Doctor> selectedDoctorProperty() {
        return selectedDoctor;
    }

    @Override
    public Doctor getSelectedDoctor() {
        return selectedDoctor.getValue();
    }

    @Override
    public void setSelectedDoctor(Doctor doctor) {
        if (doctor != null && !filteredDoctors.contains(doctor)) {
            throw new DoctorNotFoundException();
        }
        selectedDoctor.setValue(doctor);
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

    //=========== Selected Medical History ===================================================================
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

    //=========== Selected Prescription ===================================================================
    @Override
    public ReadOnlyProperty<Prescription> selectedPrescriptionProperty() {
        return selectedPrescription;
    }

    @Override
    public Prescription getSelectedPrescription() {
        return selectedPrescription.getValue();
    }

    @Override
    public void setSelectedPrescription(Prescription prescription) {
        if (prescription != null && !filteredPrescriptions.contains(prescription)) {
            throw new PrescriptionNotFoundException();
        }
        selectedPrescription.setValue(prescription);
    }

    /**
     * Ensures {@code selectedMedHist} is a valid medical history in {@code filteredMedHists}.
     */
    private void ensureSelectedPrescriptionIsValid(ListChangeListener.Change<? extends Prescription> change) {
        while (change.next()) {
            if (selectedPrescription.getValue() == null) {
                // null is always a valid selected prescription, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPrescriptionReplaced =
                    change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                            && change.getRemoved().contains(selectedPrescription.getValue());
            if (wasSelectedPrescriptionReplaced) {
                // Update selectedMedHist to its new value.
                int index = change.getRemoved().indexOf(selectedPrescription.getValue());
                selectedPrescription.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPrescriptionRemoved = change.getRemoved().stream()
                    .anyMatch(removedPrescription -> selectedPrescription.getValue()
                            .isSamePrescription(removedPrescription));
            if (wasSelectedPrescriptionRemoved) {
                // Select the prescription that came before it in the list,
                // or clear the selection if there is no such prescription.
                selectedPrescription.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
        return versionedDocX.equals(other.versionedDocX)
                && userPrefs.equals(other.userPrefs)
                && filteredPatients.equals(other.filteredPatients)
                && Objects.equals(selectedPatient.get(), other.selectedPatient.get());
    }

    //=========== Selected appointment ======================================================================

    @Override
    public ReadOnlyProperty<Appointment> selectedAppointmentProperty() {
        return selectedAppointment;
    }

    @Override
    public Appointment getSelectedAppointment() {
        return selectedAppointment.getValue();
    }

    @Override
    public void setSelectedAppointment(Appointment appointment) {
        if (appointment != null && !filteredAppointments.contains(appointment)) {
            throw new AppointmentNotFoundException();
        }
        selectedAppointment.setValue(appointment);
    }

    /**
     * Ensures {@code selectedAppointment} is a valid appointment in {@code filteredAppointments}.
     */
    private void ensureSelectedAppointmentIsValid(ListChangeListener.Change<? extends Appointment> change) {
        while (change.next()) {
            if (selectedAppointment.getValue() == null) {
                // null is always a valid selected appointment, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedAppointmentReplaced =
                    change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                            && change.getRemoved().contains(selectedAppointment.getValue());
            if (wasSelectedAppointmentReplaced) {
                // Update selectedAppointment to its new value.
                int index = change.getRemoved().indexOf(selectedAppointment.getValue());
                selectedAppointment.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedAppointmentRemoved = change.getRemoved().stream()
                    .anyMatch(removedAppointment -> selectedAppointment.getValue()
                            .isSameAppointment(removedAppointment));
            if (wasSelectedAppointmentRemoved) {
                // Select the appointment that came before it in the list,
                // or clear the selection if there is no such appointment.
                selectedAppointment.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }
}
