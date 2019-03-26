package seedu.address.testutil;

import seedu.address.model.menu.Code;
import seedu.address.model.menu.Name;
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.TableNumber;

/**
 * A utility class to help with building OrderItem objects.
 */
public class OrderItemBuilder {

    public static final String DEFAULT_TABLE_NUMBER = "1";
    public static final String DEFAULT_MENU_ITEM_CODE = "W09";
    public static final String DEFAULT_MENU_ITEM_NAME = "Chicken Wings";
    public static final int DEFAULT_QUANTITY = 3;

    private TableNumber tableNumber;
    private Code menuItemCode;
    private Name menuItemName;
    private int quantityOrdered;

    public OrderItemBuilder() {
        tableNumber = new TableNumber(DEFAULT_TABLE_NUMBER);
        menuItemCode = new Code(DEFAULT_MENU_ITEM_CODE);
        menuItemName = new Name(DEFAULT_MENU_ITEM_NAME);
        quantityOrdered = DEFAULT_QUANTITY;
    }

    /**
     * Initializes the OrderItemBuilder with the data of {@code toCopy}.
     */
    public OrderItemBuilder(OrderItem toCopy) {
        tableNumber = toCopy.getTableNumber();
        menuItemCode = toCopy.getMenuItemCode();
        menuItemName = toCopy.getMenuItemName();
        quantityOrdered = toCopy.getQuantity();
    }

    /**
     * Sets the {@code TableNumber} of the {@code OrderItem} that we are building.
     */
    public OrderItemBuilder withTableNumber(String tableNumber) {
        this.tableNumber = new TableNumber(tableNumber);
        return this;
    }

    /**
     * Sets the {@code Code} of the {@code OrderItem} that we are building.
     */
    public OrderItemBuilder withCode(String menuItemCode) {
        this.menuItemCode = new Code(menuItemCode);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code OrderItem} that we are building.
     */
    public OrderItemBuilder withName(String menuItemName) {
        this.menuItemName = new Name(menuItemName);
        return this;
    }

    /**
     * Sets the quantity of the {@code OrderItem} that we are building.
     */
    public OrderItemBuilder withQuantity(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
        return this;
    }

    public OrderItem build() {
        return new OrderItem(tableNumber, menuItemCode, menuItemName, quantityOrdered);
    }

}
