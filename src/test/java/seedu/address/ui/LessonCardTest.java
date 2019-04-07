package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysLesson;

import org.junit.Test;

import guitests.guihandles.LessonCardHandle;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;

public class LessonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no optionals
        Lesson lessonWithNotOptionals = new LessonBuilder().withNoOptionalHeaders().build();
        LessonCard lessonCard = new LessonCard(lessonWithNotOptionals, 1);
        uiPartRule.setUiPart(lessonCard);
        assertCardDisplay(lessonCard, lessonWithNotOptionals, 1);

        // with tags
        Lesson lessonWithOptionals = new LessonBuilder().build();
        lessonCard = new LessonCard(lessonWithOptionals, 2);
        uiPartRule.setUiPart(lessonCard);
        assertCardDisplay(lessonCard, lessonWithOptionals, 2);
    }

    @Test
    public void equals() {
        Lesson lesson = new LessonBuilder().build();
        LessonCard lessonCard = new LessonCard(lesson, 0);

        // same lesson, same index -> returns true
        LessonCard copy = new LessonCard(lesson, 0);
        assertTrue(lessonCard.equals(copy));

        // same object -> returns true
        assertTrue(lessonCard.equals(lessonCard));

        // null -> returns false
        assertFalse(lessonCard.equals(null));

        // different types -> returns false
        assertFalse(lessonCard.equals(0));

        // same lesson with same index but different index -> returns true
        Lesson differentLesson = new LessonBuilder().withName("differentName").build();
        assertTrue(lessonCard.equals(new LessonCard(differentLesson, 0)));

        // different lesson, same index -> returns true
        differentLesson = new LessonBuilder().withNoCards().build();
        assertFalse(lessonCard.equals(new LessonCard(differentLesson, 0)));

        // same lesson, different index -> returns false
        assertFalse(lessonCard.equals(new LessonCard(lesson, 1)));
    }

    /**
     * Asserts that {@code lessonCard} displays the details of {@code expectedLesson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(LessonCard lessonCard, Lesson expectedLesson, int expectedId) {
        guiRobot.pauseForHuman();

        LessonCardHandle lessonCardHandle = new LessonCardHandle(lessonCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", lessonCardHandle.getId());

        // verify lesson details are displayed correctly
        assertCardDisplaysLesson(expectedLesson, lessonCardHandle);
    }
}
