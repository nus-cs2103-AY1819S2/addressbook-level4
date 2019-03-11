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
import seedu.address.model.record.Record;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_RECORD = "Records list contains duplicate record(s).";

    private final List<JsonAdaptedRecord> records = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given records.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("records") List<JsonAdaptedRecord> records) {
        this.records.addAll(records);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        records.addAll(source.getRecordList().stream().map(JsonAdaptedRecord::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedRecord jsonAdaptedRecord : records) {
            Record record = jsonAdaptedRecord.toModelType();
            if (addressBook.hasRecord(record)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECORD);
            }
            addressBook.addRecord(record);
        }
        return addressBook;
    }

}
