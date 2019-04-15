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
import seedu.address.model.person.doctor.AppointmentContainsDoctorPredicate;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.DoctorsMatch;
import seedu.address.testutil.DoctorBuilder;

public class AppointmentContainsDoctorPredicateTest {

    @Test
    public void equals() {

        List<Doctor> doc1 = new ArrayList<>();
        doc1.add(new DoctorBuilder().withName("Elvina Tan").build());
        List<Doctor> doc2 = new ArrayList<>();
        doc2.add(new DoctorBuilder().withSpecs("surgery").build());

        AppointmentDate date1 = new AppointmentDate("2019-06-20");
        AppointmentDate date2 = new AppointmentDate("2019-07-01");

        AppointmentTime time1 = new AppointmentTime("09:00");
        AppointmentTime time2 = new AppointmentTime("14:00");

        DoctorsMatch dm1 = new DoctorsMatch(doc1, date1, time1);
        DoctorsMatch dm2 = new DoctorsMatch(doc2, date2, time2);


        AppointmentContainsDoctorPredicate firstPredicate =
                new AppointmentContainsDoctorPredicate(dm1);
        AppointmentContainsDoctorPredicate secondPredicate =
                new AppointmentContainsDoctorPredicate(dm2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AppointmentContainsDoctorPredicate firstPredicateCopy =
                new AppointmentContainsDoctorPredicate(new DoctorsMatch(doc1, date1, time1));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different doctor -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_apptContainsDoctor_returnsTrue() {
        AppointmentDate date1 = new AppointmentDate("2019-06-20");
        AppointmentTime time1 = new AppointmentTime("09:00");

        // appt contains doctor
        Doctor testDoctor = new DoctorBuilder().build();
        Appointment appt = new Appointment(new AppointmentPatientId("1"),
                new AppointmentDoctorId(testDoctor.getIdToString()),
                new AppointmentDate("2019-06-20"), new AppointmentTime("09:00"));
        List<Doctor> doc1 = new ArrayList<>();
        doc1.add(testDoctor);
        DoctorsMatch dm1 = new DoctorsMatch(doc1, date1, time1);
        AppointmentContainsDoctorPredicate predicate = new AppointmentContainsDoctorPredicate(dm1);
        assertTrue(predicate.test(appt));

    }

    @Test
    public void test_doctorHasAppointmentOrWrongSpec_returnsFalse() {
        AppointmentDate date1 = new AppointmentDate("2019-06-20");
        AppointmentTime time1 = new AppointmentTime("09:00");

        // appt does not contains doctor
        Doctor testDoctor = new DoctorBuilder().build();
        Appointment appt = new Appointment(new AppointmentPatientId("1"),
                new AppointmentDoctorId(String.valueOf(testDoctor.getId().personId + 1)),
                new AppointmentDate("2019-06-20"), new AppointmentTime("09:00"));
        List<Doctor> doc1 = new ArrayList<>();
        doc1.add(testDoctor);
        DoctorsMatch dm1 = new DoctorsMatch(doc1, date1, time1);
        AppointmentContainsDoctorPredicate predicate = new AppointmentContainsDoctorPredicate(dm1);
        assertFalse(predicate.test(appt));
    }
}
