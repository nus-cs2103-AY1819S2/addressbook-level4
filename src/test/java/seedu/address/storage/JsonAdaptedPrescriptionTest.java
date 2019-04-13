package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPrescription.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.prescription.Description;
import seedu.address.model.prescription.Medicine;
import seedu.address.testutil.Assert;

public class JsonAdaptedPrescriptionTest {
    private static final String INVALID_DATE = "2030-02-15";

    private static final int VALID_PATIENT_ID = 1;
    private static final int VALID_DOCTOR_ID = 2;
    private static final String VALID_DATE = "2018-05-13";
    private static final String VALID_MEDICINE_NAME = "Aspirin";
    private static final String VALID_DESCRIPTION = "For curing fever";

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(VALID_PATIENT_ID,
                VALID_DOCTOR_ID, INVALID_DATE, VALID_MEDICINE_NAME, VALID_DESCRIPTION);
        String expectedMessage = ValidDate.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, prescription::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(VALID_PATIENT_ID,
                VALID_DOCTOR_ID, null, VALID_MEDICINE_NAME, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ValidDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, prescription::toModelType);
    }

    @Test
    public void toModelType_nullMedicineName_throwsIllegalValueException() {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(VALID_PATIENT_ID,
                VALID_DOCTOR_ID, VALID_DATE, null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Medicine.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, prescription::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(VALID_PATIENT_ID,
                VALID_DOCTOR_ID, VALID_DATE, VALID_MEDICINE_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, prescription::toModelType);
    }

}


