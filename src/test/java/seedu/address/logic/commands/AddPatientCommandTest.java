package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.patient.AddPatientCommand;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDocX;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.PatientBuilder;

public class AddPatientCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPatientCommand(null);
    }

    @Test
    public void execute_patientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPatientAdded modelStub = new ModelStubAcceptingPatientAdded();
        Patient validPatient = new PatientBuilder().build();

        CommandResult commandResult = new AddPatientCommand(validPatient).execute(modelStub, commandHistory);

        assertEquals(String.format(AddPatientCommand.MESSAGE_SUCCESS, validPatient), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPatient), modelStub.patientsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() throws Exception {
        Patient validPatient = new PatientBuilder().build();
        AddPatientCommand addPatientCommand = new AddPatientCommand(validPatient);
        ModelStub modelStub = new ModelStubWithPatient(validPatient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);
        addPatientCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Patient alice = new PatientBuilder().withName("Alice").build();
        Patient bob = new PatientBuilder().withName("Bob").build();
        AddPatientCommand addAliceCommand = new AddPatientCommand(alice);
        AddPatientCommand addBobCommand = new AddPatientCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddPatientCommand addAliceCommandCopy = new AddPatientCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different patient -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredMedHistList(Comparator<MedicalHistory> medHistComparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredPrescriptionList(Comparator<Prescription> prescriptionComparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Patient getPatientById(PersonId patientId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Doctor getDoctorById(PersonId doctorId) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDocXFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDocXFilePath(Path docXFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDocX(ReadOnlyDocX newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDocX getDocX() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatient(Patient target, Patient editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDoctor(Doctor target, Doctor editedDoctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointment(Appointment appointment, Appointment appointment2) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Appointment> selectedAppointmentProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Appointment getSelectedAppointment() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        // Needed to be implemented later
        @Override
        public boolean hasMedHist(MedicalHistory medicalHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMedHist(MedicalHistory medicalHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMedHist(MedicalHistory target, MedicalHistory editedMedHist) {
            throw new AssertionError("This method should not be called.");
        }

        // Needed to be implemented later
        @Override
        public boolean hasPrescription(Prescription prescription) {
            return false;
        }

        @Override
        public void addPrescription(Prescription prescription) {
        }

        @Override
        public void setPrescription(Prescription target, Prescription editedPrescription) {
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Doctor> getFilteredDoctorList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<MedicalHistory> getFilteredMedHistList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Prescription> getFilteredPrescriptionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDoctorList(Predicate<Doctor> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMedHistList(Predicate<MedicalHistory> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<MedicalHistory> selectedMedHistProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public MedicalHistory getSelectedMedHist() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedMedHist(MedicalHistory medHist) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPrescriptionList(Predicate<Prescription> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Prescription> selectedPrescriptionProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Prescription getSelectedPrescription() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPrescription(Prescription medHist) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitDocX() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Patient> selectedPatientProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Patient getSelectedPatient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDoctor(Doctor toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDoctor(Doctor toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Doctor> selectedDoctorProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Doctor getSelectedDoctor() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedDoctor(Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDoctor(Doctor target) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single patient.
     */
    private class ModelStubWithPatient extends ModelStub {
        private final Patient patient;

        ModelStubWithPatient(Patient patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return this.patient.isSamePatient(patient);
        }
    }

    /**
     * A Model stub that always accept the patient being added.
     */
    private class ModelStubAcceptingPatientAdded extends ModelStub {
        final ArrayList<Patient> patientsAdded = new ArrayList<>();

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return patientsAdded.stream().anyMatch(patient::isSamePatient);
        }

        @Override
        public void addPatient(Patient patient) {
            requireNonNull(patient);
            patientsAdded.add(patient);
        }

        @Override
        public void commitDocX() {
            // called by {@code AddPatientCommand#execute()}
        }

        @Override
        public ReadOnlyDocX getDocX() {
            return new DocX();
        }
    }

}
