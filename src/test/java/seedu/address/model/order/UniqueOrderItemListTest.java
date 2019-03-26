package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W12;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.menu.Code;
import seedu.address.model.order.exceptions.DuplicateOrderItemException;
import seedu.address.model.order.exceptions.OrderItemNotFoundException;
import seedu.address.model.table.TableNumber;
import seedu.address.testutil.OrderItemBuilder;

public class UniqueOrderItemListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueOrderItemList uniqueOrderItemList = new UniqueOrderItemList();

    @Test
    public void contains_nullOrderItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderItemList.contains(null);
    }

    @Test
    public void contains_orderItemNotInList_returnsFalse() {
        assertFalse(uniqueOrderItemList.contains(TABLE1_W09));
    }

    @Test
    public void contains_orderItemInList_returnsTrue() {
        uniqueOrderItemList.add(TABLE1_W09);
        assertTrue(uniqueOrderItemList.contains(TABLE1_W09));
    }

    @Test
    public void contains_orderItemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueOrderItemList.add(TABLE1_W09);
        OrderItem editedOrderItem = new OrderItemBuilder(TABLE1_W09).withQuantity(5)
                .build();
        assertTrue(uniqueOrderItemList.contains(editedOrderItem));
    }

    @Test
    public void add_nullOrderItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderItemList.add(null);
    }

    @Test
    public void add_duplicateOrderItem_throwsDuplicateOrderItemException() {
        uniqueOrderItemList.add(TABLE1_W09);
        thrown.expect(DuplicateOrderItemException.class);
        uniqueOrderItemList.add(TABLE1_W09);
    }

    @Test
    public void setOrderItem_nullTargetOrderItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderItemList.setOrderItem(null, TABLE1_W09);
    }

    @Test
    public void setOrderItem_nullEditedOrderItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderItemList.setOrderItem(TABLE1_W09, null);
    }

    @Test
    public void setOrderItem_targetOrderItemNotInList_throwsOrderItemNotFoundException() {
        thrown.expect(OrderItemNotFoundException.class);
        uniqueOrderItemList.setOrderItem(TABLE1_W09, TABLE1_W09);
    }

    @Test
    public void setOrderItem_editedOrderItemIsSameOrderItem_success() {
        uniqueOrderItemList.add(TABLE1_W09);
        uniqueOrderItemList.setOrderItem(TABLE1_W09, TABLE1_W09);
        UniqueOrderItemList expectedUniqueOrderItemList = new UniqueOrderItemList();
        expectedUniqueOrderItemList.add(TABLE1_W09);
        assertEquals(expectedUniqueOrderItemList, uniqueOrderItemList);
    }

    @Test
    public void setOrderItem_editedOrderItemHasSameIdentity_success() {
        uniqueOrderItemList.add(TABLE1_W09);
        OrderItem editedOrderItem = new OrderItemBuilder(TABLE1_W09).withQuantity(5)
                .build();
        uniqueOrderItemList.setOrderItem(TABLE1_W09, editedOrderItem);
        UniqueOrderItemList expectedUniqueOrderItemList = new UniqueOrderItemList();
        expectedUniqueOrderItemList.add(editedOrderItem);
        assertEquals(expectedUniqueOrderItemList, uniqueOrderItemList);
    }

    @Test
    public void setOrderItem_editedOrderItemHasDifferentIdentity_success() {
        uniqueOrderItemList.add(TABLE1_W09);
        uniqueOrderItemList.setOrderItem(TABLE1_W09, TABLE1_W12);
        UniqueOrderItemList expectedUniqueOrderItemList = new UniqueOrderItemList();
        expectedUniqueOrderItemList.add(TABLE1_W12);
        assertEquals(expectedUniqueOrderItemList, uniqueOrderItemList);
    }

    @Test
    public void setOrderItem_editedOrderItemHasNonUniqueIdentity_throwsDuplicateOrderItemException() {
        uniqueOrderItemList.add(TABLE1_W09);
        uniqueOrderItemList.add(TABLE1_W12);
        thrown.expect(DuplicateOrderItemException.class);
        uniqueOrderItemList.setOrderItem(TABLE1_W09, TABLE1_W12);
    }

    @Test
    public void remove_nullOrderItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderItemList.remove(null);
    }

    @Test
    public void remove_orderItemDoesNotExist_throwsOrderItemNotFoundException() {
        thrown.expect(OrderItemNotFoundException.class);
        uniqueOrderItemList.remove(TABLE1_W09);
    }

    @Test
    public void remove_existingOrderItem_removesOrderItem() {
        uniqueOrderItemList.add(TABLE1_W09);
        uniqueOrderItemList.remove(TABLE1_W09);
        UniqueOrderItemList expectedUniqueOrderItemList = new UniqueOrderItemList();
        assertEquals(expectedUniqueOrderItemList, uniqueOrderItemList);
    }

    @Test
    public void setOrderItems_nullUniqueOrderItemList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderItemList.setOrderItems((UniqueOrderItemList) null);
    }

    @Test
    public void setOrderItems_uniqueOrderItemList_replacesOwnListWithProvidedUniqueOrderItemList() {
        uniqueOrderItemList.add(TABLE1_W09);
        UniqueOrderItemList expectedUniqueOrderItemList = new UniqueOrderItemList();
        expectedUniqueOrderItemList.add(TABLE1_W12);
        uniqueOrderItemList.setOrderItems(expectedUniqueOrderItemList);
        assertEquals(expectedUniqueOrderItemList, uniqueOrderItemList);
    }

    @Test
    public void setOrderItems_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderItemList.setOrderItems((List<OrderItem>) null);
    }

    @Test
    public void setOrderItems_list_replacesOwnListWithProvidedList() {
        uniqueOrderItemList.add(TABLE1_W09);
        List<OrderItem> orderItemList = Collections.singletonList(TABLE1_W12);
        uniqueOrderItemList.setOrderItems(orderItemList);
        UniqueOrderItemList expectedUniqueOrderItemList = new UniqueOrderItemList();
        expectedUniqueOrderItemList.add(TABLE1_W12);
        assertEquals(expectedUniqueOrderItemList, uniqueOrderItemList);
    }

    @Test
    public void setOrderItems_listWithDuplicateOrderItems_throwsDuplicateOrderItemException() {
        List<OrderItem> listWithDuplicateOrderItems = Arrays.asList(TABLE1_W09, TABLE1_W09);
        thrown.expect(DuplicateOrderItemException.class);
        uniqueOrderItemList.setOrderItems(listWithDuplicateOrderItems);
    }

    @Test
    public void getOrderItem_itemInList_success() {
        uniqueOrderItemList.add(TABLE1_W09);
        assertEquals(uniqueOrderItemList.getOrderItem(new TableNumber("1"), new Code("W09")).get(), TABLE1_W09);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueOrderItemList.asUnmodifiableObservableList().remove(0);
    }
}
