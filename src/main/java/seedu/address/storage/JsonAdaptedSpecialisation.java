package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Specialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Jackson-friendly version of {@link Specialisation}.
 */
class JsonAdaptedSpecialisation {

    private final String spec;

    /**
     * Constructs a {@code JsonAdaptedSpecialisation} with the given {@code spec}.
     */
    @JsonCreator
    public JsonAdaptedSpecialisation(String spec) {
        this.spec = spec;
    }

    /**
     * Converts a given {@code Specialisation} into this class for Jackson use.
     */
    public JsonAdaptedSpecialisation(Specialisation source) {
        spec = source.specialisation;
    }

    @JsonValue
    public String getSpec() {
        return spec;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Specialisation} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted specialisation.
     */
    public Specialisation toModelType() throws IllegalValueException {
        if (!Specialisation.isValidSpecialisation(spec)) {
            throw new IllegalValueException(Specialisation.MESSAGE_CONSTRAINTS);
        }
        return new Specialisation(spec);
    }

}
