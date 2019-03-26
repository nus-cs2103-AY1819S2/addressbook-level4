package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook extends Observable {

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
}
