package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedMedicine.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalMedicines.IBUPROFEN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Quantity;
import seedu.address.testutil.Assert;

public class JsonAdaptedMedicineTest {
    private static final String INVALID_NAME = "I@bupr!fen";
    private static final String INVALID_COMPANY = " ";
    private static final String INVALID_QUANTITY = "+651234";
    private static final String INVALID_EXPIRY = "a/1/09";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_BATCHNUMBER = "!#4@$532532";

    private static final String VALID_NAME = IBUPROFEN.getName().toString();
    private static final String VALID_COMPANY = IBUPROFEN.getCompany().toString();
    private static final String VALID_QUANTITY = IBUPROFEN.getTotalQuantity().toString();
    private static final String VALID_EXPIRY = IBUPROFEN.getNextExpiry().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = IBUPROFEN.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedBatch> VALID_BATCHES = IBUPROFEN.getBatches().values().stream()
            .map(JsonAdaptedBatch::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validMedicineDetails_returnsMedicine() throws Exception {
        JsonAdaptedMedicine medicine = new JsonAdaptedMedicine(IBUPROFEN);
        assertEquals(IBUPROFEN, medicine.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedMedicine medicine =
                new JsonAdaptedMedicine(INVALID_NAME, VALID_COMPANY, VALID_QUANTITY, VALID_EXPIRY, VALID_TAGS,
                        VALID_BATCHES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedMedicine medicine = new JsonAdaptedMedicine(null, VALID_COMPANY, VALID_QUANTITY, VALID_EXPIRY,
                VALID_TAGS, VALID_BATCHES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedMedicine medicine =
                new JsonAdaptedMedicine(VALID_NAME, INVALID_COMPANY, VALID_QUANTITY, VALID_EXPIRY, VALID_TAGS,
                        VALID_BATCHES);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedMedicine medicine =
                new JsonAdaptedMedicine(VALID_NAME, null, VALID_QUANTITY, VALID_EXPIRY, VALID_TAGS, VALID_BATCHES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedMedicine medicine =
                new JsonAdaptedMedicine(VALID_NAME, VALID_COMPANY, INVALID_QUANTITY, VALID_EXPIRY, VALID_TAGS,
                        VALID_BATCHES);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedMedicine medicine =
                new JsonAdaptedMedicine(VALID_NAME, VALID_COMPANY, null, VALID_EXPIRY, VALID_TAGS,
                        VALID_BATCHES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidExpiry_throwsIllegalValueException() {
        JsonAdaptedMedicine medicine =
                new JsonAdaptedMedicine(VALID_NAME, VALID_COMPANY, VALID_QUANTITY, INVALID_EXPIRY, VALID_TAGS,
                        VALID_BATCHES);
        String expectedMessage = Expiry.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullExpiry_throwsIllegalValueException() {
        JsonAdaptedMedicine medicine =
                new JsonAdaptedMedicine(VALID_NAME, VALID_COMPANY, VALID_QUANTITY, null, VALID_TAGS, VALID_BATCHES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Expiry.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedMedicine medicine =
                new JsonAdaptedMedicine(VALID_NAME, VALID_COMPANY, VALID_QUANTITY, VALID_EXPIRY, invalidTags,
                        VALID_BATCHES);
        Assert.assertThrows(IllegalValueException.class, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidBatches_throwsIllegalValueException() {
        List<JsonAdaptedBatch> invalidBatches = new ArrayList<>(VALID_BATCHES);
        invalidBatches.add(new JsonAdaptedBatch(INVALID_BATCHNUMBER, VALID_QUANTITY, VALID_EXPIRY));
        JsonAdaptedMedicine medicine =
                new JsonAdaptedMedicine(VALID_NAME, VALID_COMPANY, VALID_QUANTITY, VALID_EXPIRY, VALID_TAGS,
                        invalidBatches);
        Assert.assertThrows(IllegalValueException.class, medicine::toModelType);
    }

}
