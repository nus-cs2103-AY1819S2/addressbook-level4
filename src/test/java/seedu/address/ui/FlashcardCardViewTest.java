package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardViewDisplaysFlashcard;

import org.junit.Test;

import guitests.guihandles.FlashcardCardViewHandle;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.testutil.FlashcardBuilder;

public class FlashcardCardViewTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Flashcard flashcardWithNoTags = new FlashcardBuilder().withTags().build();
        FlashcardCardView flashcardCardView = new FlashcardCardView(flashcardWithNoTags);
        uiPartRule.setUiPart(flashcardCardView);
        assertCardDisplay(flashcardCardView, flashcardWithNoTags);

        // with tags
        Flashcard flashcardWithTags = new FlashcardBuilder().build();
        flashcardCardView = new FlashcardCardView(flashcardWithTags);
        uiPartRule.setUiPart(flashcardCardView);
        assertCardDisplay(flashcardCardView, flashcardWithTags);
    }

    @Test
    public void equals() {
        Flashcard flashcard = new FlashcardBuilder().build();
        FlashcardCardView flashcardCardView = new FlashcardCardView(flashcard);

        // same flashcard -> returns true
        FlashcardCardView copy = new FlashcardCardView(flashcard);

        assertTrue(flashcardCardView.equals(copy));

        // same object -> returns true
        assertTrue(flashcardCardView.equals(flashcardCardView));

        // null -> returns false
        assertFalse(flashcardCardView.equals(null));

        // different types -> returns false
        assertFalse(flashcardCardView.equals(0));

        // same flashcard, different tags -> returns true
        Flashcard flashCardNoTag = new FlashcardBuilder().withTags().build();
        assertTrue(flashcardCardView.equals(new FlashcardCardView(flashCardNoTag)));
    }

    /**
     * Asserts that {@code flashcardCardView} displays the details of {@code expectedFlashcard} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(FlashcardCardView flashcardCardView, Flashcard expectedFlashcard) {
        guiRobot.pauseForHuman();

        FlashcardCardViewHandle flashcardCardViewHandle = new FlashcardCardViewHandle(flashcardCardView.getRoot());

        // verify flashcard details are displayed correctly
        assertCardViewDisplaysFlashcard(expectedFlashcard, flashcardCardViewHandle);
    }
}
