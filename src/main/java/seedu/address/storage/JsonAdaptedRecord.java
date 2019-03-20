package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedConstants.DIVIDER;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetime.DateBase;
import seedu.address.model.description.Description;
import seedu.address.model.person.Name;
import seedu.address.model.record.Record;
import seedu.address.model.record.exceptions.BadRecordFormatException;

/**
 * Jackson-friendly version of {@link Record}.
 */
class JsonAdaptedRecord {

    private final String doctorName;

    private final String description;

    private final String recordDate;

    /**
     * Constructs a {@code JsonAdaptedRecord} with the given {@code recordLine}.
     */
    @JsonCreator
    public JsonAdaptedRecord(String recordLine) {
        String[] sb = recordLine.split(DIVIDER);

        if (sb.length == 3) {
            doctorName = sb[0];
            description = sb[1];
            recordDate = sb[2];
        } else {
            throw new BadRecordFormatException();
        }
    }

    /**
     * Converts a given {@code Record} into this class for Jackson use.
     */
    public JsonAdaptedRecord(Record record) {
        String recordLine = record.toString();
        String[] sb = recordLine.split(DIVIDER);

        if (sb.length == 3) {
            doctorName = sb[0];
            description = sb[1];
            recordDate = sb[2];
        } else {
            throw new BadRecordFormatException();
        }
    }

    @JsonValue
    public String getRecordName() {
        return doctorName + DIVIDER + description + DIVIDER + recordDate;
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Record} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record.
     */
    public Record toModelType() throws IllegalValueException {
        if (!Name.isValidName(doctorName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        } else if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        } else if (!DateBase.isValidDate(recordDate)) {
            throw new IllegalValueException(DateBase.MESSAGE_CONSTRAINTS);
        } else {
            return new Record(doctorName, description, recordDate);
        }
    }

}
