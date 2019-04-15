package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_GENERAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STROKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_STEVEN;
import static seedu.address.testutil.TypicalDoctors.ALVINA;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.getTypicalDocX;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.PersonIdCounter;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.exceptions.DuplicatePatientException;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PatientBuilder;


public class DocXTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DocX docX = new DocX();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), docX.getPatientList());
        assertEquals(Collections.emptyList(), docX.getDoctorList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        docX.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyDocX_replacesData() {
        DocX newData = getTypicalDocX();
        docX.resetData(newData);
        assertEquals(newData, docX);
    }

    @Test
    public void resetData_withDuplicatePatients_throwsDuplicatePatientException() {
        // Two persons with the same identity fields
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_STROKE)
                .build();
        List<Patient> newPatients = Arrays.asList(ALICE, editedAlice);
        DocXStub newData = new DocXStub(newPatients);

        thrown.expect(DuplicatePatientException.class);
        docX.resetData(newData);
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        docX.hasPatient(null);
    }

    @Test
    public void hasPatient_personNotInDocX_returnsFalse() {
        assertFalse(docX.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_personInDocX_returnsTrue() {
        docX.addPatient(ALICE);
        assertTrue(docX.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_personWithSameIdentityFieldsInDocX_returnsTrue() {
        docX.addPatient(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_STROKE)
                .build();
        assertTrue(docX.hasPatient(editedAlice));
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        docX.getPatientList().remove(0);
    }

    @Test
    public void hasDoctor_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        docX.hasDoctor(null);
    }

    @Test
    public void hasDoctor_personNotInDocX_returnsFalse() {
        assertFalse(docX.hasDoctor(ALVINA));
    }

    @Test
    public void hasDoctor_personInDocX_returnsTrue() {
        docX.addDoctor(ALVINA);
        assertTrue(docX.hasDoctor(ALVINA));
    }

    @Test
    public void hasDoctor_personWithSameIdentityFieldsInDocX_returnsTrue() {
        docX.addDoctor(ALVINA);
        Doctor editedAlvina = new DoctorBuilder(ALVINA).withGender(VALID_GENDER_STEVEN).withYear(VALID_YEAR_STEVEN)
                .withSpecs(VALID_SPECIALISATION_GENERAL).build();
        assertTrue(docX.hasDoctor(editedAlvina));
    }

    @Test
    public void getDoctorList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        docX.getDoctorList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        docX.addListener(listener);
        docX.addPatient(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        docX.addListener(listener);
        docX.removeListener(listener);
        docX.addPatient(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyDocX whose persons list can violate interface constraints.
     */
    private static class DocXStub implements ReadOnlyDocX {
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();
        private final ObservableList<MedicalHistory> medHists = FXCollections.observableArrayList();
        private final ObservableList<Doctor> doctors = FXCollections.observableArrayList();
        private final ObservableList<Prescription> prescriptions = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private final PersonIdCounter personIdCounter = PersonIdCounter.getInstance();

        DocXStub(Collection<Patient> patients) {
            this.patients.setAll(patients);
        }


        @Override
        public ObservableList<Patient> getPatientList() {
            return patients;
        }

        @Override
        public ObservableList<MedicalHistory> getMedHistList() {
            return medHists;
        }

        @Override
        public ObservableList<Doctor> getDoctorList() {
            return doctors;
        }

        @Override
        public ObservableList<Prescription> getPrescriptionList() {
            return prescriptions;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }

        @Override
        public PersonIdCounter getPersonIdCounter() {
            return personIdCounter;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
