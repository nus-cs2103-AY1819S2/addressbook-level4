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
import seedu.address.logic.commands.doctor.AddDoctorCommand;
import seedu.address.logic.commands.exceptions.CommandException;
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
import seedu.address.testutil.DoctorBuilder;


public class AddDoctorCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_doctorAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDoctorAdded modelStub = new ModelStubAcceptingDoctorAdded();
        Doctor validDoctor = new DoctorBuilder().build();

        CommandResult commandResult = new AddDoctorCommand(validDoctor).execute(modelStub, commandHistory);

        assertEquals(String.format(AddDoctorCommand.MESSAGE_SUCCESS, validDoctor), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDoctor), modelStub.doctorsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateDoctor_throwsCommandException() throws Exception {
        Doctor validDoctor = new DoctorBuilder().build();
        AddDoctorCommand addDoctorCommand = new AddDoctorCommand(validDoctor);
        ModelStub modelStub = new ModelStubWithDoctor(validDoctor);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);
        addDoctorCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Doctor alvina = new DoctorBuilder().withName("Alvina").build();
        Doctor alvin = new DoctorBuilder().withName("Alvin").build();
        AddDoctorCommand addAlvinaCommand = new AddDoctorCommand(alvina);
        AddDoctorCommand addAlvinCommand = new AddDoctorCommand(alvin);

        // same object -> returns true
        assertTrue(addAlvinaCommand.equals(addAlvinaCommand));

        // same values -> returns true
        AddDoctorCommand addAlvinaCommandCopy = new AddDoctorCommand(alvina);
        assertTrue(addAlvinaCommand.equals(addAlvinaCommandCopy));

        // different types -> returns false
        assertFalse(addAlvinaCommand.equals(1));

        // null -> returns false
        assertFalse(addAlvinaCommand.equals(null));

        // different person -> returns false
        assertFalse(addAlvinaCommand.equals(addAlvinCommand));
    }

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
        public void setDocX(ReadOnlyDocX docX) {
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
        public void addPatient(Patient patient) {
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

        @Override
        public boolean hasMedHist(MedicalHistory medicalHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMedHist(MedicalHistory medicalHistory) {
            throw new AssertionError("This method should not be called.");
        }

        // Needed to be implemented later
        @Override
        public boolean hasPrescription(Prescription prescription) {
            return false;
        }

        // Needed to be implemented later
        @Override
        public void addPrescription(Prescription prescription) {
        }

        @Override
        public void setPrescription(Prescription target, Prescription editedPrescription) {
        }

        @Override
        public void setMedHist(MedicalHistory target, MedicalHistory editedMedHist) {
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
        public ObservableList<Patient> getFilteredPatientList() {
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
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMedHistList(Predicate<MedicalHistory> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Doctor> getFilteredDoctorList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDoctorList(Predicate<Doctor> predicate) {
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
        public ReadOnlyProperty<MedicalHistory> selectedMedHistProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Patient getSelectedPatient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public MedicalHistory getSelectedMedHist() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedMedHist(MedicalHistory medHist) {
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
     * A Model Stub that contains a single doctor.
     */
    private class ModelStubWithDoctor extends ModelStub {
        private final Doctor doctor;

        ModelStubWithDoctor(Doctor doctor) {
            requireNonNull(doctor);
            this.doctor = doctor;
        }

        @Override
        public boolean hasDoctor(Doctor doctor) {
            requireNonNull(doctor);
            return this.doctor.isSameDoctor(doctor);
        }
    }

    /**
     * A Model stub that always accept the doctor being added.
     */
    private class ModelStubAcceptingDoctorAdded extends ModelStub {
        final ArrayList<Doctor> doctorsAdded = new ArrayList<>();

        @Override
        public boolean hasDoctor(Doctor doctor) {
            requireNonNull(doctor);
            return doctorsAdded.stream().anyMatch(doctor::isSameDoctor);
        }

        @Override
        public void addDoctor(Doctor doctor) {
            requireNonNull(doctor);
            doctorsAdded.add(doctor);
        }

        @Override
        public void commitDocX() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyDocX getDocX() {
            return new DocX();
        }
    }

}
