package seedu.address.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.model.util.SampleCourseRequirement.getTypicalRequirements;

import org.junit.Test;

import seedu.address.model.util.SampleCourse;
import seedu.address.testutil.Assert;

public class CourseTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        CourseRequirement[] nullArray = null;
        Assert.assertThrows(NullPointerException.class, () -> new Course(null,
            new CourseDescription("Course Description"), getTypicalRequirements().toArray(new CourseRequirement[0])));
        Assert.assertThrows(NullPointerException.class, () -> new Course(new CourseName("CourseName"),
                null, getTypicalRequirements().toArray(new CourseRequirement[0])));
        Assert.assertThrows(NullPointerException.class, () -> new Course(new CourseName("CourseName"),
                new CourseDescription("CourseDescription"), nullArray));
        Assert.assertThrows(NullPointerException.class, () -> new Course(new CourseName("CourseName"),
                new CourseDescription("CourseDescription"), null));
    }

    @Test
    public void equal() {
        Course algorithms = SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
        Course ai = SampleCourse.COMPUTER_SCIENCE_AI;
        assertEquals(algorithms, algorithms);
        Course diffCourse = new Course(algorithms.getCourseName(), algorithms.getCourseDescription(),
                algorithms.getCourseRequirements().toArray(new CourseRequirement[0]));
        assertEquals(algorithms, diffCourse);

        assertNotEquals(algorithms, 0);
        assertNotEquals(algorithms, null);
        diffCourse = new Course(ai.getCourseName(), algorithms.getCourseDescription(),
                algorithms.getCourseRequirements().toArray(new CourseRequirement[0]));
        assertNotEquals(diffCourse, algorithms);
        diffCourse = new Course(algorithms.getCourseName(), ai.getCourseDescription(),
                algorithms.getCourseRequirements().toArray(new CourseRequirement[0]));
        assertNotEquals(diffCourse, algorithms);
        diffCourse = new Course(algorithms.getCourseName(), algorithms.getCourseDescription(),
                ai.getCourseRequirements().toArray(new CourseRequirement[0]));
        assertNotEquals(diffCourse, algorithms);
    }

}
