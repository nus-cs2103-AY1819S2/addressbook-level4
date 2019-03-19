package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedEquipment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEquipments.HWIYOHCC;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.equipment.Address;
import seedu.address.model.equipment.Email;
import seedu.address.model.equipment.Name;
import seedu.address.model.equipment.Phone;
import seedu.address.model.equipment.SerialNumber;
import seedu.address.testutil.Assert;

public class JsonAdaptedEquipmentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_SERIAL_NUMBER = "A0$SD9L";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = HWIYOHCC.getName().toString();
    private static final String VALID_PHONE = HWIYOHCC.getPhone().toString();
    private static final String VALID_EMAIL = HWIYOHCC.getEmail().toString();
    private static final String VALID_ADDRESS = HWIYOHCC.getAddress().toString();
    private static final String VALID_SERIAL_NUMBER = HWIYOHCC.getSerialNumber().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = HWIYOHCC.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedEquipment person = new JsonAdaptedEquipment(HWIYOHCC);
        assertEquals(HWIYOHCC, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEquipment person =
                new JsonAdaptedEquipment(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SERIAL_NUMBER,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEquipment person = new JsonAdaptedEquipment(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SERIAL_NUMBER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedEquipment person =
                new JsonAdaptedEquipment(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SERIAL_NUMBER,
                        VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedEquipment person = new JsonAdaptedEquipment(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_SERIAL_NUMBER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedEquipment person =
                new JsonAdaptedEquipment(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_SERIAL_NUMBER,
                        VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedEquipment person = new JsonAdaptedEquipment(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_SERIAL_NUMBER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedEquipment person =
                new JsonAdaptedEquipment(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_SERIAL_NUMBER,
                        VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedEquipment person = new JsonAdaptedEquipment(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_SERIAL_NUMBER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSerialNumber_throwsIllegalValueException() {
        JsonAdaptedEquipment person =
                new JsonAdaptedEquipment(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_SERIAL_NUMBER,
                        VALID_TAGS);
        String expectedMessage = SerialNumber.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSerialNumber_throwsIllegalValueException() {
        JsonAdaptedEquipment person = new JsonAdaptedEquipment(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SerialNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedEquipment person =
                new JsonAdaptedEquipment(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SERIAL_NUMBER,
                        invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
