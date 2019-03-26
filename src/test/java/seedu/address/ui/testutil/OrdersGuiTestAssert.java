package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.OrderItemCardHandle;
import guitests.guihandles.OrderItemListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TableCardHandle;
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.Table;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class OrdersGuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(OrderItemCardHandle expectedCard, OrderItemCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getTableNumber(), actualCard.getTableNumber());
        assertEquals(expectedCard.getMenuItem(), actualCard.getMenuItem());
        assertEquals(expectedCard.getQuantity(), actualCard.getQuantity());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedOrderItem}.
     */
    public static void assertCardDisplaysOrderItem(OrderItem expectedOrderItem, OrderItemCardHandle actualCard) {
        assertEquals("Table " + expectedOrderItem.getTableNumber().tableNumber, actualCard.getTableNumber());
        assertEquals(expectedOrderItem.getMenuItemCode().itemCode + " " + expectedOrderItem.getMenuItemName().itemName,
                actualCard.getMenuItem());
        assertEquals("Qty: " + expectedOrderItem.getQuantity(), actualCard.getQuantity());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedOrderItem}.
     */
    public static void assertCardDisplaysTable(Table expectedTable, TableCardHandle actualCard) {
        assertEquals(expectedTable.getTableNumber().toString(), actualCard.getTableNumber());
        assertEquals(expectedTable.getTableStatus().toString(), actualCard.getTableStatus());
    }

    /**
     * Asserts that the list in {@code orderItemListPanelHandle} displays the details of {@code orderItems} correctly
     * and
     * in the correct order.
     */
    public static void assertListMatching(OrderItemListPanelHandle orderItemListPanelHandle, OrderItem... orderItems) {
        for (int i = 0; i < orderItems.length; i++) {
            orderItemListPanelHandle.navigateToCard(i);
            assertCardDisplaysOrderItem(orderItems[i], orderItemListPanelHandle.getOrderItemCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code orderItemListPanelHandle} displays the details of {@code orderItems} correctly
     * and
     * in the correct order.
     */
    public static void assertListMatching(OrderItemListPanelHandle orderItemListPanelHandle,
                                          List<OrderItem> orderItems) {
        assertListMatching(orderItemListPanelHandle, orderItems.toArray(new OrderItem[0]));
    }

    /**
     * Asserts the size of the list in {@code orderItemListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(OrderItemListPanelHandle orderItemListPanelHandle, int size) {
        int numberOfPeople = orderItemListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
