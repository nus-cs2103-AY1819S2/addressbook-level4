package seedu.hms.storage;

import static org.junit.Assert.assertEquals;
import static seedu.hms.storage.JsonAdaptedCustomer.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.hms.testutil.TypicalCustomers.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.testutil.Assert;

public class JsonAdaptedCustomerTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DOB = "12/24/4212";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_IDENTIFICATION_NO = "Z51234";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_DOB = BENSON.getDateOfBirth().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_IDENTIFICATION_NO = BENSON.getIdNum().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
        .map(JsonAdaptedTag::new)
        .collect(Collectors.toList());

    @Test
    public void toModelTypeValidCustomerDetailsReturnsCustomer() throws Exception {
        JsonAdaptedCustomer customer = new JsonAdaptedCustomer(BENSON);
        assertEquals(BENSON, customer.toModelType());
    }

    @Test
    public void toModelTypeInvalidNameThrowsIllegalValueException() {
        JsonAdaptedCustomer customer =
            new JsonAdaptedCustomer(INVALID_NAME, VALID_PHONE, VALID_DOB, VALID_EMAIL, VALID_IDENTIFICATION_NO,
                VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeNullNameThrowsIllegalValueException() {
        JsonAdaptedCustomer customer = new JsonAdaptedCustomer(null, VALID_PHONE, VALID_DOB, VALID_EMAIL,
            VALID_IDENTIFICATION_NO, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeInvalidPhoneThrowsIllegalValueException() {
        JsonAdaptedCustomer customer =
            new JsonAdaptedCustomer(VALID_NAME, INVALID_PHONE, VALID_DOB, VALID_EMAIL, VALID_IDENTIFICATION_NO,
                VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeNullDateOfBirthThrowsIllegalValueException() {
        JsonAdaptedCustomer customer = new JsonAdaptedCustomer(VALID_NAME, VALID_PHONE, null, VALID_EMAIL,
            VALID_IDENTIFICATION_NO, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeInvalidDateOfBirthThrowsIllegalValueException() {
        JsonAdaptedCustomer customer =
            new JsonAdaptedCustomer(VALID_NAME, VALID_PHONE, INVALID_DOB, VALID_EMAIL, VALID_IDENTIFICATION_NO,
                VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeNullPhoneThrowsIllegalValueException() {
        JsonAdaptedCustomer customer =
            new JsonAdaptedCustomer(VALID_NAME, null, VALID_DOB, VALID_EMAIL, VALID_IDENTIFICATION_NO,
                VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeInvalidEmailThrowsIllegalValueException() {
        JsonAdaptedCustomer customer =
            new JsonAdaptedCustomer(VALID_NAME, VALID_PHONE, VALID_DOB, INVALID_EMAIL, VALID_IDENTIFICATION_NO,
                VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeNullEmailThrowsIllegalValueException() {
        JsonAdaptedCustomer customer =
            new JsonAdaptedCustomer(VALID_NAME, VALID_PHONE, VALID_DOB, null, VALID_IDENTIFICATION_NO,
                VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeInvalidIdentificationNoThrowsIllegalValueException() {
        JsonAdaptedCustomer customer =
            new JsonAdaptedCustomer(VALID_NAME, VALID_PHONE, VALID_DOB, VALID_EMAIL, INVALID_IDENTIFICATION_NO,
                VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = IdentificationNo.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeNullIdentificationNoThrowsIllegalValueException() {
        JsonAdaptedCustomer customer = new JsonAdaptedCustomer(VALID_NAME, VALID_PHONE, VALID_DOB, VALID_EMAIL,
            null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IdentificationNo.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeInvalidhmsThrowsIllegalValueException() {
        JsonAdaptedCustomer customer =
            new JsonAdaptedCustomer(VALID_NAME, VALID_PHONE, VALID_DOB, VALID_EMAIL, VALID_IDENTIFICATION_NO,
                INVALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeNullAddressThrowsIllegalValueException() {
        JsonAdaptedCustomer customer = new JsonAdaptedCustomer(VALID_NAME, VALID_PHONE, VALID_DOB, VALID_EMAIL,
            VALID_IDENTIFICATION_NO, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, customer::toModelType);
    }

    @Test
    public void toModelTypeInvalidTagsThrowsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedCustomer customer =
            new JsonAdaptedCustomer(VALID_NAME, VALID_PHONE, VALID_DOB, VALID_EMAIL, VALID_IDENTIFICATION_NO,
                VALID_ADDRESS,
                invalidTags);
        Assert.assertThrows(IllegalValueException.class, customer::toModelType);
    }

}
