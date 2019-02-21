package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.medicine.Medicine;

/**
 * An Immutable Inventory that is serializable to JSON format.
 */
@JsonRootName(value = "Inventory")
class JsonSerializableInventory {

    public static final String MESSAGE_DUPLICATE_MEDICINE = "Medicines list contains duplicate medicine(s).";

    private final List<JsonAdaptedMedicine> medicines = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableInventory} with the given medicines.
     */
    @JsonCreator
    public JsonSerializableInventory(@JsonProperty("medicines") List<JsonAdaptedMedicine> medicines) {
        this.medicines.addAll(medicines);
    }

    /**
     * Converts a given {@code ReadOnlyInventory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableInventory}.
     */
    public JsonSerializableInventory(ReadOnlyInventory source) {
        medicines.addAll(source.getMedicineList().stream().map(JsonAdaptedMedicine::new).collect(Collectors.toList()));
    }

    /**
     * Converts this inventory into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Inventory toModelType() throws IllegalValueException {
        Inventory inventory = new Inventory();
        for (JsonAdaptedMedicine jsonAdaptedMedicine : medicines) {
            Medicine medicine = jsonAdaptedMedicine.toModelType();
            if (inventory.hasMedicine(medicine)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEDICINE);
            }
            inventory.addMedicine(medicine);
        }
        return inventory;
    }

}
