package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.Name;
import seedu.address.model.order.OrderItem;
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
        quantityOrdered = String.valueOf(source.getQuantity());
        quantityUnserved = String.valueOf(0);
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ordered"));
        }

        if (quantityUnserved == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "unserved"));
        }

        final int modelQuantityOrdered;
        // final int modelQuantityUnserved;

        try {
            modelQuantityOrdered = ParserUtil.parseQuantity(quantityOrdered);
            // modelQuantityUnserved = ParserUtil.parseQuantity(quantityUnserved);
        } catch (ParseException e) {
            throw new IllegalValueException(ParserUtil.MESSAGE_INVALID_QUANTITY);
        }

        return new OrderItem(modelTableNumber, modelMenuItemCode, modelMenuItemName, modelQuantityOrdered);
    }

}
