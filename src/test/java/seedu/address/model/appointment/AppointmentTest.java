package seedu.address.model.appointment;

import static seedu.address.model.util.SamplePatientsUtil.getSamplePatients;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Nric;

public class AppointmentTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), getSamplePatients());
    private Nric nricA = new Nric("S9123456A");
    private Nric nricB = new Nric("S9234568C");
    private LocalDate dateA = LocalDate.parse("2019-10-23");
    private LocalDate dateB = LocalDate.parse("2019-10-24");
    private LocalTime startA = LocalTime.parse("16:00");
    private LocalTime startB = LocalTime.parse("12:00");
    private LocalTime endA = LocalTime.parse("17:00");
    private LocalTime endB = LocalTime.parse("13:00");
    private String comment = "This is a comment";

    @Test
    public void equals() {
        Appointment appA = new Appointment(model.getPatientWithNric(nricA), dateA, startA, endA, comment);
        Appointment appB = new Appointment(model.getPatientWithNric(nricA), dateA, startA, endA, comment);

        // test equality of same referenced object
        Assert.assertTrue(appA.equals(appB));

        // test equality of two different object except Nric, which is the same
        Assert.assertTrue(appA.equals(appB));

        // test equality of two different appointemnt object with different date
        appA = new Appointment(model.getPatientWithNric(nricA), dateA, startA, endA, comment);
        appB = new Appointment(model.getPatientWithNric(nricA), dateB, startA, endA, comment);

        Assert.assertFalse(appA.equals(appB));

        // test equality of two different appointment object with different start time
        appA = new Appointment(model.getPatientWithNric(nricA), dateA, startA, endA, comment);
        appB = new Appointment(model.getPatientWithNric(nricA), dateA, startB, endA, comment);

        Assert.assertFalse(appA.equals(appB));

        // test equality of two different appointment object with different end time
        appA = new Appointment(model.getPatientWithNric(nricA), dateA, startA, endA, comment);
        appB = new Appointment(model.getPatientWithNric(nricA), dateA, startA, endB, comment);

        Assert.assertFalse(appA.equals(appB));
    }
}
