package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;
import seedu.address.model.prescription.Prescription;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Patient> PREDICATE_SHOW_ALL_PATIENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Doctor> PREDICATE_SHOW_ALL_DOCTORS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<MedicalHistory> PREDICATE_SHOW_ALL_MEDHISTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the address book.
     */
    boolean hasPatient(Patient patient);

    /**
     * Returns true if a medical history with the same identity as {@code patient} exists in the address book.
     */
    boolean hasMedHist(MedicalHistory medicalHistory);

    /**
     * Deletes the given patient.
     * The patient must exist in the address book.
     */
    void deletePatient(Patient target);

    /**
     * Deletes the given medHist.
     * The medHist must exist in the address book.
     */
    void deleteMedHist(MedicalHistory target);

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the address book.
     */
    boolean hasAppointment(Appointment appointment);

    /**
     * Adds the appointment.
     * {@code appointment} must not be a duplicate
     */
    void addAppointment(Appointment appointment);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the address book.
     */
    void addPatient(Patient patient);

    /**
     * Adds the given medical history.
     * {@code medical history} must not already exist in the address book.
     */
    void addMedHist(MedicalHistory medicalHistory);

    /**
     * Adds the given prescription.
     * {@code prescription} must not already exist in the address book.
     */
    void addPrescription(Prescription prescription);

    /**
     * Returns true if a prescription with the same identity as {@code prescription} exists in the address book.
     */
    boolean hasPrescription(Prescription prescription);


    /**
     * Replaces the given patient {@code target} with {@code editedPatient}.
     * {@code target} must exist in the address book.
     * The patient identity of {@code editedPatient} must not be the same
     * as another existing patient in the address book.
     */


    void setPatient(Patient target, Patient editedPatient);

    /**
     * Replaces the given medHist {@code target} with {@code editedMedHist}.
     * {@code target} must exist in the address book.
     * The medHist identity of {@code editedMedHist} must not be the same
     * as another existing medHist in the address book.
     */
    void setMedHist(MedicalHistory target, MedicalHistory editedMedHist);

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered medical history list */
    ObservableList<MedicalHistory> getFilteredMedHistList();

    /** Returns an unmodifiable view of the filtered doctor list */
    ObservableList<Doctor> getFilteredDoctorList();

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Patient> predicate);

    /**
     * Updates the filter of the filtered medical history list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMedHistList(Predicate<MedicalHistory> predicate);

    /**
     * Updates the filter of the filtered doctor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDoctorList(Predicate<Doctor> predicate);

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

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
     * Returns true if a doctor with the same identity as {@code doctor} exists in docX.
     */
    boolean hasDoctor(Doctor toAdd);

    /**
     * Adds the given doctor.
     * {@code doctor} must not already exist in docX.
     */
    void addDoctor(Doctor toAdd);
}
