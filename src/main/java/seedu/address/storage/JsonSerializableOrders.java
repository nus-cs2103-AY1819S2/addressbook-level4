package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;
import seedu.address.model.order.OrderItem;
import seedu.address.model.person.Person;

/**
 * An Immutable order list that is serializable to JSON format.
 */
@JsonRootName(value = "orders")
class JsonSerializableOrders {

    public static final String MESSAGE_DUPLICATE_ORDER_ITEMS = "Order list contains duplicate order item(s).";

    private final List<JsonAdaptedOrderItem> orderItems = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableOrders} with the given order items.
     */
    @JsonCreator
    public JsonSerializableOrders(@JsonProperty("orderitems") List<JsonAdaptedOrderItem> orderItems) {
        this.orderItems.addAll(orderItems);
    }

    /**
     * Converts a given {@code ReadOnlyRestOrRant} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableOrders}.
     */
    public JsonSerializableOrders(ReadOnlyRestOrRant source) {
        orderItems.addAll(source.getOrderItemList().stream().map(JsonAdaptedOrderItem::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this order list into the model's {@code RestOrRant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RestOrRant toModelType() throws IllegalValueException {
        RestOrRant restOrRant = new RestOrRant();
        for (JsonAdaptedOrderItem jsonAdaptedOrderItem : orderItems) {
            OrderItem orderItem = jsonAdaptedOrderItem.toModelType();
            /*if (restOrRant.hasOrderItem(orderItem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ORDER_ITEMS);
            }
            restOrRant.addOrderItem(orderItem);*/
        }
        return restOrRant; // TODO: for now, returns an empty RestOrRant
    }

}
