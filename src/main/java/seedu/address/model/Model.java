package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.prescription.Prescription;


/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Patient> PREDICATE_SHOW_ALL_PATIENTS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Doctor> PREDICATE_SHOW_ALL_DOCTORS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<MedicalHistory> PREDICATE_SHOW_ALL_MEDHISTS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Prescription> PREDICATE_SHOW_ALL_PRESCRIPTIONS = unused -> true;

    /**
     * {@code Comparator} that sort medical history by date in ascending order from oldest to newest.
     */
    Comparator<MedicalHistory> COMPARATOR_MED_HIST_DATE_ASC = new MedHistDateAscComparator();

    /**
     * {@code Comparator} that sort medical history by date in decending order from newest to oldest.
     */
    Comparator<MedicalHistory> COMPARATOR_MED_HIST_DATE_DESC = new MedHistDateDescComparator();

    /**
     * {@code Comparator} that sort prescriptions by date in ascending order from oldest to newest.
     */
    Comparator<Prescription> COMPARATOR_PRESC_DATE_ASC = new PrescriptionDateAscComparator();

    /**
     * {@code Comparator} that sort prescriptions by date in descending order from newest to oldest.
     */
    Comparator<Prescription> COMPARATOR_PRESC_DATE_DESC = new PrescriptionDateDescComparator();

    /**
     * Comparater of Medical History
     * Medical history with older date is larger than medical history with newer date.
     */
    class MedHistDateAscComparator implements Comparator<MedicalHistory> {
        public int compare(MedicalHistory mh1, MedicalHistory mh2) {
            return mh1.getDate().date.compareTo(mh2.getDate().date);
        }
    }

    /**
     * Comparater of Medical History
     * Medical history with newer date is larger than medical history with older date.
     */
    class MedHistDateDescComparator implements Comparator<MedicalHistory> {
        public int compare(MedicalHistory mh1, MedicalHistory mh2) {
            return mh2.getDate().date.compareTo(mh1.getDate().date);
        }
    }

    /**
     * Comparator of Prescription
     * Prescriptions with older date is larger than prescription with newer date.
     */
    class PrescriptionDateAscComparator implements Comparator<Prescription> {
        public int compare(Prescription p1, Prescription p2) {
            return p1.getDate().date.compareTo(p2.getDate().date);
        }
    }

    /**
     * Comparator of Prescription
     * Prescription with newer date is larger than prescription with older date.
     */
    class PrescriptionDateDescComparator implements Comparator<Prescription> {
        public int compare(Prescription p1, Prescription p2) {
            return p2.getDate().date.compareTo(p1.getDate().date);
        }
    }

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
     * Returns the user prefs' DocX file path.
     */
    Path getDocXFilePath();

    /**
     * Sets the user prefs' DocX file path.
     */
    void setDocXFilePath(Path docXFilePath);

    /**
     * Replaces DocX data with the data in {@code DocX}.
     */
    void setDocX(ReadOnlyDocX docX);

    /**
     * Returns the DocX
     */
    ReadOnlyDocX getDocX();

    /**
     * Return object Patient with given id
     */
    Patient getPatientById(PersonId patientId);

    /**
     * Return object Doctor with given id
     */
    Doctor getDoctorById(PersonId doctorId);

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the DocX.
     */
    boolean hasPatient(Patient patient);

    /**
     * Returns true if a medical history with the same identity as {@code patient} exists in the DocX.
     */
    boolean hasMedHist(MedicalHistory medicalHistory);

    /**
     * Deletes the given patient.
     * The patient must exist in the DocX.
     */
    void deletePatient(Patient target);

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the DocX.
     */
    boolean hasAppointment(Appointment appointment);

    /**
     * Adds the appointment.
     * {@code appointment} must not be a duplicate
     */
    void addAppointment(Appointment appointment);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the DocX.
     */
    void addPatient(Patient patient);

    /**
     * Adds the given medical history.
     * {@code medical history} must not already exist in the DocX.
     */
    void addMedHist(MedicalHistory medicalHistory);

    /**
     * Adds the given prescription.
     * {@code prescription} must not already exist in the DocX.
     */
    void addPrescription(Prescription prescription);

    /**
     * Returns true if a prescription with the same identity as {@code prescription} exists in the DocX.
     */
    boolean hasPrescription(Prescription prescription);


    /**
     * Replaces the given patient {@code target} with {@code editedPatient}.
     * {@code target} must exist in the DocX.
     * The patient identity of {@code editedPatient} must not be the same
     * as another existing patient in the DocX.
     */
    void setPatient(Patient target, Patient editedPatient);

    /**
     * Replaces the given doctor {@code target} with {@code editedDoctor}.
     * {@code target} must exist in docX.
     * The doctor identity of {@code editedDoctor} must not be the same
     * as another existing doctor in docX.
     */
    void setDoctor(Doctor target, Doctor editedDoctor);

    /**
     * Replaces the given medHist {@code target} with {@code editedMedHist}.
     * {@code target} must exist in the DocX.
     * The medHist identity of {@code editedMedHist} must not be the same
     * as another existing medHist in the DocX.
     */
    void setMedHist(MedicalHistory target, MedicalHistory editedMedHist);

    /**
     * Replaces the given appointment {@code target} with {@code changedAppointment}.
     * {@code target} must exist in the DocX.
     * The new appointment must not be the same as another existing appointment in DocX.
     */
    void setAppointment(Appointment target, Appointment changedAppointment);

    /**
     * Replaces the given Prescription {@code target} with {@code editedPrescription}.
     * {@code target} must exist in the DocX.
     * The prescription identity of {@code editedPrescription} must not be the same
     * as another existing prescription in the DocX.
     */
    void setPrescription(Prescription target, Prescription editedPrescription);

    /**
     * Returns an unmodifiable view of the filtered patient list
     */
    ObservableList<Patient> getFilteredPatientList();

    /**
     * Returns an unmodifiable view of the filtered medical history list
     */
    ObservableList<MedicalHistory> getFilteredMedHistList();

    /**
     * Returns an unmodifiable view of the filtered doctor list
     */
    ObservableList<Doctor> getFilteredDoctorList();

    /**
     * Returns an unmodifiable view of the filtered appointment list
     */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Patient> predicate);

    /**
     * Updates the filter of the filtered medical history list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMedHistList(Predicate<MedicalHistory> predicate);

    /**
     * Updates the filter of the filtered doctor list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDoctorList(Predicate<Doctor> predicate);

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);


    /**
     * Sort filtered medical history list
     */
    void sortFilteredMedHistList(Comparator<MedicalHistory> medicalHistoryComparator);

    /**
     * Returns an unmodifiable view of the filtered prescription list
     */
    ObservableList<Prescription> getFilteredPrescriptionList();

    /**
     * Updates the filter of the filtered prescription list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPrescriptionList(Predicate<Prescription> predicate);

    /**
     * Sort filtered prescription list
     */
    void sortFilteredPrescriptionList(Comparator<Prescription> prescriptionComparator);

    /**
     * Saves the current DocX state for undo/redo.
     */
    void commitDocX();

    /**
     * Selected patient in the filtered patient list.
     * null if no patient is selected.
     */
    ReadOnlyProperty<Patient> selectedPatientProperty();

    /**
     * Selected medical history in the filtered medHist list.
     * null if no medHist is selected.
     */
    ReadOnlyProperty<MedicalHistory> selectedMedHistProperty();

    /**
     * Selected appointment in the filtered appointment list.
     * null if no appointment is selected.
     */
    ReadOnlyProperty<Appointment> selectedAppointmentProperty();

    /**
     * Selected prescription in the filtered medHist list.
     * null if no medHist is selected.
     */
    ReadOnlyProperty<Prescription> selectedPrescriptionProperty();

    /**
     * Returns the selected patient in the filtered patient list.
     * null if no patient is selected.
     */
    Patient getSelectedPatient();

    /**
     * Returns the selected medHist in the filtered medHist list.
     * null if no medHist is selected.
     */
    MedicalHistory getSelectedMedHist();

    /**
     * Returns the selected appointment in the filtered appointment list.
     * null if no appointment is selected.
     */
    Appointment getSelectedAppointment();

    /**
     * Returns the selected prescription in the filtered prescription list.
     * null if no prescription is selected.
     */
    Prescription getSelectedPrescription();

    /**
     * Sets the selected patient in the filtered patient list.
     */
    void setSelectedPatient(Patient patient);

    /**
     * Sets the selected medHist in the filtered medHist list.
     */
    void setSelectedMedHist(MedicalHistory medHist);

    /**
     * Sets the selected appointment in the filtered appointment list.
     */
    void setSelectedAppointment(Appointment appointment);

    /**
     * Sets the selected prescription in the filtered prescription list.
     */
    void setSelectedPrescription(Prescription prescription);

    /**
     * Returns true if a doctor with the same identity as {@code doctor} exists in docX.
     */
    boolean hasDoctor(Doctor toAdd);

    /**
     * Adds the given doctor.
     * {@code doctor} must not already exist in docX.
     */
    void addDoctor(Doctor toAdd);

    /**
     * Selected doctor in the filtered doctor list.
     * null if no doctor is selected.
     */
    ReadOnlyProperty<Doctor> selectedDoctorProperty();

    /**
     * Returns the selected doctor in the filtered doctor list.
     * null if no doctor is selected.
     */
    Doctor getSelectedDoctor();

    /**
     * Sets the selected doctor in the filtered doctor list.
     */
    void setSelectedDoctor(Doctor doctor);

    /**
     * Deletes the given doctor.
     * The doctor must exist in the DocX.
     */
    void deleteDoctor(Doctor target);

}
