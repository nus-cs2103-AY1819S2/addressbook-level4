package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Option;

/**
 * Jackson-friendly version of {@link Option}.
 */
public class JsonAdaptedOption {

    private final String optionValue;

    /**
     * Constructs a {@code JsonAdaptedOption} with the given {@code optionValue}.
     */
    @JsonCreator
    public JsonAdaptedOption(String optionValue) {
        this.optionValue = optionValue;
    }

    /**
     * Converts a given {@code Option} into this class for Jackson use.
     */
    public JsonAdaptedOption(Option source) {
        optionValue = source.optionValue;
    }

    @JsonValue
    public String getOptionValue() {
        return optionValue;
    }

    /**
     * Converts this Jackson-friendly adapted option object into the model's {@code Option} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted hint.
     */
    public Option toModelType() throws IllegalValueException {
        if (!Option.isValidOption(optionValue)) {
            throw new IllegalValueException(Option.MESSAGE_CONSTRAINTS);
        }
        return new Option(optionValue);
    }
}
