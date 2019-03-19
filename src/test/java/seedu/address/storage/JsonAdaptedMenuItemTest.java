package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedMenu.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalRestOrRant.FRENCH_FRIES;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.testutil.Assert;

public class JsonAdaptedMenuItemTest {
    private static final String INVALID_NAME = "@g1i0 01!0";
    private static final String INVALID_CODE = "invalid1234";
    private static final String INVALID_PRICE = "12.345";

    private static final String VALID_NAME = FRENCH_FRIES.getName().toString();
    private static final String VALID_CODE = FRENCH_FRIES.getCode().toString();
    private static final String VALID_PRICE = FRENCH_FRIES.getPrice().toString();

    @Test
    public void toModelType_validMenuItemDetails_returnsMenuItem() throws Exception {
        JsonAdaptedMenu menuItem = new JsonAdaptedMenu(FRENCH_FRIES);
        assertEquals(FRENCH_FRIES, menuItem.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedMenu menuItem = new JsonAdaptedMenu(INVALID_NAME, VALID_CODE, VALID_PRICE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, menuItem::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedMenu menuItem = new JsonAdaptedMenu(null, VALID_CODE, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, menuItem::toModelType);
    }

    @Test
    public void toModelType_invalidCode_throwsIllegalValueException() {
        JsonAdaptedMenu menuItem = new JsonAdaptedMenu(VALID_NAME, INVALID_CODE, VALID_PRICE);
        String expectedMessage = Code.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, menuItem::toModelType);
    }

    @Test
    public void toModelType_nullCode_throwsIllegalValueException() {
        JsonAdaptedMenu menuItem = new JsonAdaptedMenu(VALID_NAME, null, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Code.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, menuItem::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedMenu menuItem = new JsonAdaptedMenu(VALID_NAME, VALID_CODE, INVALID_PRICE);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, menuItem::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedMenu menuItem = new JsonAdaptedMenu(VALID_NAME, VALID_CODE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, menuItem::toModelType);
    }

}
