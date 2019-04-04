package seedu.address.storage.coursestorage;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.CourseRequirement;

/**
 * Jackson-friendly version of {@link CourseRequirement}.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonAdaptedPrimitiveRequirement.class,
                name = "PrimitiveReq"),

        @JsonSubTypes.Type(value = JsonAdaptedCompositeRequirement.class,
                name = "CompositeReq") }
)
public interface JsonAdaptedCourseRequirement {
    /**
     * Converts this Jackson-friendly adapted moduleTaken object into the model's {@code CourseRequirement}
     * interface objects.
     * @throws IllegalValueException if unacceptable values of CourseRequirements are used
     */
    CourseRequirement toModelType() throws IllegalValueException;

}
