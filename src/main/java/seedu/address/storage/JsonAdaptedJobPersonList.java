package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Nric;

/**
 * Jackson-friendly version of {@link Nric}.
 */
class JsonAdaptedJobPersonList {

    private final String nric;

    /**
     * Constructs a {@code JsonAdaptedJobPersonList} with the given {@code Nric}.
     */
    @JsonCreator
    public JsonAdaptedJobPersonList(String nric) {
        this.nric = nric;
    }

    /**
     * Converts a given {@code JsonAdaptedJobPersonList} into this class for Jackson use.
     */
    public JsonAdaptedJobPersonList(Nric source) {
        nric = source.value;
    }

    @JsonValue
    public String getNricName() {
        return nric;
    }

    /**
     * Converts this Jackson-friendly adapted nric object into the model's {@code Nric} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted nric.
     */
    public Nric toModelType() throws IllegalValueException {
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(nric);
    }

}
