package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.PersonIdCounter;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.prescription.Prescription;


/**
 * Unmodifiable view of an docX
 */
public interface ReadOnlyDocX extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Patient> getPatientList();

    /**
     * Returns an unmodifiable view of the medical histories list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<MedicalHistory> getMedHistList();

    /**
     * Returns an unmodifiable view of the doctors list.
     * This list will not contain any duplicate doctors.
     */
    ObservableList<Doctor> getDoctorList();

    /**
     * Returns an unmodifiable view of the prescription list.
     * This list will not contain any duplicate prescriptions.
     */
    ObservableList<Prescription> getPrescriptionList();


    /**
     * Returns an unmodifiable view of the doctors list.
     * This list will not contain any duplicate doctors.
     */
    ObservableList<Appointment> getAppointmentList();

    /**
     * Returns PersonIdCounter for patient and doctor
     */
    PersonIdCounter getPersonIdCounter();
}
