package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.order.OrderItem;

/**
 * Provides a handle for {@code OrderItemListPanel} containing the list of {@code OrderItemCard}.
 */
public class OrderItemListPanelHandle extends NodeHandle<ListView<OrderItem>> {
    public static final String ORDER_ITEM_LIST_VIEW_ID = "#orderItemListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<OrderItem> lastRememberedSelectedOrderItemCard;

    public OrderItemListPanelHandle(ListView<OrderItem> orderItemListPanelNode) {
        super(orderItemListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code OrderItemCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public OrderItemCardHandle getHandleToSelectedCard() {
        List<OrderItem> selectedOrderItemList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedOrderItemList.size() != 1) {
            throw new AssertionError("OrderItem list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(OrderItemCardHandle::new)
                .filter(handle -> handle.equals(selectedOrderItemList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<OrderItem> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code orderItem}.
     */
    public void navigateToCard(OrderItem orderItem) {
        if (!getRootNode().getItems().contains(orderItem)) {
            throw new IllegalArgumentException("OrderItem does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(orderItem);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code OrderItemCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the orderItem card handle of a orderItem associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public OrderItemCardHandle getOrderItemCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(OrderItemCardHandle::new)
                .filter(handle -> handle.equals(getOrderItem(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private OrderItem getOrderItem(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code OrderItemCard} in the list.
     */
    public void rememberSelectedOrderItemCard() {
        List<OrderItem> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedOrderItemCard = Optional.empty();
        } else {
            lastRememberedSelectedOrderItemCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code OrderItemCard} is different from the value remembered by the most recent
     * {@code rememberSelectedOrderItemCard()} call.
     */
    public boolean isSelectedOrderItemCardChanged() {
        List<OrderItem> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedOrderItemCard.isPresent();
        } else {
            return !lastRememberedSelectedOrderItemCard.isPresent()
                    || !lastRememberedSelectedOrderItemCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
