package seedu.address.model.lesson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LessonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LessonList lessonList = new LessonList();

    private void addTestLesson() {
        lessonList.addLesson(getTestLesson());
    }

    private Lesson getTestLesson() {
        ArrayList<String> testFields = new ArrayList<>();
        testFields.add("test 1");
        testFields.add("test 2");
        Lesson lesson = new Lesson("test", 2, testFields);

        return lesson;
    }
    @Test
    public void getLessons_lessonsNotNull_getsLessonsList() {
        assertNotNull(lessonList.getLessons());
    }

    @Test
    public void getLesson_indexOutOfBounds_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        lessonList.getLesson(0);
        lessonList.getLesson(-1);
        lessonList.getLesson(999);
    }

    @Test
    public void getLesson_validLesson_getsLesson() {
        addTestLesson();
        assertEquals(getTestLesson(), lessonList.getLesson(0));
    }

    @Test
    public void addLesson_validLesson_addsLesson() {
        addTestLesson();
        assertEquals(1, lessonList.getLessons().size());
    }

    @Test
    public void addLesson_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        lessonList.addLesson(null);
    }

    @Test
    public void setLesson_nullLesson_throwsNullPointerException() {
        addTestLesson();
        thrown.expect(NullPointerException.class);
        lessonList.setLesson(0, null);
    }

    @Test
    public void setLesson_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        lessonList.setLesson(0, getTestLesson());
    }

    @Test
    public void setLesson_validData_updatesLesson() {
        addTestLesson();
        Lesson newLesson = getTestLesson();
        newLesson.addCard(Arrays.asList("test1", "test2"));
        assertNotEquals(newLesson, getTestLesson());
        lessonList.setLesson(0, newLesson);
        assertEquals(newLesson, lessonList.getLesson(0));
    }

    @Test
    public void deleteLesson_invalidIndex_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        lessonList.deleteLesson(0);
        lessonList.deleteLesson(-1);
        lessonList.deleteLesson(1);
    }

    @Test
    public void deleteLesson_validIndex_deletesLesson() {
        addTestLesson();
        assertEquals(1, lessonList.getLessons().size());
        assertEquals(getTestLesson(), lessonList.getLesson(0));
        lessonList.deleteLesson(0);
        assertEquals(0, lessonList.getLessons().size());
        thrown.expect(IllegalArgumentException.class);
        lessonList.getLesson(0);
    }

    @Test
    public void openLesson_validIndex_opensLesson() {
        addTestLesson();
        assertEquals(1, lessonList.getLessons().size());
        assertEquals(getTestLesson(), lessonList.getLesson(0));

        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);

        // get openedLesson which has been set to lesson at index 0 -> Lesson returned = lesson at index 0
        assertEquals(lessonList.getOpenedLesson(), lessonList.getLesson(0));
    }

    @Test
    public void openLesson_invalidIndex_throwsIllegalArgumentException() {
        addTestLesson();
        assertEquals(1, lessonList.getLessons().size());
        assertEquals(getTestLesson(), lessonList.getLesson(0));

        // open non-existing lesson at invalid index -> IllegalArgumentException thrown
        thrown.expect(IllegalArgumentException.class);
        lessonList.openLesson(1);
    }

    @Test
    public void noLesson_getOpenedLesson_returnNull() {
        assertEquals(0, lessonList.getLessons().size());

        // get openedLesson which has not been set -> return null
        assertEquals(lessonList.getOpenedLesson(), null);
    }

    @Test
    public void openLesson_closeLesson_getOpenedLesson_returnNull() {
        addTestLesson();
        assertEquals(1, lessonList.getLessons().size());
        assertEquals(getTestLesson(), lessonList.getLesson(0));

        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);

        // close lesson -> openedLesson set to null
        lessonList.closeLesson();

        // get openedLesson which has been set to null by closeLesson -> return null
        assertEquals(lessonList.getOpenedLesson(), null);
    }

    @Test
    public void equals() {
        LessonList diffLessonList = new LessonList();
        diffLessonList.addLesson(getTestLesson());
        assertEquals(lessonList, lessonList);
        assertNotEquals(lessonList, diffLessonList);
        assertNotEquals(lessonList, new Object());
        LessonList diffLessonListCopy = new LessonList();
        for (Lesson l : diffLessonList.getLessons()) {
            diffLessonListCopy.addLesson(l);
        }
        assertEquals(diffLessonList, diffLessonListCopy);
    }
}
