package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_OF_MED_HIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_MED_HIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDHIST_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WRITE_UP;
import static seedu.address.testutil.TypicalMedHists.MED_HIST1;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.PersonId;
import seedu.address.testutil.Assert;

public class JsonAdaptedMedHistTest {

    @Test
    public void toModelType_validMedHistDetails_returnsMedHist() throws Exception {
        JsonAdaptedMedicalHistory medHist = new JsonAdaptedMedicalHistory(MED_HIST1);
        assertEquals(MED_HIST1, medHist.toModelType());
    }

    @Test
    public void toModelType_invalidPatientId_throwsIllegalValueException() {
        JsonAdaptedMedicalHistory medHist = new JsonAdaptedMedicalHistory(VALID_MEDHIST_ID,
                INVALID_PATIENT_ID, VALID_DOCTOR_ID, VALID_DATE_OF_MED_HIST, VALID_WRITE_UP);
        String expectedMessage = PersonId.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medHist::toModelType);
    }

    @Test
    public void toModelType_nullPatientId_throwsIllegalValueException() {
        JsonAdaptedMedicalHistory medHist = new JsonAdaptedMedicalHistory(VALID_MEDHIST_ID,
                null, VALID_DOCTOR_ID, VALID_DATE_OF_MED_HIST, VALID_WRITE_UP);
        String expectedMessage = String.format(
                JsonAdaptedMedicalHistory.MISSING_FIELD_MESSAGE_FORMAT, PersonId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medHist::toModelType);
    }

    @Test
    public void toModelType_invalidDoctorId_throwsIllegalValueException() {
        JsonAdaptedMedicalHistory medHist = new JsonAdaptedMedicalHistory(VALID_MEDHIST_ID,
                VALID_PATIENT_ID, INVALID_DOCTOR_ID, VALID_DATE_OF_MED_HIST, VALID_WRITE_UP);
        String expectedMessage = PersonId.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medHist::toModelType);
    }

    @Test
    public void toModelType_nullDoctorId_throwsIllegalValueException() {
        JsonAdaptedMedicalHistory medHist = new JsonAdaptedMedicalHistory(VALID_MEDHIST_ID,
                VALID_PATIENT_ID, null, VALID_DATE_OF_MED_HIST, VALID_WRITE_UP);
        String expectedMessage = String.format(
                JsonAdaptedMedicalHistory.MISSING_FIELD_MESSAGE_FORMAT, PersonId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medHist::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedMedicalHistory medHist = new JsonAdaptedMedicalHistory(VALID_MEDHIST_ID,
                VALID_PATIENT_ID, VALID_DOCTOR_ID, INVALID_DATE_OF_MED_HIST, VALID_WRITE_UP);
        String expectedMessage = ValidDate.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medHist::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedMedicalHistory medHist = new JsonAdaptedMedicalHistory(VALID_MEDHIST_ID,
                VALID_PATIENT_ID, VALID_DOCTOR_ID, null, VALID_WRITE_UP);
        String expectedMessage = String.format(JsonAdaptedMedicalHistory.MISSING_FIELD_MESSAGE_FORMAT,
                ValidDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medHist::toModelType);
    }

    @Test
    public void toModelType_nullWriteUp_throwsIllegalValueException() {
        JsonAdaptedMedicalHistory medHist = new JsonAdaptedMedicalHistory(VALID_MEDHIST_ID,
                VALID_PATIENT_ID, VALID_DOCTOR_ID, VALID_DATE_OF_MED_HIST, null);
        String expectedMessage = String.format(
                JsonAdaptedMedicalHistory.MISSING_FIELD_MESSAGE_FORMAT, WriteUp.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medHist::toModelType);
    }
}
