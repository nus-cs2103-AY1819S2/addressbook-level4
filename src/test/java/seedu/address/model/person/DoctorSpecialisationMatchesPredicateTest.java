package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.doctor.DoctorMatch;
import seedu.address.model.person.doctor.DoctorSpecialisationMatchesPredicate;
import seedu.address.model.person.specialisation.Specialisation;
import seedu.address.testutil.DoctorBuilder;

public class DoctorSpecialisationMatchesPredicateTest {

    @Test
    public void equals() {
        DoctorMatch firstPredicateDoctorMatch = new DoctorMatch(new Specialisation("acupuncture"),
                new AppointmentDate("2019-06-05"), new AppointmentTime("11:00"));
        DoctorMatch secondPredicateDoctorMatch = new DoctorMatch(new Specialisation("general"),
                new AppointmentDate("2019-07-10"), new AppointmentTime("14:00"));

        DoctorSpecialisationMatchesPredicate firstPredicate =
                new DoctorSpecialisationMatchesPredicate(firstPredicateDoctorMatch);
        DoctorSpecialisationMatchesPredicate secondPredicate =
                new DoctorSpecialisationMatchesPredicate(secondPredicateDoctorMatch);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DoctorSpecialisationMatchesPredicate firstPredicateCopy =
                new DoctorSpecialisationMatchesPredicate(firstPredicateDoctorMatch);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different DoctorMatch object -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_doctorMatchContainsKeywords_returnsTrue() {
        // One keyword
        DoctorSpecialisationMatchesPredicate predicate =
                new DoctorSpecialisationMatchesPredicate(new DoctorMatch(new Specialisation("acupuncture"),
                        new AppointmentDate("2019-07-08"), new AppointmentTime("14:00")));
        assertTrue(predicate.test(new DoctorBuilder().withSpecs("acupuncture", "general").build()));

        // Mixed-case keywords
        predicate = new DoctorSpecialisationMatchesPredicate(new DoctorMatch(new Specialisation("AcuPUNcTure"),
                new AppointmentDate("2019-07-08"), new AppointmentTime("14:00")));
        assertTrue(predicate.test(new DoctorBuilder().withSpecs("acupuncture", "general").build()));
    }

    @Test
    public void test_specDoesNotContainKeywords_returnsFalse() {

        // Non-matching keyword
        DoctorSpecialisationMatchesPredicate predicate = new DoctorSpecialisationMatchesPredicate(
                new DoctorMatch(new Specialisation("surgery"),
                new AppointmentDate("2019-07-08"), new AppointmentTime("14:00")));
        assertFalse(predicate.test(new DoctorBuilder().withSpecs("acupuncture", "general").build()));

    }


}
