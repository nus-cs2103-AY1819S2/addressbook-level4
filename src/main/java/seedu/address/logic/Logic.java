package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyDocX;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.prescription.Prescription;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the DocX.
     *
     * @see seedu.address.model.Model#getDocX()
     */
    ReadOnlyDocX getDocX();

    /** Returns an unmodifiable view of the filtered list of patients */
    ObservableList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered list of medical histories */
    ObservableList<MedicalHistory> getFilteredMedHistList();

    /** Returns an unmodifiable view of the filtered list of medical histories */
    ObservableList<Appointment> getFilteredAppointmentList();

    /** Returns an unmodifiable view of the filtered list of prescriptions */
    ObservableList<Prescription> getFilteredPrescriptionList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' docX file path.
     */
    Path getDocXFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected patient in the filtered patient list.
     * null if no patient is selected.
     *
     * @see seedu.address.model.Model#selectedPatientProperty()
     */
    ReadOnlyProperty<Patient> selectedPatientProperty();

    /**
     * Selected medical history in the filtered medHist list.
     * null if no medHist is selected.
     *
     * @see seedu.address.model.Model#selectedMedHistProperty()
     */
    ReadOnlyProperty<MedicalHistory> selectedMedHistProperty();

    /**
     * Selected appointment in the filtered appointment list.
     * null if no patient is selected.
     *
     * @see seedu.address.model.Model#selectedAppointmentProperty()
     */
    ReadOnlyProperty<Appointment> selectedAppointmentProperty();

    /**
     * Selected prescription in the filtered prescription list.
     * null if no prescription is selected.
     *
     * @see seedu.address.model.Model#selectedPrescriptionProperty()
     */
    ReadOnlyProperty<Prescription> selectedPrescriptionProperty();

    /**
     * Sets the selected patient in the filtered patient list.
     *
     * @see seedu.address.model.Model#setSelectedPatient(Patient)
     */
    void setSelectedPatient(Patient patient);

    /**
     * Sets the selected medical history in the filtered medHist list.
     *
     * @see seedu.address.model.Model#setSelectedMedHist(MedicalHistory)
     */
    void setSelectedMedHist(MedicalHistory medHist);

    /**
     * Sets the selected medical history in the filtered medHist list.
     *
     * @see seedu.address.model.Model#setSelectedPrescription(Prescription)
     */
    void setSelectedPrescription(Prescription prescription);

    /**
     * Sets the selected appointment in the filtered appointment list.
     *
     * @see seedu.address.model.Model#setSelectedAppointment(Appointment)
     */
    void setSelectedAppointment(Appointment appointment);

    /** Returns an unmodifiable view of the filtered list of doctors */
    ObservableList<Doctor> getFilteredDoctorList();

    /**
     * Selected doctor in the filtered doctor list.
     * null if no doctor is selected.
     *
     * @see seedu.address.model.Model#selectedDoctorProperty()
     */
    ReadOnlyProperty<Doctor> selectedDoctorProperty();

    /**
     * Sets the selected doctor in the filtered doctor list.
     *
     * @see seedu.address.model.Model#setSelectedDoctor(Doctor)
     */
    void setSelectedDoctor(Doctor doctor);
}
