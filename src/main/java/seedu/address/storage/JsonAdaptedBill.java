package seedu.address.storage;

import java.util.Date;

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
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;

/**
 * Jackson-friendly version of {@link Statistics}.
 */
class JsonAdaptedBill {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Bill item's %s field is missing!";

    private final String tableNumber;
    //private final String date;
    private final String totalBill;

    /**
     * Constructs a {@code JsonAdaptedBill} with the given bill details.
     */
    @JsonCreator
    public JsonAdaptedBill(@JsonProperty("tableNumber") String tableNumber,
                           //@JsonProperty("date") String date,
                           @JsonProperty("totalBill") String totalBill) {
        this.tableNumber = tableNumber;
        //this.date = date;
        this.totalBill = totalBill;
    }

    /**
     * Converts a given {@code Bill} into this class for Jackson use.
     */
    public JsonAdaptedBill(Bill source) {
        tableNumber = String.valueOf(source.getTableNumber());
        //date = String.valueOf(source.getFormattedDate());
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

        if (!TableNumber.isValidTableNumber(tableNumber)) {
            throw new IllegalValueException(TableNumber.MESSAGE_CONSTRAINTS);
        }

        final TableNumber modelTableNumber = new TableNumber(tableNumber);
        //final Date modelDate = new Date(); 
        final float modelBill = Float.parseFloat(totalBill);

        return new Bill(modelTableNumber, modelBill);
    }
    
}
