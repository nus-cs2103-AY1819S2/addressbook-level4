package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;

import org.junit.Test;

public class JsonAdaptedOrderItemTest {
    private static final String INVALID_TABLE_NUMBER = "Abc";
    private static final String INVALID_CODE = "52";
    // TODO: ItemStatus

    private static final String VALID_TABLE_NUMBER = TABLE1_W09.getTableNumber().toString();
    private static final String VALID_CODE = TABLE1_W09.getMenuItemCode().toString();
    private static final String VALID_QUANTITY_ORDERED = "4";
    private static final String VALID_QUANTITY_UNSERVED = "4";

    @Test
    public void toModelType_validOrderItemDetails_returnsOrderItem() throws Exception {
        JsonAdaptedOrderItem orderItem = new JsonAdaptedOrderItem(TABLE1_W09);
        assertEquals(TABLE1_W09, orderItem.toModelType());
    }

    //    @Test TODO
    //    public void toModelType_invalidTableNumber_throwsIllegalValueException() {
    //        JsonAdaptedOrderItem orderItem =
    //                new JsonAdaptedOrderItem(INVALID_TABLE_NUMBER, VALID_CODE, VALID_QUANTITY_ORDERED,
    //                        VALID_QUANTITY_UNSERVED);
    //        String expectedMessage = TableNumber.MESSAGE_CONSTRAINTS;
    //        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    //    }

    //    @Test TODO
    //    public void toModelType_nullTableNumber_throwsIllegalValueException() {
    //        JsonAdaptedOrderItem orderItem = new JsonAdaptedOrderItem(null, VALID_CODE, VALID_QUANTITY_ORDERED,
    //                VALID_QUANTITY_UNSERVED);
    //        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TableNumber.class.getSimpleName());
    //        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    //    }

    //    @Test TODO
    //    public void toModelType_invalidCode_throwsIllegalValueException() {
    //        JsonAdaptedOrderItem orderItem =
    //                new JsonAdaptedOrderItem(VALID_TABLE_NUMBER, INVALID_CODE, VALID_QUANTITY_ORDERED,
    //                        VALID_QUANTITY_UNSERVED);
    //        String expectedMessage = Code.MESSAGE_CONSTRAINTS;
    //        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    //    }

    //    @Test TODO
    //    public void toModelType_nullCode_throwsIllegalValueException() {
    //        JsonAdaptedOrderItem orderItem = new JsonAdaptedOrderItem(VALID_TABLE_NUMBER, null,
    //                VALID_QUANTITY_ORDERED, VALID_QUANTITY_UNSERVED);
    //        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Code.class.getSimpleName());
    //        Assert.assertThrows(IllegalValueException.class, expectedMessage, orderItem::toModelType);
    //    }

}
