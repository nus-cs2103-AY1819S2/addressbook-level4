package seedu.address.model.lesson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.LessonListBuilder;
import seedu.address.testutil.TypicalLessons;

public class LessonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Lesson lesson = TypicalLessons.LESSON_DEFAULT;
    private Lesson lessonToAdd = TypicalLessons.LESSON_TRUE_FALSE;
    private LessonList lessonList = new LessonListBuilder()
            .withLessons(List.of(lesson)).build();
    private int defaultSize = lessonList.getLessons().size();

    @Test
    public void getLessons_lessonsNotNull_getsLessonsList() {
        assertNotNull(lessonList.getLessons());
    }

    @Test
    public void getLesson_indexOutOfBounds_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        lessonList.getLesson(-1);
        lessonList.getLesson(999);
    }

    @Test
    public void getLesson_validLesson_getsLesson() {
        assertEquals(lesson, lessonList.getLesson(0));
    }

    @Test
    public void addLesson_validLesson_addsLesson() {
        lessonList.addLesson(lessonToAdd);
        assertEquals(defaultSize + 1, lessonList.getLessons().size());
    }

    @Test
    public void addLesson_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        lessonList.addLesson(null);
    }

    @Test
    public void setLesson_nullLesson_throwsNullPointerException() {
        lessonList.addLesson(lessonToAdd);
        thrown.expect(NullPointerException.class);
        lessonList.setLesson(0, null);
    }

    @Test
    public void setLesson_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        lessonList.setLesson(defaultSize + 1, lesson);
    }

    @Test
    public void setLesson_validData_updatesLesson() {
        assertNotEquals(lessonToAdd, lessonList.getLesson(0));
        lessonList.setLesson(0, lessonToAdd);
        assertEquals(lessonToAdd, lessonList.getLesson(0));
    }

    @Test
    public void deleteLesson_invalidIndex_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        lessonList.deleteLesson(-1);
        lessonList.deleteLesson(1);
    }

    @Test
    public void deleteLesson_validIndex_deletesLesson() {
        lessonList.addLesson(lessonToAdd);
        assertEquals(defaultSize + 1, lessonList.getLessons().size());
        lessonList.deleteLesson(0);
        assertEquals(defaultSize, lessonList.getLessons().size());
        thrown.expect(IllegalArgumentException.class);
        lessonList.getLesson(defaultSize);
    }

    @Test
    public void openLesson_validIndex_opensLesson() {
        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);

        // get openedLesson which has been set to lesson at index 0 -> Lesson returned = lesson at index 0
        assertEquals(lessonList.getOpenedLesson(), lessonList.getLesson(0));

        // cards are the same
        assertEquals(lessonList.getOpenedLessonCards(), lessonList.getLesson(0).getCards());
    }

    @Test
    public void openLesson_invalidIndex_throwsIllegalArgumentException() {
        // open non-existing lesson at invalid index -> IllegalArgumentException thrown
        thrown.expect(IllegalArgumentException.class);
        lessonList.openLesson(-1);
    }

    @Test
    public void noLesson_getOpenedLesson_returnNull() {
        // get openedLesson which has not been set -> return null
        Assert.assertNull(lessonList.getOpenedLesson());
    }

    @Test
    public void getOpenedLesson_returnNull() {
        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);

        // get openedLesson which has been set to valid lesson -> return valid lesson
        assertNotNull(lessonList.getOpenedLesson());

        // close lesson -> openedLesson set to null
        lessonList.closeLesson();

        // get openedLesson which has been set to null by closeLesson -> return null
        Assert.assertNull(lessonList.getOpenedLesson());
    }

    @Test
    public void openAndDeleteLesson_returnNull() {
        lessonList.addLesson(lessonToAdd);

        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);
        assertNotNull(lessonList.getOpenedLesson());

        // delete lesson at valid index -> openedLesson = null
        lessonList.deleteLesson(0);

        // get openedLesson which has been set to null by deleteLesson which calls closeLesson -> return null
        Assert.assertNull(lessonList.getOpenedLesson());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertEquals(lessonList, lessonList);

        // different Lessonlist -> return false
        LessonList diffLessonList = new LessonListBuilder().withLessons(List.of(lessonToAdd)).build();
        assertNotEquals(lessonList, diffLessonList);

        // different types -> returns false
        assertNotEquals(lessonList, new Object());
    }
}
