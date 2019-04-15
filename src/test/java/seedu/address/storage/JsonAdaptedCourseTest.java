package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.coursestorage.JsonAdaptedCourse.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.CompositeRequirement;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseDescription;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.PrimitiveRequirement;
import seedu.address.model.util.SampleCourse;
import seedu.address.storage.coursestorage.JsonAdaptedCompositeRequirement;
import seedu.address.storage.coursestorage.JsonAdaptedCourse;
import seedu.address.storage.coursestorage.JsonAdaptedCourseRequirement;
import seedu.address.storage.coursestorage.JsonAdaptedPrimitiveRequirement;

public class JsonAdaptedCourseTest {

    private final Course algorithms = SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
    private final String courseName = algorithms.getCourseName().toString();
    private final String courseDesc = algorithms.getCourseDescription().toString();
    private final List<JsonAdaptedCourseRequirement> courseRequirements =
            algorithms.getCourseRequirements().stream()
                    .map(courseRequirement -> courseRequirement instanceof CompositeRequirement
                            ? new JsonAdaptedCompositeRequirement((CompositeRequirement) courseRequirement)
                            : new JsonAdaptedPrimitiveRequirement((PrimitiveRequirement) courseRequirement))
                    .collect(Collectors.toList());


    private final String invalidName = "!@^%^";
    private final String invalidDesc = "   description";

    @Test
    public void toModelType_validCourse_returnsCourse() throws Exception {
        JsonAdaptedCourse test = new JsonAdaptedCourse(algorithms);
        assertEquals(algorithms, test.toModelType());
    }

    @Test
    public void toModelType_missingName_throwsIllegalValueException() {
        JsonAdaptedCourse test = new JsonAdaptedCourse(null, courseDesc, courseRequirements);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CourseName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedCourse test = new JsonAdaptedCourse(invalidName, courseDesc, courseRequirements);
        assertThrows(IllegalValueException.class, test::toModelType);
    }

    @Test
    public void toModelType_missingDesc_throwsIllegalValueException() {
        JsonAdaptedCourse test = new JsonAdaptedCourse(courseName, null , courseRequirements);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CourseDescription.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }

    @Test
    public void toModelType_invalidDesc_throwsIllegalValueException() {
        JsonAdaptedCourse test = new JsonAdaptedCourse(courseName, invalidDesc, courseRequirements);
        assertThrows(IllegalValueException.class, test::toModelType);
    }
}
