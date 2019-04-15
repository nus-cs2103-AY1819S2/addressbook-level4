package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.DoctorHasAppointmentPredicate;
import seedu.address.model.person.specialisation.Specialisation;
import seedu.address.testutil.DoctorBuilder;

public class DoctorHasAppointmentPredicateTest {

    @Test
    public void equals() {

        List<Appointment> apptset1 = new ArrayList<>();
        apptset1.add(new Appointment(new AppointmentPatientId("1"), new AppointmentDoctorId("8"),
                new AppointmentDate("2019-06-20"), new AppointmentTime("10:00")));
        List<Appointment> apptset2 = new ArrayList<>();
        apptset2.add(new Appointment(new AppointmentPatientId("1"), new AppointmentDoctorId("8"),
                new AppointmentDate("2019-06-22"), new AppointmentTime("13:00")));
        Specialisation spec1 = new Specialisation("general");
        Specialisation spec2 = new Specialisation("surgery");


        DoctorHasAppointmentPredicate firstPredicate =
                new DoctorHasAppointmentPredicate(apptset1, spec1);
        DoctorHasAppointmentPredicate secondPredicate =
                new DoctorHasAppointmentPredicate(apptset2, spec2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DoctorHasAppointmentPredicate firstPredicateCopy =
                new DoctorHasAppointmentPredicate(apptset1, spec1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different doctor -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_doctorHasAppointment_returnsTrue() {
        Specialisation spec1 = new Specialisation("general");

        // doctor has no appointment and specialisation matches -> true (correct doctor and is available)
        Doctor testDoctor = new DoctorBuilder().withSpecs("general").build();
        List<Appointment> apptset = new ArrayList<>();
        apptset.add(new Appointment(new AppointmentPatientId("1"),
                new AppointmentDoctorId(String.valueOf(testDoctor.getId().personId + 1)),
                new AppointmentDate("2019-06-22"), new AppointmentTime("13:00")));
        DoctorHasAppointmentPredicate predicate = new DoctorHasAppointmentPredicate(apptset, spec1);
        assertTrue(predicate.test(testDoctor));
    }

    @Test
    public void test_doctorHasAppointmentOrWrongSpec_returnsFalse() {
        Specialisation spec1 = new Specialisation("general");
        Specialisation spec2 = new Specialisation("surgery");

        // doctor has appointment and specialisation matches -> false (doctor is not available)
        Doctor testDoctor = new DoctorBuilder().withSpecs("acupuncture", "general").build();
        List<Appointment> apptset = new ArrayList<>();
        apptset.add(new Appointment(new AppointmentPatientId("1"), new AppointmentDoctorId(testDoctor.getIdToString()),
                new AppointmentDate("2019-06-22"), new AppointmentTime("13:00")));
        DoctorHasAppointmentPredicate predicate =
                new DoctorHasAppointmentPredicate(apptset, spec1);
        assertFalse(predicate.test(testDoctor));

        // doctor has appointment but specialisation does not match -> false (irrelevant doctor)
        predicate = new DoctorHasAppointmentPredicate(apptset, spec2);
        assertFalse(predicate.test(testDoctor));
    }
}
