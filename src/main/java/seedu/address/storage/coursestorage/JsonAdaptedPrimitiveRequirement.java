package seedu.address.storage.coursestorage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Condition;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.course.PrimitiveRequirement;

/**
 * A Jackson-friendly version of {@link PrimitiveRequirement}
 */
public class JsonAdaptedPrimitiveRequirement implements JsonAdaptedCourseRequirement {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "PrimitiveRequirement's %s field is missing!";
    private final String requirementName;
    private final String requirementDescription;
    private final JsonAdaptedCondition condition;
    private final String requirementType;

    /**
     * Creates a {@code JsonAdaptedPrimitiveRequirement} with Primitive Requirement details
     */
    @JsonCreator
    public JsonAdaptedPrimitiveRequirement(@JsonProperty("requirementName") String requirementName,
                                           @JsonProperty("requirementDescription") String requirementDescription,
                                           @JsonProperty("condition") JsonAdaptedCondition condition,
                                           @JsonProperty("requirementType") String requirementType) {
        this.requirementName = requirementName;
        this.requirementDescription = requirementDescription;
        this.condition = condition;
        this.requirementType = requirementType;
    }

    /**
     * Creates a {@code JsonAdaptedPrimitiveRequirement} from a given Primitive Requirement
     */
    public JsonAdaptedPrimitiveRequirement(PrimitiveRequirement primitiveRequirement) {
        this.requirementName = primitiveRequirement.getCourseReqName();
        this.requirementDescription = primitiveRequirement.getCourseReqDesc();
        this.condition = new JsonAdaptedCondition(primitiveRequirement.getCondition());
        this.requirementType = primitiveRequirement.getType().toString();
    }


    /**
     * Converts this Jackson-friendly adapted primitive requirement object
     * into the model's {@code PrimitiveRequirement} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted moduleTaken.
     */
    @Override
    public CourseRequirement toModelType() throws IllegalValueException {
        if (this.requirementName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "requirementName"));
        }

        if (this.requirementDescription == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "requirementDescription"));
        }

        if (this.condition == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "condition"));
        }

        if (this.requirementType == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "requirementType"));
        }

        final Condition condition = this.condition.toModelType();
        return new PrimitiveRequirement(requirementName, requirementDescription,
            condition, CourseReqType.valueOf(requirementType));
    }
}
