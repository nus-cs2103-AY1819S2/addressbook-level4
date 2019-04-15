package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysLesson;

import org.junit.Test;

import guitests.guihandles.LessonCardHandle;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;

public class LessonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no optionals
        Lesson lessonWithNotOptionals = new LessonBuilder()
                .withCoreHeaders("A", "B", "C")
                .withNoOptionalHeaders()
                .withNoCards()
                .build();
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

        // same same -> return true
        assertEquals(lessonCard, lessonCard);

        // same lesson, same index -> returns true
        LessonCard copy = new LessonCard(lesson, 0);
        assertEquals(lessonCard, copy);

        // same object -> returns true
        assertEquals(lessonCard, lessonCard);

        // null -> returns false
        assertNotEquals(null, lessonCard);

        // different types -> returns false
        assertNotEquals(0, lessonCard);

        // different types -> return false
        assertNotEquals(lessonCard, new LessonOverview(lesson));

        // same lesson with same index but different index -> returns true
        Lesson differentLesson = new LessonBuilder().withName("differentName").build();
        assertEquals(lessonCard, new LessonCard(differentLesson, 0));

        // different lesson, same index -> returns true
        differentLesson = new LessonBuilder().withNoCards().build();
        assertNotEquals(lessonCard, new LessonCard(differentLesson, 0));

        // same lesson, different index -> returns false
        assertNotEquals(lessonCard, new LessonCard(lesson, 1));
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
