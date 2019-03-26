package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W12;

import org.junit.Test;

import seedu.address.testutil.OrderItemBuilder;

public class OrderItemTest {
    @Test
    public void isSameOrderItem() {
        // same object -> returns true
        assertTrue(TABLE1_W09.isSameOrderItem(TABLE1_W09));

        // null -> returns false
        assertFalse(TABLE1_W09.isSameOrderItem(null));

        // different item code -> returns false
        OrderItem editedOrderItem = new OrderItemBuilder(TABLE1_W09).withCode("A05").build();
        assertFalse(TABLE1_W09.isSameOrderItem(editedOrderItem));

        // different item name -> returns false
        editedOrderItem = new OrderItemBuilder(TABLE1_W09).withName("Chicken Wings 3pcs").build();
        assertFalse(TABLE1_W09.isSameOrderItem(editedOrderItem));

        // different table number -> returns false
        editedOrderItem = new OrderItemBuilder(TABLE1_W09).withTableNumber("2").build();
        assertFalse(TABLE1_W09.isSameOrderItem(editedOrderItem));

        // same table number, item code and name, different quantity -> returns true
        editedOrderItem = new OrderItemBuilder(TABLE1_W09).withQuantity(5).build();
        assertTrue(TABLE1_W09.isSameOrderItem(editedOrderItem));
    }

    @Test
    public void equals() {
        // same values -> returns true
        OrderItem orderItemCopy = new OrderItemBuilder(TABLE1_W09).build();
        assertTrue(TABLE1_W09.equals(orderItemCopy));

        // same object -> returns true
        assertTrue(TABLE1_W09.equals(TABLE1_W09));

        // null -> returns false
        assertFalse(TABLE1_W09.equals(null));

        // different type -> returns false
        assertFalse(TABLE1_W09.equals(5));

        // different order -> returns false
        assertFalse(TABLE1_W09.equals(TABLE1_W12));

        // different table number -> returns false
        OrderItem editedOrderItem = new OrderItemBuilder(TABLE1_W09).withTableNumber("2").build();
        assertFalse(TABLE1_W09.equals(editedOrderItem));

        // different item code -> returns false
        editedOrderItem = new OrderItemBuilder(TABLE1_W09).withCode("A05").build();
        assertFalse(TABLE1_W09.equals(editedOrderItem));

        // different item name -> returns false
        editedOrderItem = new OrderItemBuilder(TABLE1_W09).withName("Chicken Wings 3pcs").build();
        assertFalse(TABLE1_W09.equals(editedOrderItem));

        // different quantity -> returns false
        editedOrderItem = new OrderItemBuilder(TABLE1_W09).withQuantity(5).build();
        assertFalse(TABLE1_W09.equals(editedOrderItem));
    }
}
