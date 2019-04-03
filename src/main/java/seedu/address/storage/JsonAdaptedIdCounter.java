package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.IdCounter;

/**
 * Jackson-friendly version of {@link IdCounter}.
 */
public class JsonAdaptedIdCounter {

    private final int value;

    /**
     * Constructs a {@code JsonAdaptedIdCounter} with the given {@code idCounterValue}.
     */
    @JsonCreator
    public JsonAdaptedIdCounter(int idCounterValue) {
        this.value = idCounterValue;
    }

    /**
     * Converts a given {@code IdCounter} into this class for Jackson use.
     */
    public JsonAdaptedIdCounter(IdCounter source) {
        value = source.getValue();
    }

    @JsonValue
    public int getIdCounterValue() {
        return value;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code IdCounter} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted IdCounter.
     */
    public IdCounter toModelType() throws IllegalValueException {
        return new IdCounter(value);
    }

}
