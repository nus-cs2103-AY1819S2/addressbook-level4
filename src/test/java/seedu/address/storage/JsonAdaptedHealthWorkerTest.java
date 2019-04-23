package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.tag.Skills;
import seedu.address.testutil.Assert;

public class JsonAdaptedHealthWorkerTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_NRIC = "";
    private static final String INVALID_ORGANISATION = "";

    private static final String VALID_NAME = BETTY.getName().toString();
    private static final String VALID_NRIC = BETTY.getNric().toString();
    private static final String VALID_PHONE = BETTY.getPhone().toString();
    private static final String VALID_ORGANISATION = BETTY.getOrganization().toString();
    private static final String VALID_SKILLS = BETTY.getSkills().toString();

    @Test
    public void toModelType_validHealthWorkerDetails_returnsHealthWorker() throws Exception {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(BETTY);
        assertEquals(BETTY, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person =
                new JsonAdaptedHealthWorker(INVALID_NAME, VALID_PHONE, VALID_NRIC, VALID_ORGANISATION, VALID_SKILLS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(null, VALID_PHONE, VALID_NRIC,
                VALID_ORGANISATION, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(VALID_NAME, INVALID_PHONE, VALID_NRIC,
                VALID_ORGANISATION, VALID_SKILLS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(VALID_NAME, null, VALID_NRIC,
                VALID_ORGANISATION, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(VALID_NAME, VALID_PHONE, INVALID_NRIC,
                VALID_ORGANISATION, VALID_SKILLS);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(VALID_NAME, VALID_PHONE, null,
                VALID_ORGANISATION, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidOrganisation_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(VALID_NAME, VALID_PHONE, VALID_NRIC,
                INVALID_ORGANISATION, VALID_SKILLS);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullOrganisation_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(VALID_NAME, VALID_PHONE, VALID_NRIC,
                null, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Organization.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSkills_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(VALID_NAME, VALID_PHONE, VALID_NRIC,
                VALID_ORGANISATION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Skills.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
