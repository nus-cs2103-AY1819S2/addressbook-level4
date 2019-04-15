package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetime.DateBase;
import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.description.Description;
import seedu.address.model.nextofkin.NextOfKinRelation;
import seedu.address.model.patient.DrugAllergy;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Sex;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "1234567";
    private static final String INVALID_DOB = "09/06/1995";
    private static final String INVALID_SEX = "09/06/1995";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DRUG_ALLERGY = "$%$";
    private static final String INVALID_DESC = "    ";
    private static final String INVALID_KIN_RELATION = "!Mother$";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = ((Patient) BENSON).getNric().getNric();
    private static final String VALID_DOB = ((Patient) BENSON).getDateOfBirth().getRawFormat();
    private static final String VALID_SEX = ((Patient) BENSON).getSex().getSex();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_DRUG_ALLERGY = ((Patient) BENSON).getDrugAllergy().toString();
    private static final String VALID_DESC = ((Patient) BENSON).getPatientDesc().toString();
    private static final String VALID_TEETH = ((Patient) BENSON).getTeeth().getRawFormat();
    private static final JsonAdaptedNextOfKin VALID_KIN = new JsonAdaptedNextOfKin(((Patient) BENSON).getNextOfKin());
    private static final String VALID_KIN_NAME = ((Patient) BENSON).getNextOfKin().getName().toString();
    private static final String VALID_KIN_RELATION = ((Patient) BENSON).getNextOfKin().getKinRelation().toString();
    private static final String VALID_KIN_EMAIL = ((Patient) BENSON).getNextOfKin().getEmail().toString();
    private static final String VALID_KIN_PHONE = ((Patient) BENSON).getNextOfKin().getPhone().toString();
    private static final String VALID_KIN_ADDRESS = ((Patient) BENSON).getNextOfKin().getAddress().toString();

    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedRecord> VALID_RECORDS = ((Patient) BENSON).getRecords().stream()
            .map(JsonAdaptedRecord::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                        VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, INVALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                        VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, null,
                VALID_EMAIL, VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, INVALID_EMAIL,
                        VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                        VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE,
                null, VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                        INVALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                    VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE,
                VALID_EMAIL, null, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, INVALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, null, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, INVALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_tomorrowDateOfBirth_throwsIllegalValueException() {
        DateBase temp = DateOfBirth.getToday();
        temp.setTo(temp.getDay(), temp.getMonth(), temp.getYear() + 1);
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, temp.toString(), VALID_SEX, VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                        VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS_FUTURE_DAY;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, null, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSex_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, INVALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = Sex.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSex_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDrugAllergy_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, INVALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = DrugAllergy.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDrugAllergy_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DrugAllergy.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDesc_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, INVALID_DESC);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDesc_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS, VALID_KIN, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidKinName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS,
                new JsonAdaptedNextOfKin(INVALID_NAME, VALID_KIN_RELATION,
                    VALID_KIN_EMAIL, VALID_KIN_PHONE, VALID_KIN_ADDRESS), VALID_DESC);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullKinName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS,
                new JsonAdaptedNextOfKin(null, VALID_KIN_RELATION,
                    VALID_KIN_EMAIL, VALID_KIN_PHONE, VALID_KIN_ADDRESS), VALID_DESC);
        String expectedMessage =
            String.format(JsonAdaptedNextOfKin.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidKinRelation_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS,
                new JsonAdaptedNextOfKin(VALID_KIN_NAME, INVALID_KIN_RELATION,
                    VALID_KIN_EMAIL, VALID_KIN_PHONE, VALID_KIN_ADDRESS), VALID_DESC);
        String expectedMessage = NextOfKinRelation.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullKinRelation_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS,
                new JsonAdaptedNextOfKin(VALID_KIN_NAME, null,
                    VALID_KIN_EMAIL, VALID_KIN_PHONE, VALID_KIN_ADDRESS), VALID_DESC);
        String expectedMessage =
            String.format(JsonAdaptedNextOfKin.MISSING_FIELD_MESSAGE_FORMAT, NextOfKinRelation.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidKinPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS,
                new JsonAdaptedNextOfKin(VALID_KIN_NAME, VALID_KIN_RELATION,
                    VALID_KIN_EMAIL, INVALID_PHONE, VALID_KIN_ADDRESS), VALID_DESC);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullKinPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS,
                new JsonAdaptedNextOfKin(VALID_KIN_NAME, VALID_KIN_RELATION,
                    VALID_KIN_EMAIL, null, VALID_KIN_ADDRESS), VALID_DESC);
        String expectedMessage =
            String.format(JsonAdaptedNextOfKin.MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidKinAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS,
                new JsonAdaptedNextOfKin(VALID_KIN_NAME, VALID_KIN_RELATION,
                    VALID_KIN_EMAIL, VALID_KIN_PHONE, INVALID_ADDRESS), VALID_DESC);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullKinAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_DOB, VALID_SEX, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DRUG_ALLERGY, VALID_TEETH,
                VALID_TAGS, VALID_RECORDS,
                new JsonAdaptedNextOfKin(VALID_KIN_NAME, VALID_KIN_RELATION,
                    VALID_KIN_EMAIL, VALID_KIN_PHONE, null), VALID_DESC);
        String expectedMessage =
            String.format(JsonAdaptedNextOfKin.MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
