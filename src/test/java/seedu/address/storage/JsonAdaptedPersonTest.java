package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Major;
import seedu.address.model.person.MatricNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.YearOfStudy;
import seedu.address.testutil.Assert;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_MATRICNUMBER = "A0123456W @#$%";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_GENDER = "malefe";
    private static final String INVALID_YEAROFSTUDY = "1 1";
    private static final String INVALID_MAJOR = " ";
    private static final String INVALID_TAG = "#swim";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_MATRICNUMBER = BENSON.getMatricNumber().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_YEAROFSTUDY = BENSON.getYearOfStudy().toString();
    private static final String VALID_MAJOR = BENSON.getMajor().toString();

    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, null,
                VALID_ADDRESS, VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL,
                null, VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidYearOfStudy_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GENDER, INVALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = YearOfStudy.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullYearOfStudy_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_GENDER, null, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, YearOfStudy.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMatricNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = MatricNumber.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullMatricNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MatricNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMajor_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GENDER, VALID_YEAROFSTUDY, INVALID_MAJOR, VALID_TAGS);
        String expectedMessage = Major.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullMajor_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_GENDER, VALID_YEAROFSTUDY, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Major.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_MATRICNUMBER, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_GENDER, VALID_YEAROFSTUDY, VALID_MAJOR, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
