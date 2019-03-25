package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalOrderItems;

import org.junit.Test;

import guitests.guihandles.OrderItemListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.Name;
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.TableNumber;

public class OrderItemListPanelTest extends GuiUnitTest {
    private static final ObservableList<OrderItem> TYPICAL_ORDER_ITEMS =
            FXCollections.observableList(getTypicalOrderItems());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<OrderItem> selectedOrderItem = new SimpleObjectProperty<>();
    private OrderItemListPanelHandle orderItemListPanelHandle;

    //    @Test TODO
    //    public void display() {
    //        initUi(TYPICAL_ORDER_ITEMS);
    //
    //        for (int i = 0; i < TYPICAL_ORDER_ITEMS.size(); i++) {
    //            orderItemListPanelHandle.navigateToCard(TYPICAL_ORDER_ITEMS.get(i));
    //            OrderItem expectedOrderItem = TYPICAL_ORDER_ITEMS.get(i);
    //            OrderItemCardHandle actualCard = orderItemListPanelHandle.getOrderItemCardHandle(i);
    //
    //            assertCardDisplaysOrderItem(expectedOrderItem, actualCard);
    //            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
    //        }
    //    }

    //    @Test TODO
    //    public void selection_modelSelectedOrderItemChanged_selectionChanges() {
    //        initUi(TYPICAL_ORDER_ITEMS);
    //        OrderItem secondOrderItem = TYPICAL_ORDER_ITEMS.get(INDEX_SECOND_ITEM.getZeroBased());
    //        guiRobot.interact(() -> selectedOrderItem.set(secondOrderItem));
    //        guiRobot.pauseForHuman();
    //
    //        OrderItemCardHandle expectedOrderItem =
    //                orderItemListPanelHandle.getOrderItemCardHandle(INDEX_SECOND_ITEM.getZeroBased());
    //        OrderItemCardHandle selectedOrderItem = orderItemListPanelHandle.getHandleToSelectedCard();
    //        assertCardEquals(expectedOrderItem, selectedOrderItem);
    //    }

    /**
     * Verifies that creating and deleting large number of orderItems in {@code OrderItemListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<OrderItem> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of orderItem cards exceeded time limit");
    }

    /**
     * Returns a list of orderItems containing {@code orderItemCount} orderItems that is used to populate the
     * {@code OrderItemListPanel}.
     */
    private ObservableList<OrderItem> createBackingList(int orderItemCount) {
        ObservableList<OrderItem> backingList = FXCollections.observableArrayList();
        for (int i = 1; i <= orderItemCount; i++) {
            TableNumber tableNumber = new TableNumber(i + "");
            Code menuItemCode = new Code("W09");
            Name menuItemName = new Name("Chicken Wings");
            int quantityOrdered = 3;
            OrderItem orderItem = new OrderItem(tableNumber, menuItemCode, menuItemName, quantityOrdered);
            backingList.add(orderItem);
        }
        return backingList;
    }

    /**
     * Initializes {@code orderItemListPanelHandle} with a {@code OrderItemListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code OrderItemListPanel}.
     */
    private void initUi(ObservableList<OrderItem> backingList) {
        OrderItemListPanel orderItemListPanel =
                new OrderItemListPanel(backingList, selectedOrderItem, selectedOrderItem::set);
        uiPartRule.setUiPart(orderItemListPanel);

        orderItemListPanelHandle = new OrderItemListPanelHandle(getChildNode(orderItemListPanel.getRoot(),
                OrderItemListPanelHandle.ORDER_ITEM_LIST_VIEW_ID));
    }
}
