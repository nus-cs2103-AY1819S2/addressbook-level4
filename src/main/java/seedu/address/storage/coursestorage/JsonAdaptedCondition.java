package seedu.address.storage.coursestorage;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Condition;

/**
 * Jackson-friendly version of {@link Condition}
 */

public class JsonAdaptedCondition {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Condition's %s field is missing!";
    public static final String NOT_PARSABLE_AS_INT = "minToSatisfy not parsable as integer";
    private final String minToSatisfy;
    private final String pattern;

    /**
     * Constructs a {@code JsonAdaptedCondition} given condition details
     * @param minToSatisfy minimum number of regexes matches to satisfy condition
     * @param pattern regular expression to be matched
     */
    @JsonCreator
    public JsonAdaptedCondition(@JsonProperty("minToSatisfy") String minToSatisfy,
                                @JsonProperty("pattern") String pattern) {
        this.minToSatisfy = minToSatisfy;
        this.pattern = pattern;
    }

    /**
     * Converts a give {@code Condition} into this class for Jackson use
     * @param condition a condition object to be converted into JsonAdaptedCondition
     */
    public JsonAdaptedCondition(Condition condition) {
        this.minToSatisfy = String.valueOf(condition.getMinToSatisfy());
        this.pattern = condition.getPattern().toString();
    }


    /**
     * Converts this Jackson-friendly adapted condition object into the model's {@code Condition} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted condition.
     */
    public Condition toModelType() throws IllegalValueException {
        try {

            if (minToSatisfy == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "minToSatisfy"));
            }
            if (pattern == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Pattern.class.getSimpleName()));
            }

            final int minToSatisfy = Integer.parseInt(this.minToSatisfy);

            if (!Condition.isValidRegex(pattern)) {
                throw new IllegalValueException(Condition.INVALID_REGEXES);
            }

            if (!Condition.isValidMinToSatisfy(minToSatisfy)) {
                throw new IllegalValueException(Condition.INVALID_MIN_TO_SATISFY);
            }

            return new Condition(minToSatisfy, pattern);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(NOT_PARSABLE_AS_INT);
        }
    }
}
