package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.model.util.SampleCourse.getSampleCourseList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.course.CourseList;
import seedu.address.storage.coursestorage.CourseManager;

public class CourseManagerTest {

    private CourseManager courseManager;

    @Before
    public void setUp() {
        courseManager = new CourseManager();
    }


    @Test
    public void courseListRead() throws Exception {
        CourseList courseList = getSampleCourseList();
        CourseList retrieved = courseManager.readCourseFile().get();
        assertEquals(courseList, retrieved);
    }
    @Test
    public void getCourseInputStreamPath() {
        assertNotNull(courseManager.getCourseInputStreamPath());
    }
}

