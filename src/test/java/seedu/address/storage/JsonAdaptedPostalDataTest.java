package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPostalData.A;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Postal;
import seedu.address.testutil.Assert;

public class JsonAdaptedPostalDataTest {
    private static final String VALID_POSTAL_CODE = "267951";
    private static final double VALID_X_COORDINATE = 1.213123124;
    private static final double VALID_Y_COORDINATE = 1.221231233;

    private static final String INVALID_POSTAL_CODE = "2672951";

    @Test
    public void toModelType_validPostalData_returnsPostalData() throws Exception {
        JsonAdaptedPostalData postalData = new JsonAdaptedPostalData(A.getPostal(), A.getX(), A.getY());
        assertEquals(A, postalData.toModelType());
    }

    @Test
    public void toModelType_invalidPostalCode_throwsIllegalValueException() {
        JsonAdaptedPostalData postalData = new JsonAdaptedPostalData(INVALID_POSTAL_CODE,
                VALID_X_COORDINATE, VALID_X_COORDINATE);
        String expectedMessage = Postal.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, postalData::toModelType);
    }

}
