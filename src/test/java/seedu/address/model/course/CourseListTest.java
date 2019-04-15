package seedu.address.model.course;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.util.SampleCourse.COMPUTER_SCIENCE_AI;
import static seedu.address.model.util.SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
import static seedu.address.model.util.SampleCourse.getSampleCourseList;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class CourseListTest {

    @Test
    public void constructor_emptyList() {
        assertEquals(new CourseList().getObservableList(), Collections.emptyList());
    }

    @Test
    public void equals() {
        CourseList courseList = new CourseList();
        courseList.addCourse(COMPUTER_SCIENCE_ALGORITHMS);
        assertTrue(courseList.equals(courseList));
        CourseList anotherCourseList = new CourseList();
        anotherCourseList.addCourse(COMPUTER_SCIENCE_ALGORITHMS);
        assertTrue(courseList.equals(anotherCourseList));

        assertFalse(courseList.equals(null));
        assertFalse(courseList.equals(5));
        courseList.addCourse(COMPUTER_SCIENCE_AI);
        assertFalse(courseList.equals(anotherCourseList));
    }

    @Test
    public void getObservableList_modify_throwsUnsupportedException() {
        CourseList courseList = new CourseList();
        courseList.addCourse(COMPUTER_SCIENCE_ALGORITHMS);
        List<Course> obervableCourseList = courseList.getObservableList();
        assertThrows(UnsupportedOperationException.class, () -> obervableCourseList.remove(0));
    }

    @Test
    public void getCourse_courseInCourseList_returnCourse() {
        CourseList courseList = getSampleCourseList();

        assertTrue(courseList.getCourse(new CourseName("Computer Science Artificial Intelligence"))
                == COMPUTER_SCIENCE_AI);
    }

    @Test
    public void getCourse_courseNotInCourseList_returnNull() {
        CourseList courseList = getSampleCourseList();
        assertTrue(courseList.getCourse(new CourseName("Computer Science AI")) == null);
    }

}
