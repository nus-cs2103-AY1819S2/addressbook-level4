package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCard;

import org.junit.Test;

import guitests.guihandles.FlashcardCardHandle;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.TypicalCards;

public class FlashcardCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Card cardWithNotOptionals = new CardBuilder().build();
        FlashcardCard cardCard = new FlashcardCard(cardWithNotOptionals, 1, 0, 1);
        uiPartRule.setUiPart(cardCard);
        assertCardDisplay(cardCard, cardWithNotOptionals, 1);
    }

    @Test
    public void equals() {
        Card card = TypicalCards.CARD_MULTI;
        FlashcardCard cardCard = new FlashcardCard(card, 0, 0, 1);

        // same card, same index -> returns true
        FlashcardCard copy = new FlashcardCard(card, 0, 0, 1);
        assertEquals(cardCard, copy);

        // same object -> returns true
        assertEquals(cardCard, cardCard);

        // null -> returns false
        assertNotEquals(cardCard, null);

        // different types -> returns false
        assertNotEquals(cardCard, new Object());

        // same card with same index but different index -> returns true
        Card differentCard = new CardBuilder(card).build();
        assertEquals(cardCard, new FlashcardCard(differentCard, 0, 0, 1));
    }

    /**
     * Asserts that {@code cardCard} displays the details of {@code expectedCard} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(FlashcardCard cardCard, Card expectedCard, int expectedId) {
        guiRobot.pauseForHuman();

        FlashcardCardHandle cardCardHandle = new FlashcardCardHandle(cardCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", cardCardHandle.getId());

        // verify card details are displayed correctly
        assertCardDisplaysCard(expectedCard, cardCardHandle);
    }
}
