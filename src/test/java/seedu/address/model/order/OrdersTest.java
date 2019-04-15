package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalOrderItems;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.menu.Code;
import seedu.address.model.order.exceptions.DuplicateOrderItemException;
import seedu.address.model.table.TableNumber;
import seedu.address.testutil.OrderItemBuilder;

public class OrdersTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Orders orders = new Orders();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), orders.getOrderItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        orders.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyOrders_replacesData() {
        Orders newData = new Orders();
        for (OrderItem orderItem : getTypicalOrderItems()) {
            newData.addOrderItem(orderItem);
        }
        orders.resetData(newData);
        assertEquals(newData, orders);
    }

    @Test
    public void resetData_withDuplicateOrderItems_throwsDuplicateOrderItemException() {
        // Two order items with the same identity fields
        OrderItem editedOrderItem = new OrderItemBuilder(TABLE1_W09).withQuantity(5).build();
        List<OrderItem> newOrderItems = Arrays.asList(TABLE1_W09, editedOrderItem);
        OrdersStub newData = new OrdersStub(newOrderItems);

        thrown.expect(DuplicateOrderItemException.class);
        orders.resetData(newData);
    }

    @Test
    public void hasOrderItem_nullOrderItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        orders.hasOrderItem(null);
    }

    @Test
    public void hasOrderItem_orderItemNotInOrders_returnsFalse() {
        assertFalse(orders.hasOrderItem(TABLE1_W09));
    }

    @Test
    public void hasOrderItem_orderItemInOrders_returnsTrue() {
        orders.addOrderItem(TABLE1_W09);
        assertTrue(orders.hasOrderItem(TABLE1_W09));
    }

    @Test
    public void hasOrderItem_orderItemWithSameIdentityFieldsInOrders_returnsTrue() {
        orders.addOrderItem(TABLE1_W09);
        OrderItem editedOrderItem = new OrderItemBuilder(TABLE1_W09).withQuantity(5).build();
        assertTrue(orders.hasOrderItem(editedOrderItem));
    }

    @Test
    public void getOrderItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        orders.getOrderItemList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        orders.addListener(listener);
        orders.addOrderItem(TABLE1_W09);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        orders.addListener(listener);
        orders.removeListener(listener);
        orders.addOrderItem(TABLE1_W09);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyOrders whose order item list can violate interface constraints.
     */
    private static class OrdersStub implements ReadOnlyOrders {
        private final ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();

        OrdersStub(Collection<OrderItem> orderItems) {
            this.orderItems.setAll(orderItems);
        }

        @Override
        public ObservableList<OrderItem> getOrderItemList() {
            return orderItems;
        }

        @Override
        public Optional<OrderItem> getOrderItem(TableNumber tableNumber, Code itemCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
