package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.medicine.Medicine;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_MEDICINE = "Medicines list contains duplicate medicine(s).";

    private final List<JsonAdaptedMedicine> medicines = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given medicines.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("medicines") List<JsonAdaptedMedicine> medicines) {
        this.medicines.addAll(medicines);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        medicines.addAll(source.getMedicineList().stream().map(JsonAdaptedMedicine::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedMedicine jsonAdaptedMedicine : medicines) {
            Medicine medicine = jsonAdaptedMedicine.toModelType();
            if (addressBook.hasMedicine(medicine)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEDICINE);
            }
            addressBook.addMedicine(medicine);
        }
        return addressBook;
    }

}
