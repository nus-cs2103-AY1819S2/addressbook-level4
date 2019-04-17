package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedApparel.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalApparels.PANTS1;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;
import seedu.address.testutil.Assert;

public class JsonAdaptedApparelTest {
    private static final String INVALID_NAME = "Formal Belt!!!";

    private static final String VALID_NAME = PANTS1.getName().toString();
    private static final Color VALID_COLOR = PANTS1.getColor();
    private static final ClothingType VALID_CLOTHING_TYPE = PANTS1.getClothingType();


    @Test
    public void toModelType_validApparelDetails_returnsApparel() throws Exception {
        JsonAdaptedApparel apparel = new JsonAdaptedApparel(PANTS1);
        assertEquals(PANTS1, apparel.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedApparel person =
                new JsonAdaptedApparel(INVALID_NAME, VALID_COLOR, VALID_CLOTHING_TYPE, true, 1);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedApparel person = new JsonAdaptedApparel(null, VALID_COLOR, VALID_CLOTHING_TYPE, true, 1);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_nullColor_throwsIllegalValueException() {
        JsonAdaptedApparel person = new JsonAdaptedApparel(VALID_NAME, null, VALID_CLOTHING_TYPE, true, 1);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Color.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_nullClothingType_throwsIllegalValueException() {
        JsonAdaptedApparel person = new JsonAdaptedApparel(VALID_NAME, VALID_COLOR, null, true, 1);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClothingType.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


}
