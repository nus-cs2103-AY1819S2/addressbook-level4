package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.core.UserType;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Company;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NRIC = "notrealnric";
    private static final String INVALID_COMPANY = " ";
    private static final String INVALID_SECTION = " ";
    private static final String INVALID_RANK = "PPPP";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "65";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PASSWORD = "  ";

    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final String VALID_SECTION = BENSON.getSection().toString();
    private static final String VALID_RANK = BENSON.getRank().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_PASSWORD = BENSON.getPassword().toString();

    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NRIC, VALID_COMPANY, VALID_SECTION, VALID_RANK, VALID_NAME,
                        VALID_PHONE, VALID_TAGS, VALID_PASSWORD, UserType.ADMIN);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NRIC, INVALID_COMPANY, VALID_SECTION, VALID_RANK, VALID_NAME,
                        VALID_PHONE, VALID_TAGS, VALID_PASSWORD, UserType.GENERAL);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NRIC, VALID_COMPANY, VALID_SECTION, VALID_RANK, INVALID_NAME,
                        VALID_PHONE, VALID_TAGS, VALID_PASSWORD, UserType.GENERAL);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NRIC, VALID_COMPANY, VALID_SECTION, VALID_RANK, null,
                        VALID_PHONE, VALID_TAGS, VALID_PASSWORD, UserType.ADMIN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NRIC, VALID_COMPANY, VALID_SECTION, VALID_RANK, VALID_NAME,
                        INVALID_PHONE, VALID_TAGS, VALID_PASSWORD, UserType.ADMIN);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NRIC, VALID_COMPANY, VALID_SECTION, VALID_RANK, VALID_NAME,
                        null, VALID_TAGS, VALID_PASSWORD, UserType.ADMIN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NRIC, VALID_COMPANY, VALID_SECTION, VALID_RANK, VALID_NAME,
                        VALID_PHONE, invalidTags, VALID_PASSWORD, UserType.ADMIN);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }
}
