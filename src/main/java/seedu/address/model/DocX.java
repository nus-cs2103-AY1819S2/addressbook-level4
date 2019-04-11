package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.UniqueMedHistList;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.PersonIdCounter;
import seedu.address.model.person.UniqueDoctorList;
import seedu.address.model.person.UniquePatientList;
import seedu.address.model.prescription.Prescription;
import seedu.address.model.prescription.UniquePrescriptionList;



/**
 * Wraps all data at the docX level
 * Duplicates are not allowed (by .isSamePatient comparison)
 */
public class DocX implements ReadOnlyDocX {

    private final UniquePatientList patients;
    private final UniqueDoctorList doctors;
    private final UniqueMedHistList medHists;
    private final UniquePrescriptionList prescriptions;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();
    private final UniqueAppointmentList appointments;
    private final PersonIdCounter personIdCounter;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     * among constructors.
     */
    {
        patients = new UniquePatientList();
        doctors = new UniqueDoctorList();
        medHists = new UniqueMedHistList();
        prescriptions = new UniquePrescriptionList();
        appointments = new UniqueAppointmentList();
        personIdCounter = PersonIdCounter.getInstance();
    }

    public DocX() {
    }

    /**
     * Creates an DocX using the Patients in the {@code toBeCopied}
     */
    public DocX(ReadOnlyDocX toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the content of the idCounter with {@code idCounter}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setPersonIdCounter(PersonIdCounter idCounter) {
        this.personIdCounter.setCurrentMaxId(idCounter.getCurrentMaxId());
        indicateModified();
    }

    /**
     * Replaces the contents of the patient list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setPatients(List<Patient> patients) {
        this.patients.setPatients(patients);
        indicateModified();
    }

    /**
     * Replaces the contents of the medical history list with {@code medHists}.
     * {@code medHists} must not contain duplicate medical histories.
     */
    public void setMedHists(List<MedicalHistory> medHists) {
        this.medHists.setMedHists(medHists);
        indicateModified();
    }

    /**
     * Replaces the contents of the doctor list with {@code doctors}.
     * {@code doctors} must not contain duplicate doctors.
     */
    public void setDoctors(List<Doctor> doctors) {
        this.doctors.setDoctors(doctors);
        indicateModified();
    }

    /**
     * Replaces the contents of the appointment list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
        indicateModified();
    }

    /**
     * Replaces the contents of the medical history list with {@code medHists}.
     * {@code medHists} must not contain duplicate medical histories.
     */
    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions.setPrescriptions(prescriptions);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code DocX} with {@code newData}.
     */
    public void resetData(ReadOnlyDocX newData) {
        requireNonNull(newData);
        setPatients(newData.getPatientList());
        setMedHists(newData.getMedHistList());
        setDoctors(newData.getDoctorList());
        setAppointments(newData.getAppointmentList());
        setPrescriptions(newData.getPrescriptionList());
    }

    //// patient-level operations

    /**
     * Return object Patient with given id
     */
    public Patient getPatientById(PersonId patientId) {
        requireNonNull(patientId);
        return patients.findPatientById(patientId);
    }

    /**
     * Return object Doctor with given id
     */
    Doctor getDoctorById(PersonId doctorId) {
        requireNonNull(doctorId);
        return doctors.findDoctorById(doctorId);
    }

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the docX.
     */
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patients.contains(patient);
    }

    /**
     * Adds a patient to the docX.
     * The patient must not already exist in the docX.
     */
    public void addPatient(Patient p) {
        patients.add(p);
        indicateModified();
    }

    /**
     * Returns true if a doctor with the same identity as {@code doctor} exists in the docX.
     */
    public boolean hasDoctor(Doctor doctor) {
        requireNonNull(doctor);
        return doctors.contains(doctor);
    }

    /**
     * Adds a doctor to the docX.
     * The doctor must not already exist in the docX.
     */
    public void addDoctor(Doctor d) {
        doctors.add(d);
        indicateModified();
    }

    /**
     * Replaces the given patient {@code target} in the list with {@code editedPatient}.
     * {@code target} must exist in the docX.
     * The patient identity of {@code editedPatient} must not be the same
     * as another existing patient in the docX.
     */
    public void setPatient(Patient target, Patient editedPatient) {
        requireNonNull(editedPatient);

        patients.setPatient(target, editedPatient);
        indicateModified();
    }

    /**
     * Returns true if a duplicate {@code appointment} exists in docX.
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(appointment);
    }

    /**
     * Adds an appointment.
     * The appointment must not already exist.
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setPatient(getPatientById(appointment.getPatientId()));
        appointment.setDoctor(getDoctorById(appointment.getDoctorId()));
        indicateModified();
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code changedAppointment}.
     * {@code target} must exist in docX.
     * The new appointment must not be a duplicate of an existing appointment in docX.
     */
    public void setAppointment(Appointment target, Appointment changedAppointment) {
        requireNonNull(changedAppointment);

        changedAppointment.setPatient(getPatientById(changedAppointment.getPatientId()));
        changedAppointment.setDoctor(getDoctorById(changedAppointment.getDoctorId()));
        appointments.setAppointment(target, changedAppointment);
        indicateModified();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    /**
     * Replaces the given doctor {@code target} in the list with {@code editedDoctor}.
     * {@code target} must exist in the docX.
     * The doctor identity of {@code editedDoctor} must not be the same as another existing doctor in the docX.
     */
    public void setDoctor(Doctor target, Doctor editedDoctor) {
        requireNonNull(editedDoctor);

        doctors.setDoctor(target, editedDoctor);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code DocX}.
     * {@code key} must exist in the docX.
     */
    public void removePatient(Patient key) {
        patients.remove(key);
        updateMedHistWhenPatientDeleted(key.getId());
        updatePrescriptionWhenPatientDeleted(key.getId());
        indicateModified();
    }

    //// medical history-level operations
    /**
     * Set Patient in medical history to null if the patient is deleted
     */
    public void updateMedHistWhenPatientDeleted(PersonId patientId) {
        requireNonNull(patientId);
        medHists.setPatientToNull(patientId);
    }

    /**
     * Set Doctor in medical history to null if the patient is deleted
     */
    public void updateMedHistWhenDoctorDeleted(PersonId doctorId) {
        requireNonNull(doctorId);
        medHists.setDoctorToNull(doctorId);
    }

    /**
     * Returns true if a medical history with the same identity as {@code person} exists in the docX.
     */
    public boolean hasMedHist(MedicalHistory medHist) {
        requireNonNull(medHist);
        return medHists.contains(medHist);
    }

    /**
     * Adds a medical history to the docX.
     * The medical history must not already exist in the docX.
     */
    public void addMedHist(MedicalHistory medHist) {
        medHists.add(medHist);
        Patient patientWithId = getPatientById(medHist.getPatientId());
        medHist.setPatient(patientWithId);
        Doctor doctorWithId = getDoctorById(medHist.getDoctorId());
        medHist.setDoctor(doctorWithId);
        indicateModified();
    }

    /**
     * Sort medical histories by date.
     */
    public void sortMedHist(Comparator<MedicalHistory> medHistComparator) {
        medHists.sort(medHistComparator);
    }

    /**
     * Replaces the given medical history {@code target} in the list with {@code editedMedHist}.
     * {@code target} must exist in the docX.
     * The medical history identity of {@code editedMedHist} must not be the same as another existing medical history
     * in the docX.
     */
    public void setMedHist(MedicalHistory target, MedicalHistory editedMedHist) {
        requireNonNull(editedMedHist);

        medHists.setMedHist(target, editedMedHist);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code DocX}.
     * {@code key} must exist in the docX.
     */
    public void removeMedHist(MedicalHistory key) {
        medHists.remove(key);
        indicateModified();
    }
    //// prescription-level operations
    /**
     * Set Doctor in prescription to null if the patient is deleted
     */
    public void updatePrescriptionWhenDoctorDeleted(PersonId doctorId) {
        requireNonNull(doctorId);
        prescriptions.setDoctorToNull(doctorId);
    }

    /**
     * Set Patient in prescription to null if the patient is deleted
     */
    public void updatePrescriptionWhenPatientDeleted(PersonId patientId) {
        requireNonNull(patientId);
        prescriptions.setPatientToNull(patientId);
    }

    /**
     * Returns true if a prescription with the same identity as {@code prescription} exists in the docX.
     */
    public boolean hasPrescription(Prescription prescription) {
        requireNonNull(prescription);
        return prescriptions.contains(prescription);
    }

    /**
     * Adds a prescription to the docX.
     * The prescription must not already exist in the docX.
     */
    public void addPrescription(Prescription prescription) {
        prescriptions.addPrescription(prescription);
        indicateModified();
    }

    /**
     * Replaces the given prescription {@code target} in the list with {@code editedPrescription}.
     * {@code target} must exist in the docX.
     * The prescription identity of {@code editedPrescription} must not be the same as another existing prescription
     * in the docX.
     */
    public void setPrescription(Prescription target, Prescription editedPrescription) {
        requireNonNull(editedPrescription);

        prescriptions.setPrescription(target, editedPrescription);
        indicateModified();
    }


    /**
     * Removes {@code key} from this {@code DocX}.
     * {@code key} must exist in the docX.
     */
    public void removeDoctor(Doctor key) {
        doctors.remove(key);
        updateMedHistWhenDoctorDeleted(key.getId());
        updatePrescriptionWhenDoctorDeleted(key.getId());
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the docX has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return patients.asUnmodifiableObservableList().size() + " patients";
        // TODO: refine later
    }

    @Override
    public ObservableList<Patient> getPatientList() {
        return patients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<MedicalHistory> getMedHistList() {
        return medHists.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Doctor> getDoctorList() {
        return doctors.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Prescription> getPrescriptionList() {
        return prescriptions.asUnmodifiableObservableList();
    }

    @Override
    public PersonIdCounter getPersonIdCounter() {
        return personIdCounter;
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DocX // instanceof handles nulls
                && patients.equals(((DocX) other).patients)
                && doctors.equals(((DocX) other).doctors)
                && medHists.equals(((DocX) other).medHists));
    }

    @Override
    public int hashCode() {
        return patients.hashCode();
    }
}
