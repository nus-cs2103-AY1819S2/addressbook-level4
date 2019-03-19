package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.OrdersGuiTestAssert.assertCardDisplaysOrderItem;

import org.junit.Test;

import guitests.guihandles.OrderItemCardHandle;
import seedu.address.model.order.OrderItem;
import seedu.address.testutil.OrderItemBuilder;

public class OrderItemCardTest extends GuiUnitTest {

    //    @Test TODO
    //    public void display() {
    //        OrderItem orderItem = new OrderItemBuilder().build();
    //        OrderItemCard orderItemCard = new OrderItemCard(orderItem, 2);
    //        uiPartRule.setUiPart(orderItemCard);
    //        assertCardDisplay(orderItemCard, orderItem, 2);
    //    }

    @Test
    public void equals() {
        OrderItem orderItem = new OrderItemBuilder().build();
        OrderItemCard orderItemCard = new OrderItemCard(orderItem, 0);

        // same orderItem, same index -> returns true
        OrderItemCard copy = new OrderItemCard(orderItem, 0);
        assertTrue(orderItemCard.equals(copy));

        // same object -> returns true
        assertTrue(orderItemCard.equals(orderItemCard));

        // null -> returns false
        assertFalse(orderItemCard.equals(null));

        // different types -> returns false
        assertFalse(orderItemCard.equals(0));

        // different orderItem, same index -> returns false
        OrderItem differentOrderItem = new OrderItemBuilder().withTableNumber("2").build();
        assertFalse(orderItemCard.equals(new OrderItemCard(differentOrderItem, 0)));

        // same orderItem, different index -> returns false
        assertFalse(orderItemCard.equals(new OrderItemCard(orderItem, 1)));
    }

    /**
     * Asserts that {@code orderItemCard} displays the details of {@code expectedOrderItem} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(OrderItemCard orderItemCard, OrderItem expectedOrderItem, int expectedId) {
        guiRobot.pauseForHuman();

        OrderItemCardHandle orderItemCardHandle = new OrderItemCardHandle(orderItemCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", orderItemCardHandle.getId());

        // verify orderItem details are displayed correctly
        assertCardDisplaysOrderItem(expectedOrderItem, orderItemCardHandle);
    }
}
