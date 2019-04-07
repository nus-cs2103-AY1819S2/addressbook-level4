package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysLesson;

import org.junit.Test;

import guitests.guihandles.LessonOverviewHandle;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;

public class LessonOverviewTest extends GuiUnitTest {

    @Test
    public void display() {
        // no optionals
        Lesson lessonWithNotOptionals = new LessonBuilder().withNoOptionalHeaders().build();
        LessonOverview lessonOverview = new LessonOverview(lessonWithNotOptionals);
        uiPartRule.setUiPart(lessonOverview);
        assertCardDisplay(lessonOverview, lessonWithNotOptionals, 1);

        // with tags
        Lesson lessonWithOptionals = new LessonBuilder().build();
        lessonOverview = new LessonOverview(lessonWithOptionals);
        uiPartRule.setUiPart(lessonOverview);
        assertCardDisplay(lessonOverview, lessonWithOptionals, 2);
    }

    @Test
    public void equals() {
        Lesson lesson = new LessonBuilder().build();
        LessonOverview lessonOverview = new LessonOverview(lesson);

        // same lesson -> returns true
        LessonOverview copy = new LessonOverview(lesson);
        assertEquals(lessonOverview, copy);

        // same object -> returns true
        assertEquals(lessonOverview, lessonOverview);

        // null -> returns false
        assertNotEquals(null, lessonOverview);

        // different types -> returns false
        assertNotEquals(0, lessonOverview);

        // different types -> returns false
        assertNotEquals(new LessonCard(lesson, 0), lessonOverview);

        // same lesson with same index but different index -> returns true
        Lesson differentLesson = new LessonBuilder().withName("differentName").build();
        assertEquals(lessonOverview, new LessonOverview(differentLesson));

        // different lesson, same index -> returns true
        differentLesson = new LessonBuilder().withNoCards().build();
        assertNotEquals(lessonOverview, new LessonOverview(differentLesson));
    }

    /**
     * Asserts that {@code lessonOverview} displays the details of {@code expectedLesson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(LessonOverview lessonOverview, Lesson expectedLesson, int expectedId) {
        guiRobot.pauseForHuman();

        LessonOverviewHandle lessonOverviewHandle = new LessonOverviewHandle(lessonOverview.getRoot());

        // verify lesson details are displayed correctly
        assertCardDisplaysLesson(expectedLesson, lessonOverviewHandle);
    }
}
