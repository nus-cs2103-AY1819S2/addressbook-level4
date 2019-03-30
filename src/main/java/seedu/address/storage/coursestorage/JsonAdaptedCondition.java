package seedu.address.storage.coursestorage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Condition;

/**
 * Jackson-friendly version of {@link Condition}
 */

public class JsonAdaptedCondition {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Condition's %s field is missing!";
    private final String conditionName;
    private final String minToSatisfy;
    private final List<String> regexes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCondition} given condition details
     * @param conditionName name of condition to be satisfied
     * @param minToSatisfy minimum number of regexes matches to satisfy condition
     * @param regexes regexes to be matched
     */
    @JsonCreator
    public JsonAdaptedCondition(@JsonProperty("conditionName") String conditionName,
                                @JsonProperty("minToSatisfy") String minToSatisfy,
                                @JsonProperty("regexes") List<String> regexes) {
        this.conditionName = conditionName;
        if (regexes != null) {
            this.regexes.addAll(regexes);
        }
        this.minToSatisfy = minToSatisfy;
    }

    /**
     * Converts a give {@code Condition} into this class for Jackson use
     * @param condition a condition object to be converted into JsonAdaptedCondition
     */
    public JsonAdaptedCondition(Condition condition) {
        this.conditionName = condition.getConditionName();
        this.minToSatisfy = String.valueOf(condition.getMinToSatisfy());
        this.regexes.addAll(condition.getRegexes());
    }


    /**
     * Converts this Jackson-friendly adapted condition object into the model's {@code Condition} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted condition.
     */
    public Condition toModelType() throws IllegalValueException {
        try {
            if (conditionName == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, "conditionName"));
            }

            if (minToSatisfy == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, "minToSatisfy"));
            }

            if (regexes.size() == 0) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, "regexes"));
            }

            final int minToSatisfy = Integer.parseInt(this.minToSatisfy);

            return new Condition(minToSatisfy, conditionName, regexes.toArray(new String[0]));

        } catch (NumberFormatException e) {
            throw new IllegalValueException(String.format("minToSatisfy not parseable as integer"));
        }
    }

}
