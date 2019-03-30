package seedu.address.storage.coursestorage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.CompositeRequirement;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.course.PrimitiveRequirement;

/**
 * Jackson-friendly version of {@link CompositeRequirement}
 */
public class JsonAdaptedCompositeRequirement implements JsonAdaptedCourseRequirement {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CompositeRequirement's %s field is missing!";

    private final JsonAdaptedCourseRequirement first;
    private final JsonAdaptedCourseRequirement second;
    private final String logicalConnector;
    private final String courseReqType;

    /**
     * Constructs a {@code JsonAdaptedCompositeRequirement} with given Composite Requirement details
     */
    @JsonCreator
    public JsonAdaptedCompositeRequirement(@JsonProperty("first") JsonAdaptedCourseRequirement first,
                                           @JsonProperty("second") JsonAdaptedCourseRequirement second,
                                           @JsonProperty("LogicalConnector") String logicalConnector,
                                           @JsonProperty("courseReqType") String courseReqType) {
        this.first = first;
        this.second = second;
        this.logicalConnector = logicalConnector;
        this.courseReqType = courseReqType;
    }

    /**
     * Converts a {@code CompositeRequirement} into this class for Jackson use
     */
    public JsonAdaptedCompositeRequirement(CompositeRequirement requirement) {
        if (requirement.getFirst() instanceof CompositeRequirement) {
            this.first = new JsonAdaptedCompositeRequirement((CompositeRequirement) requirement.getFirst());
        } else {
            this.first = new JsonAdaptedPrimitiveRequirement((PrimitiveRequirement) requirement.getFirst());
        }

        if (requirement.getSecond() instanceof CompositeRequirement) {
            this.second = new JsonAdaptedCompositeRequirement((CompositeRequirement) requirement.getSecond());
        } else {
            this.second = new JsonAdaptedPrimitiveRequirement((PrimitiveRequirement) requirement.getSecond());
        }

        this.logicalConnector = requirement.getLogicalConnector().toString();
        this.courseReqType = requirement.getCourseReqType().toString();
    }

    /**
     * Converts this Jackson-friendly adapted course object into the model's {@code CourseRequirement} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Composite Requirement.
     */
    @Override
    public CourseRequirement toModelType() throws IllegalValueException {
        if (first == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "first requirement"));
        }

        if (second == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "second requirement"));
        }

        if (logicalConnector == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "Logical Connector"));
        }

        if (courseReqType == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "Course Requirement Type"));
        }

        return new CompositeRequirement(first.toModelType(), second.toModelType(),
                CompositeRequirement.LogicalConnector.valueOf(logicalConnector),
                CourseReqType.valueOf(courseReqType));
    }
}
