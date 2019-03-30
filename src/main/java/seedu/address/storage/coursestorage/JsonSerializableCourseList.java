package seedu.address.storage.coursestorage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseList;
/**
 * An Immutable Course List that is serializable to JSON format.
 */
@JsonRootName(value = "courseList")
public class JsonSerializableCourseList {
    private final List<JsonAdaptedCourse> courseList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCourseList} with the given Courses
     */
    @JsonCreator
    public JsonSerializableCourseList(@JsonProperty("courseList") List<JsonAdaptedCourse> courseList) {
        this.courseList.addAll(courseList);
    }
    /**
     * Converts this CourseList into the model's {@code CourseList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CourseList toModelType() throws IllegalValueException {
        CourseList courseList = new CourseList();
        for (JsonAdaptedCourse jsonAdaptedCourse : this.courseList) {
            Course course = jsonAdaptedCourse.toModelType();
            courseList.addCourse(course);
        }
        return courseList;
    }
}
