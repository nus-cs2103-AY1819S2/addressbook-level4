package quickdocs.storage;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import quickdocs.commons.exceptions.IllegalValueException;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;
import quickdocs.testutil.Assert;

public class JsonAdaptedPatientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "S1234567X";
    private static final String INVALID_EMAIL = "Rachel@gmail";
    private static final String INVALID_ADDRESS = "@@@@@";
    private static final String INVALID_CONTACT = "888899999";
    private static final String INVALID_GENDER = "N";
    private static final String INVALID_DOB = "31-02-1999";
    private static final String INVALID_TAG = "D|4b3t35";

    private static final String VALID_NAME = "Rachel";
    private static final String VALID_NRIC = "S1234567J";
    private static final String VALID_EMAIL = "Rachel@gmail.com";
    private static final String VALID_ADDRESS = "4 admiralty Road";
    private static final String VALID_CONTACT = "88888888";
    private static final String VALID_GENDER = "F";
    private static final String VALID_DOB = "2000-02-29";
    private static final List<JsonAdaptedTag> VALID_TAG = new ArrayList<>(
            Arrays.asList(new JsonAdaptedTag("Diabetes")));

    private static final Patient EXPECTED = new Patient(
            new Name(VALID_NAME),
            new Nric(VALID_NRIC),
            new Email(VALID_EMAIL),
            new Address(VALID_ADDRESS),
            new Contact(VALID_CONTACT),
            new Gender(VALID_GENDER),
            new Dob(VALID_DOB),
            new ArrayList<Tag>(Arrays.asList(new Tag("Diabetes")))
    );

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL,
                VALID_ADDRESS, VALID_CONTACT, VALID_GENDER, VALID_DOB, VALID_TAG);

        assertEquals(EXPECTED, patient.toModelType());
    }

    @Test
    public void toModelType_invalidPatientdetails() {

        // Name testing
        JsonAdaptedPatient patient = new JsonAdaptedPatient(INVALID_NAME, VALID_NRIC, VALID_EMAIL,
                VALID_ADDRESS, VALID_CONTACT, VALID_GENDER, VALID_DOB, VALID_TAG);
        Assert.assertThrows(IllegalArgumentException.class, Name.NAME_CONSTRAINTS, patient::toModelType);

        // NRIC testing
        patient = new JsonAdaptedPatient(VALID_NAME, INVALID_NRIC, VALID_EMAIL,
                VALID_ADDRESS, VALID_CONTACT, VALID_GENDER, VALID_DOB, VALID_TAG);
        Assert.assertThrows(IllegalArgumentException.class, Nric.NRIC_CONSTRAINTS, patient::toModelType);

        // Email testing
        patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, INVALID_EMAIL,
                VALID_ADDRESS, VALID_CONTACT, VALID_GENDER, VALID_DOB, VALID_TAG);
        Assert.assertThrows(IllegalArgumentException.class, Email.EMAIL_CONSTRAINTS, patient::toModelType);

        // address testing
        patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL,
                INVALID_ADDRESS, VALID_CONTACT, VALID_GENDER, VALID_DOB, VALID_TAG);
        Assert.assertThrows(IllegalArgumentException.class, Address.ADDRESS_CONSTRAINTS, patient::toModelType);

        // contact testing
        patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL,
                VALID_ADDRESS, INVALID_CONTACT, VALID_GENDER, VALID_DOB, VALID_TAG);
        Assert.assertThrows(IllegalArgumentException.class, Contact.CONTACT_CONSTRAINTS, patient::toModelType);

        // gender testing
        patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL,
                VALID_ADDRESS, VALID_CONTACT, INVALID_GENDER, VALID_DOB, VALID_TAG);
        Assert.assertThrows(IllegalArgumentException.class, Gender.GENDER_CONSTRAINTS, patient::toModelType);

        // date of birth testing
        patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL,
                VALID_ADDRESS, VALID_CONTACT, VALID_GENDER, INVALID_DOB, VALID_TAG);
        Assert.assertThrows(IllegalArgumentException.class, Dob.DOB_CONSTRAINTS, patient::toModelType);

        // invalid tags
        List<JsonAdaptedTag> invalidTags = new ArrayList<>();
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL,
                VALID_ADDRESS, VALID_CONTACT, VALID_GENDER, VALID_DOB, invalidTags);
        Assert.assertThrows(IllegalValueException.class, patient::toModelType);
    }

}
