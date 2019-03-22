package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.ConditionTag;

/**
 * Jackson-friendly version of {@link ConditionTag}.
 */
class JsonAdaptedConditionTag {

    private final String conditionTagName;

    /**
     * Constructs a {@code JsonAdaptedConditionTag} with the given {@code conditionTagName}.
     */
    @JsonCreator
    public JsonAdaptedConditionTag(String conditionTagName) {
        this.conditionTagName = conditionTagName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedConditionTag(ConditionTag source) {
        conditionTagName = source.conditionTagName;
    }

    @JsonValue
    public String getTagName() {
        return conditionTagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public ConditionTag toModelType() throws IllegalValueException {
        if (!ConditionTag.isValidConditionTagName(conditionTagName)) {
            throw new IllegalValueException(ConditionTag.MESSAGE_CONSTRAINTS);
        }
        return new ConditionTag(conditionTagName);
    }

}
