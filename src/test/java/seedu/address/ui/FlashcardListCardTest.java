package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertListCardDisplaysFlashcard;

import org.junit.Test;

import guitests.guihandles.FlashcardListCardHandle;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.testutil.FlashcardBuilder;

public class FlashcardListCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Flashcard flashcardWithNoTags = new FlashcardBuilder().withTags().build();
        FlashcardListCard flashcardListCard = new FlashcardListCard(flashcardWithNoTags, 1);
        uiPartRule.setUiPart(flashcardListCard);
        assertCardDisplay(flashcardListCard, flashcardWithNoTags, 1);

        // with tags
        Flashcard flashcardWithTags = new FlashcardBuilder().build();
        flashcardListCard = new FlashcardListCard(flashcardWithTags, 2);
        uiPartRule.setUiPart(flashcardListCard);
        assertCardDisplay(flashcardListCard, flashcardWithTags, 2);
    }

    @Test
    public void equals() {
        Flashcard flashcard = new FlashcardBuilder().build();
        FlashcardListCard flashcardListCard = new FlashcardListCard(flashcard, 0);

        // same flashcard, same index -> returns true
        FlashcardListCard copy = new FlashcardListCard(flashcard, 0);
        assertTrue(flashcardListCard.equals(copy));

        // same object -> returns true
        assertTrue(flashcardListCard.equals(flashcardListCard));

        // null -> returns false
        assertFalse(flashcardListCard.equals(null));

        // different types -> returns false
        assertFalse(flashcardListCard.equals(0));

        // different flashcard, same index -> returns false
        Flashcard differentFlashcard = new FlashcardBuilder().withFrontFace("differentName").build();
        assertFalse(flashcardListCard.equals(new FlashcardListCard(differentFlashcard, 0)));

        // same flashcard, different index -> returns false
        assertFalse(flashcardListCard.equals(new FlashcardListCard(flashcard, 1)));
    }

    /**
     * Asserts that {@code flashcardListCard} displays the details of {@code expectedFlashcard} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(FlashcardListCard flashcardListCard, Flashcard expectedFlashcard, int expectedId) {
        guiRobot.pauseForHuman();

        FlashcardListCardHandle flashcardListCardHandle = new FlashcardListCardHandle(flashcardListCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", flashcardListCardHandle.getId());

        // verify flashcard details are displayed correctly
        assertListCardDisplaysFlashcard(expectedFlashcard, flashcardListCardHandle);
    }
}
