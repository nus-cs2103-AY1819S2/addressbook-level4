package seedu.address.storage;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Medicine;

/**
 * Jackson-friendly version of {@link Medicine}.
 */
public class JsonAdaptedMedicine {

    private String name;
    private int quantity;
    private int threshold;
    private BigDecimal price;

    @JsonCreator
    public JsonAdaptedMedicine (@JsonProperty("name") String name, @JsonProperty("quantity") int quantity,
                                @JsonProperty("threshold") int threshold, @JsonProperty("price") BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
        this.price = price;
    }

    public JsonAdaptedMedicine (Medicine medicine) {
        this.name = medicine.name;
        this.quantity = medicine.getQuantity();
        this.price = medicine.getPrice();
        this.threshold = medicine.getThreshold();
    }

    /**
     * Converts this Jackson-friendly adapted medicine object into the model's {@link Medicine} object.
     * @return A model-type medicine object.
     * @throws IllegalValueException If the quantity/price values violates preconditions.
     */
    public Medicine toModelType() {
        Medicine medicine = new Medicine(name, quantity);
        medicine.setPrice(price);
        medicine.setThreshold(threshold);
        return medicine;
    }

    public String getName() {
        return name;
    }
}
