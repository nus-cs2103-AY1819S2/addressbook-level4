package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.FlashcardCardViewHandle;
import guitests.guihandles.FlashcardListCardHandle;
import guitests.guihandles.FlashcardListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.flashcard.Flashcard;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(FlashcardListCardHandle expectedCard, FlashcardListCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getFrontFace(), actualCard.getFrontFace());
        assertEquals(expectedCard.getBackFace(), actualCard.getBackFace());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCard}.
     */
    public static void assertListCardDisplaysFlashcard(Flashcard expectedCard, FlashcardListCardHandle actualCard) {
        assertEquals(expectedCard.getFrontFace().text, actualCard.getFrontFace());
        assertEquals(expectedCard.getBackFace().text, actualCard.getBackFace());
        assertEquals(expectedCard.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
            actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedFlashcard}.
     */
    public static void assertCardViewDisplaysFlashcard(Flashcard expectedCard, FlashcardCardViewHandle actualCard) {
        assertEquals(expectedCard.getFrontFace().text, actualCard.getFrontFace());
        assertEquals(expectedCard.getBackFace().text, actualCard.getBackFace());
    }

    /**
     * Asserts that the list in {@code flashcardListPanelHandle} displays the details of {@code flashcards} correctly
     * and
     * in the correct order.
     */
    public static void assertListMatching(FlashcardListPanelHandle flashcardListPanelHandle, Flashcard... flashcards) {
        for (int i = 0; i < flashcards.length; i++) {
            flashcardListPanelHandle.navigateToCard(i);
            assertListCardDisplaysFlashcard(flashcards[i], flashcardListPanelHandle.getFlashcardCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code flashcardListPanelHandle} displays the details of {@code flashcards} correctly
     * and
     * in the correct order.
     */
    public static void assertListMatching(FlashcardListPanelHandle flashcardListPanelHandle,
                                          List<Flashcard> flashcards) {
        assertListMatching(flashcardListPanelHandle, flashcards.toArray(new Flashcard[0]));
    }

    /**
     * Asserts the size of the list in {@code flashcardListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(FlashcardListPanelHandle flashcardListPanelHandle, int size) {
        int numberOfPeople = flashcardListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
