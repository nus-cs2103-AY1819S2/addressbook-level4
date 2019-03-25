package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedOrderItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.Name;
import seedu.address.model.table.TableNumber;
import seedu.address.testutil.Assert;

public class JsonAdaptedOrderItemTest {
    private static final String INVALID_TABLE_NUMBER = "Abc";
    private static final String INVALID_CODE = "52";
    private static final String INVALID_NAME = "#$!";
    // TODO: ItemStatus

    private static final String VALID_TABLE_NUMBER = TABLE1_W09.getTableNumber().toString();
    private static final String VALID_CODE = TABLE1_W09.getMenuItemCode().toString();
    private static final String VALID_NAME = TABLE1_W09.getMenuItemName().toString();
    private static final String VALID_QUANTITY_ORDERED = "4";
    private static final String VALID_QUANTITY_UNSERVED = "4";

    @Test
    public void toModelType_validOrderItemDetails_returnsOrderItem() throws Exception {
        JsonAdaptedOrderItem orderItem = new JsonAdaptedOrderItem(TABLE1_W09);
        assertEquals(TABLE1_W09, orderItem.toModelType());
    }

    @Test
    public void toModelType_invalidTableNumber_throwsIllegalValueException() {
        JsonAdaptedOrderItem orderItem =
                new JsonAdaptedOrderItem(INVALID_TABLE_NUMBER, VALID_CODE, VALID_NAME, VALID_QUANTITY_ORDERED,
                        VALID_QUANTITY_UNSERVED);
        String expectedMessage = TableNumber.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    }

    @Test
    public void toModelType_nullTableNumber_throwsIllegalValueException() {
        JsonAdaptedOrderItem orderItem = new JsonAdaptedOrderItem(null, VALID_CODE, VALID_NAME, VALID_QUANTITY_ORDERED,
                VALID_QUANTITY_UNSERVED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TableNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    }

    @Test
    public void toModelType_invalidCode_throwsIllegalValueException() {
        JsonAdaptedOrderItem orderItem =
                new JsonAdaptedOrderItem(VALID_TABLE_NUMBER, INVALID_CODE, VALID_NAME, VALID_QUANTITY_ORDERED,
                        VALID_QUANTITY_UNSERVED);
        String expectedMessage = Code.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    }

    @Test
    public void toModelType_nullCode_throwsIllegalValueException() {
        JsonAdaptedOrderItem orderItem = new JsonAdaptedOrderItem(VALID_TABLE_NUMBER, null, VALID_NAME,
                VALID_QUANTITY_ORDERED, VALID_QUANTITY_UNSERVED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Code.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedOrderItem orderItem =
                new JsonAdaptedOrderItem(VALID_TABLE_NUMBER, VALID_CODE, INVALID_NAME, VALID_QUANTITY_ORDERED,
                        VALID_QUANTITY_UNSERVED);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedOrderItem orderItem = new JsonAdaptedOrderItem(VALID_TABLE_NUMBER, VALID_CODE, null,
                VALID_QUANTITY_ORDERED, VALID_QUANTITY_UNSERVED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    }

}
