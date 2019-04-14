package seedu.address.storage.coursestorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final String requirementType;
    private final List<JsonAdaptedCondition> conditions;

    /**
     * Creates a {@code JsonAdaptedPrimitiveRequirement} with Primitive Requirement details
     */
    @JsonCreator
    public JsonAdaptedPrimitiveRequirement(@JsonProperty("requirementName") String requirementName,
                                           @JsonProperty("requirementDescription") String requirementDescription,
                                           @JsonProperty("requirementType") String requirementType,
                                           @JsonProperty("conditions") List<JsonAdaptedCondition> conditions) {
        this.requirementName = requirementName;
        this.requirementDescription = requirementDescription;
        this.conditions = conditions;
        this.requirementType = requirementType;
    }

    /**
     * Creates a {@code JsonAdaptedPrimitiveRequirement} from a given Primitive Requirement
     */
    public JsonAdaptedPrimitiveRequirement(PrimitiveRequirement primitiveRequirement) {
        this.requirementName = primitiveRequirement.getCourseReqName();
        this.requirementDescription = primitiveRequirement.getCourseReqDesc();
        this.conditions = primitiveRequirement
                .getConditions()
                .stream()
                .map(JsonAdaptedCondition::new)
                .collect(Collectors.toList());
        this.requirementType = primitiveRequirement.getType().name();
    }


    /**
     * Converts this Jackson-friendly adapted primitive requirement object
     * into the model's {@code PrimitiveRequirement} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted moduleTaken.
     */
    @Override
    public CourseRequirement toModelType() throws IllegalValueException {
        try {
            if (this.requirementName == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, "requirementName"));
            }

            if (this.requirementDescription == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, "requirementDescription"));
            }

            if (this.conditions == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Condition.class.getSimpleName()));
            }

            if (this.requirementType == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, "requirementType"));
            }
            CourseReqType courseReqType = CourseReqType.valueOf(requirementType);
            final List<Condition> modelConditions = new ArrayList<>();
            for (JsonAdaptedCondition condition : conditions) {
                modelConditions.add(condition.toModelType());
            }
            return new PrimitiveRequirement(requirementName, requirementDescription,
                    courseReqType, modelConditions.toArray(new Condition[0]));
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }


    }
}
