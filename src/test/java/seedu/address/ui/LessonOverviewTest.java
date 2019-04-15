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
        Lesson lessonWithNotOptionals = new LessonBuilder()
                .withCoreHeaders("A", "B", "C")
                .withNoOptionalHeaders()
                .withNoCards()
                .build();
        LessonOverview lessonOverview = new LessonOverview(lessonWithNotOptionals);
        uiPartRule.setUiPart(lessonOverview);
        assertCardDisplay(lessonOverview, lessonWithNotOptionals);

        // with tags
        Lesson lessonWithOptionals = new LessonBuilder().build();
        lessonOverview = new LessonOverview(lessonWithOptionals);
        uiPartRule.setUiPart(lessonOverview);
        assertCardDisplay(lessonOverview, lessonWithOptionals);
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
        assertNotEquals(lessonOverview, null);

        // different types -> returns false
        assertNotEquals(lessonOverview, 0);

        // different types -> returns false
        assertNotEquals(lessonOverview, new LessonCard(lesson, 0));

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
    private void assertCardDisplay(LessonOverview lessonOverview, Lesson expectedLesson) {
        guiRobot.pauseForHuman();

        LessonOverviewHandle lessonOverviewHandle = new LessonOverviewHandle(lessonOverview.getRoot());

        // verify lesson details are displayed correctly
        assertCardDisplaysLesson(expectedLesson, lessonOverviewHandle);
    }
}
