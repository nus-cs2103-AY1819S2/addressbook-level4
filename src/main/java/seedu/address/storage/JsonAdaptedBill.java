package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.table.TableNumber;

/**
 * Jackson-friendly version of {@link Statistics}.
 */
class JsonAdaptedBill {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Bill item's %s field is missing!";

    private final String tableNumber;
    private final String totalBill;
    private final String receipt;

    /**
     * Constructs a {@code JsonAdaptedBill} with the given bill details.
     */
    @JsonCreator
    public JsonAdaptedBill(@JsonProperty("tableNumber") String tableNumber,
            @JsonProperty("totalBill") String totalBill,
            @JsonProperty("receipt") String receipt) {
        this.tableNumber = tableNumber;
        this.totalBill = totalBill;
        this.receipt = receipt;
    }

    /**
     * Converts a given {@code Bill} into this class for Jackson use.
     */
    public JsonAdaptedBill(Bill source) {
        tableNumber = String.valueOf(source.getTableNumber());
        totalBill = String.valueOf(source.getTotalBill());
        receipt = String.valueOf(source.getReceipt());

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
        final float modelBill = Float.parseFloat(totalBill);

        return new Bill(modelTableNumber, modelBill, receipt);
    }

}
