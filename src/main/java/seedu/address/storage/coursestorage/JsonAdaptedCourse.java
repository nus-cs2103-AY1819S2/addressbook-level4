package seedu.address.storage.coursestorage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.CompositeRequirement;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseDescription;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.course.PrimitiveRequirement;

/**
 * Jackson-friendly version of {@link Course}.
 */
public class JsonAdaptedCourse {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course's %s field is missing!";

    private final String name;
    private final String description;
    private final List<JsonAdaptedCourseRequirement> requirements = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedCourse} with the given Course details.
     */
    @JsonCreator
    public JsonAdaptedCourse(@JsonProperty("name") String name,
                             @JsonProperty("description") String courseDescription,
                             @JsonProperty("tagged") List<JsonAdaptedCourseRequirement> requirements) {
        this.name = name;
        this.description = courseDescription;
        if (requirements != null) {
            this.requirements.addAll(requirements);
        }
    }
    /**
     * Converts a given {@code Course} into this class for Jackson use.
     */
    public JsonAdaptedCourse(Course course) {
        name = course.getCourseName().toString();
        description = course.getCourseDescription().toString();
        List<CourseRequirement> requirements = course.getCourseRequirements();
        for (CourseRequirement requirement : requirements) {
            if (requirement instanceof PrimitiveRequirement) {
                this.requirements.add(new JsonAdaptedPrimitiveRequirement(
                        (PrimitiveRequirement) requirement));
            } else {
                this.requirements.add(new JsonAdaptedCompositeRequirement(
                        (CompositeRequirement) requirement));
            }
        }
    }

    /**
     * Converts this Jackson-friendly adapted course object into the model's {@code Course} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Course toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, CourseName.class.getSimpleName()));
        }

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, CourseDescription.class.getSimpleName()));
        }

        final CourseName courseName = new CourseName(name);
        final CourseDescription courseDescription = new CourseDescription(description);
        final List<CourseRequirement> requirements = new ArrayList<>();
        for (JsonAdaptedCourseRequirement courseRequirement : this.requirements) {
                requirements.add(courseRequirement.toModelType());
        }
        return new Course(courseName, courseDescription, requirements.toArray(new CourseRequirement[0]));
    }
}
