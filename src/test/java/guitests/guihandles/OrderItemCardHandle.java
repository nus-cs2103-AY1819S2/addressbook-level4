package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.order.OrderItem;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class OrderItemCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TABLE_NUMBER_FIELD_ID = "#tableNumber";
    private static final String CODE_FIELD_ID = "#menuItemCode";
    private static final String QUANTITY_FIELD_ID = "#quantity";

    private final Label idLabel;
    private final Label tableNumberLabel;
    private final Label menuItemCodeLabel;
    private final Label quantityLabel;

    public OrderItemCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        tableNumberLabel = getChildNode(TABLE_NUMBER_FIELD_ID);
        menuItemCodeLabel = getChildNode(CODE_FIELD_ID);
        quantityLabel = getChildNode(QUANTITY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTableNumber() {
        return tableNumberLabel.getText();
    }

    public String getMenuItemCode() {
        return menuItemCodeLabel.getText();
    }

    public String getQuantity() {
        return quantityLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code orderItem}.
     */
    public boolean equals(OrderItem orderItem) {
        return getTableNumber().equals(orderItem.getTableNumber().tableNumber) && getMenuItemCode().equals(
                orderItem.getMenuItemCode().itemCode) && getQuantity().equals(
                Integer.toString(orderItem.getQuantity()));
    }
}
