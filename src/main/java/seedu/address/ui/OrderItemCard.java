package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.OrderItem;

/**
 * An UI component that displays information of a {@code OrderItem}.
 */
public class OrderItemCard extends UiPart<Region> {

    private static final String FXML = "OrderItemListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RestOrRant level 4</a>
     */

    public final OrderItem item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label tableNumber;
    @FXML
    private Label menuItem;
    @FXML
    private Label quantity;

    public OrderItemCard(OrderItem item, int displayedIndex) {
        super(FXML);
        this.item = item;
        id.setText(displayedIndex + ". ");

        tableNumber.setText("Table " + item.getTableNumber().tableNumber);
        menuItem.setText(item.getMenuItemCode().itemCode + " " + item.getMenuItemName());
        quantity.setText(
                "Quantity: [" + item.getQuantityToServe() + "/" + item.getQuantityOrdered() + " to serve]");

        if (item.getOrderItemStatus().isAllServed()) {
            cardPane.setStyle("-fx-background-color: #1d1d1d;");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderItemCard)) {
            return false;
        }

        // state check
        OrderItemCard card = (OrderItemCard) other;
        return id.getText().equals(card.id.getText()) && item.equals(card.item);
    }
}
