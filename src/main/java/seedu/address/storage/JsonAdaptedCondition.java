package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Condition;

/**
 * Jackson-friendly version of {@link Condition}.
 */
class JsonAdaptedCondition {

    private final String conditionName;

    /**
     * Constructs a {@code JsonAdaptedCondition} with the given {@code conditionName}.
     */
    @JsonCreator
    public JsonAdaptedCondition(String conditionName) {
        this.conditionName = conditionName;
    }

    /**
     * Converts a given {@code Condition} into this class for Jackson use.
     */
    public JsonAdaptedCondition(Condition source) {
        conditionName = source.toString();
    }

    @JsonValue
    public String getConditionName() {
        return conditionName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Condition} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted condition.
     */
    public Condition toModelType() throws IllegalValueException {
        if (!Condition.isValidConditionName(conditionName)) {
            throw new IllegalValueException(Condition.MESSAGE_CONSTRAINTS);
        }
        return new Condition(conditionName);
    }

}
