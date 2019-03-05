package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.Year;
import seedu.address.model.table.TableNumber;

/**
 * Jackson-friendly version of {@link Statistics}.
 */
class JsonAdaptedBill {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Bill item's %s field is missing!";

    private final String tableNumber;
    private final String orderItemList;
    private final String day;
    private final String month;
    private final String year;
    private final String totalBill;

    /**
     * Constructs a {@code JsonAdaptedBill} with the given bill details.
     */
    @JsonCreator
    public JsonAdaptedBill(@JsonProperty("tableNumber") String tableNumber,
                           @JsonProperty("day") String orderItemList,
                                @JsonProperty("day") String day,
                           @JsonProperty("month") String month,
                           @JsonProperty("year") String year,
                           @JsonProperty("totalBill") String totalBill) {
        this.tableNumber = tableNumber;
        this.orderItemList = orderItemList;
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalBill = totalBill;
    }

    /**
     * Converts a given {@code Bill} into this class for Jackson use.
     */
    public JsonAdaptedBill(Bill source) {
        tableNumber = String.valueOf(source.getTableNumber());
        orderItemList = String.valueOf(source.getOrderItemList());
        day = String.valueOf(source.getDay());
        month = String.valueOf(source.getMonth());
        year = String.valueOf(source.getYear());
        totalBill = String.valueOf(source.getTotalBill());
    }

    /**
     * Converts this Jackson-friendly adapted bill object into the model's {@code Bill} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order item.
     */
    public Bill toModelType() throws IllegalValueException {
        if (tableNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tableNumber"));
        }
        // TODO: check if table number is valid
        if (!TableNumber.isValidTableNumber(tableNumber)) {
            throw new IllegalValueException(TableNumber.MESSAGE_CONSTRAINTS);
        }

        if (day == null && month == null && year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        final TableNumber modelTableNumber = new TableNumber(tableNumber);
        final Day modelDay = new Day(day); 
        final Month modelMonth = new Month(month);
        final Year modelYear = new Year(year);
        final int modelBill = Integer.parseInt(totalBill);

        return new Bill(modelTableNumber, modelDay, modelMonth, modelYear, modelBill);
    }
    
}
