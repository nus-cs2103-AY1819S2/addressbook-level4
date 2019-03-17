package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Contact;
import seedu.address.model.patient.Dob;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class JsonAdaptedAppointmentTest {
    private static final String INVALID_DATE = "2019-20-20";
    private static final String INVALID_START = "25:00";
    private static final String INVALID_END = "20:60";

    private static final String VALID_DATE = "2019-10-23";
    private static final String VALID_START = "16:00";
    private static final String VALID_END = "17:00";
    private static final String VALID_COMMENT = "Weekly checkup";

    // create a valid patient
    private static final String VALID_NAME = "Rachel";
    private static final String VALID_NRIC = "S1234567J";
    private static final String VALID_EMAIL = "Rachel@gmail.com";
    private static final String VALID_ADDRESS = "4 admiralty Road";
    private static final String VALID_CONTACT = "88888888";
    private static final String VALID_GENDER = "F";
    private static final String VALID_DOB = "2000-02-29";
    private static final List<JsonAdaptedTag> VALID_TAG = new ArrayList<>(
            Arrays.asList(new JsonAdaptedTag("Diabetes")));

    private static final Patient VALID_PATIENT = new Patient(
            new Name(VALID_NAME),
            new Nric(VALID_NRIC),
            new Email(VALID_EMAIL),
            new Address(VALID_ADDRESS),
            new Contact(VALID_CONTACT),
            new Gender(VALID_GENDER),
            new Dob(VALID_DOB),
            new ArrayList<Tag>(Arrays.asList(new Tag("Diabetes")))
    );

    private static final Appointment VALID_APPOINTMENT = new Appointment(
            VALID_PATIENT, LocalDate.parse(VALID_DATE), LocalTime.parse(VALID_START),
            LocalTime.parse(VALID_END), VALID_COMMENT);

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_APPOINTMENT);
        assertEquals(VALID_APPOINTMENT, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalArgumentException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_PATIENT, INVALID_DATE, VALID_START, VALID_END, VALID_COMMENT);
        String expectedMessage = "Date format: YYYY-MM-DD";
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidStart_throwsIllegalArgumentException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_PATIENT, VALID_DATE, INVALID_START, VALID_END, VALID_COMMENT);
        String expectedMessage = "Time format: HH:MM";
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidEnd_throwsIllegalArgumentException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_PATIENT, VALID_DATE, VALID_START, INVALID_END, VALID_COMMENT);
        String expectedMessage = "Time format: HH:MM";
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, appointment::toModelType);
    }
}
