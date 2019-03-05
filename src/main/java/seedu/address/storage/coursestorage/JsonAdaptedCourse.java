package seedu.address.storage.coursestorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseDescription;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.CourseRequirement;

/**
 * Jackson-friendly version of {@link Course}.
 */
public class JsonAdaptedCourse {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course uirement's %s field is missing!";
    private final String courseName;
    private final String courseDesc;
    private final List<JsonAdaptedCourseRequirement> courseRequirements = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCourse} with the given Course uirement details.
     */
    @JsonCreator
    public JsonAdaptedCourse(@JsonProperty("courseName") String courseName,
                             @JsonProperty("courseDesc") String courseDesc,
                             @JsonProperty("modules") List<JsonAdaptedCourseRequirement> courseRequirements) {
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        if (courseRequirements != null) {
            this.courseRequirements.addAll(courseRequirements);
        }
    }

    /**
     * Converts a given {@code Course} into this class for Jackson use.
     */
    public JsonAdaptedCourse(Course source) {
        courseName = source.getCourseName().toString();
        courseDesc = source.getCourseDescription().toString();
        courseRequirements.addAll(source.getCourseRequirements()
                .stream()
                .map(JsonAdaptedCourseRequirement::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Course} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Course.
     */
    public Course toModelType() throws IllegalValueException {
        if (courseName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, CourseName.class.getSimpleName()));
        }
        if (!CourseName.isValidCourseName(courseName)) {
            throw new IllegalValueException(CourseName.MESSAGE_CONSTRAINTS);
        }
        final CourseName modelCourseName = new CourseName(courseName);

        if (courseDesc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CourseDescription.class.getSimpleName()));
        }
        if (!CourseDescription.isValidCourseDescription(courseDesc)) {
            throw new IllegalValueException(CourseDescription.MESSAGE_CONSTRAINTS);
        }
        final CourseDescription modelCourseDesc = new CourseDescription(courseDesc);

        final ArrayList<CourseRequirement> modelCourseRequirements = new ArrayList<>();

        for (JsonAdaptedCourseRequirement courseRequirement: courseRequirements) {
            modelCourseRequirements.add(courseRequirement.toModelType());
        }
        return new Course(modelCourseName, modelCourseDesc,
                modelCourseRequirements.toArray(new CourseRequirement[0]));


    }
}
