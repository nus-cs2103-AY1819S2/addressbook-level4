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
import seedu.address.model.apparel.Apparel;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "apparels")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_APPAREL = "Apparels list contains duplicate apparel(s).";

    private final List<JsonAdaptedApparel> apparels = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given apparels.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("apparels") List<JsonAdaptedApparel> apparels) {
        this.apparels.addAll(apparels);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        apparels.addAll(source.getApparelList().stream().map(JsonAdaptedApparel::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedApparel jsonAdaptedApparel : apparels) {
            Apparel apparel = jsonAdaptedApparel.toModelType();
            if (addressBook.hasApparel(apparel)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPAREL);
            }
            addressBook.addApparel(apparel);
        }
        return addressBook;
    }

}
