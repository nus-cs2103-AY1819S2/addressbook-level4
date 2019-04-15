package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.Name;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.OrderItemStatus;
import seedu.address.model.table.TableNumber;

/**
 * Jackson-friendly version of {@link OrderItem}.
 */
class JsonAdaptedOrderItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order item's %s field is missing!";

    private final String tableNumber;
    private final String menuItemCode;
    private final String menuItemName;
    private final String quantityOrdered;
    private final String quantityUnserved;

    /**
     * Constructs a {@code JsonAdaptedOrderItem} with the given order item details.
     */
    @JsonCreator
    public JsonAdaptedOrderItem(@JsonProperty("tableNumber") String tableNumber,
                                @JsonProperty("menuItemCode") String menuItemCode,
                                @JsonProperty("menuItemName") String menuItemName,
                                @JsonProperty("ordered") String quantityOrdered,
                                @JsonProperty("unserved") String quantityUnserved) {
        this.tableNumber = tableNumber;
        this.menuItemCode = menuItemCode;
        this.menuItemName = menuItemName;
        this.quantityOrdered = quantityOrdered;
        this.quantityUnserved = quantityUnserved;
    }

    /**
     * Converts a given {@code OrderItem} into this class for Jackson use.
     */
    public JsonAdaptedOrderItem(OrderItem source) {
        tableNumber = source.getTableNumber().getTableNumber();
        menuItemCode = source.getMenuItemCode().toString();
        menuItemName = source.getMenuItemName().toString();
        quantityOrdered = String.valueOf(source.getQuantityOrdered());
        quantityUnserved = String.valueOf(source.getQuantityToServe());
    }

    /**
     * Converts this Jackson-friendly adapted order item object into the model's {@code OrderItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order item.
     */
    public OrderItem toModelType() throws IllegalValueException {
        if (tableNumber == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TableNumber.class.getSimpleName()));
        }
        if (!TableNumber.isValidTableNumber(tableNumber)) {
            throw new IllegalValueException(TableNumber.MESSAGE_CONSTRAINTS);
        }
        final TableNumber modelTableNumber = new TableNumber(tableNumber);

        if (menuItemCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Code.class.getSimpleName()));
        }
        if (!Code.isValidCode(menuItemCode)) {
            throw new IllegalValueException(Code.MESSAGE_CONSTRAINTS);
        }
        final Code modelMenuItemCode = new Code(menuItemCode);

        if (menuItemName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(menuItemName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelMenuItemName = new Name(menuItemName);

        if (quantityOrdered == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "quantityOrdered"));
        }

        if (quantityUnserved == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "quantityUnserved"));
        }

        if (!OrderItemStatus.isValidQuantities(quantityOrdered, quantityUnserved)) {
            throw new IllegalValueException(OrderItemStatus.MESSAGE_CONSTRAINTS);
        }

        final OrderItemStatus modelItemStatus = new OrderItemStatus(quantityOrdered, quantityUnserved);

        return new OrderItem(modelTableNumber, modelMenuItemCode, modelMenuItemName, modelItemStatus);
    }

}
