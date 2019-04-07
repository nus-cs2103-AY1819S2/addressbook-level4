package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import guitests.guihandles.LessonCardHandle;
import guitests.guihandles.LessonOverviewHandle;
import guitests.guihandles.ResultDisplayHandle;

import seedu.address.model.lesson.Lesson;
import seedu.address.ui.LessonCard;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(LessonCardHandle expectedCard, LessonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());

        List<String> headers = expectedCard.getHeaders();
        List<String> headersToCompare = expectedCard.getHeaders();
        assertEquals(headers, headersToCompare);
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedLesson}.
     */
    public static void assertCardDisplaysLesson(Lesson expectedLesson, LessonCardHandle actualCard) {
        assertEquals(expectedLesson.getName(), actualCard.getName());
        assertEquals(LessonCard.getCountString(expectedLesson.getCardCount()), actualCard.getCount());

        List<String> headers = actualCard.getHeaders();
        List<String> headersToCompare = new ArrayList<>(expectedLesson.getCoreHeaders());
        headersToCompare.addAll(expectedLesson.getOptionalHeaders());
        assertEquals(headers, headersToCompare);
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedLesson}.
     */
    public static void assertCardDisplaysLesson(Lesson expectedLesson, LessonOverviewHandle actualCard) {
        assertEquals(expectedLesson.getName(), actualCard.getName());
        assertEquals(LessonCard.getCountString(expectedLesson.getCardCount()), actualCard.getCount());

        List<String> headers = actualCard.getHeaders();
        List<String> headersToCompare = new ArrayList<>(expectedLesson.getCoreHeaders());
        headersToCompare.addAll(expectedLesson.getOptionalHeaders());
        assertEquals(headers, headersToCompare);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
