package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCard;

import guitests.guihandles.CardThumbnailHandle;
import org.junit.Test;

import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

public class CardThumbnailTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Card cardWithNoTags = new CardBuilder().withTags(new String[0]).build();
        CardThumbnail cardThumbnail = new CardThumbnail(cardWithNoTags, 1);
        uiPartRule.setUiPart(cardThumbnail);
        assertCardDisplay(cardThumbnail, cardWithNoTags, 1);

        // with tags
        Card cardWithTags = new CardBuilder().build();
        cardThumbnail = new CardThumbnail(cardWithTags, 2);
        uiPartRule.setUiPart(cardThumbnail);
        assertCardDisplay(cardThumbnail, cardWithTags, 2);
    }

    @Test
    public void equals() {
        Card card = new CardBuilder().build();
        CardThumbnail cardThumbnail = new CardThumbnail(card, 0);

        // same card, same index -> returns true
        CardThumbnail copy = new CardThumbnail(card, 0);
        assertTrue(cardThumbnail.equals(copy));

        // same object -> returns true
        assertTrue(cardThumbnail.equals(cardThumbnail));

        // null -> returns false
        assertFalse(cardThumbnail.equals(null));

        // different types -> returns false
        assertFalse(cardThumbnail.equals(0));

        // different card, same index -> returns false
        Card differentCard = new CardBuilder().withName("differentName").build();
        assertFalse(cardThumbnail.equals(new CardThumbnail(differentCard, 0)));

        // same card, different index -> returns false
        assertFalse(cardThumbnail.equals(new CardThumbnail(card, 1)));
    }

    /**
     * Asserts that {@code cardThumbnail} displays the details of {@code expectedCard} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CardThumbnail cardThumbnail, Card expectedCard, int expectedId) {
        guiRobot.pauseForHuman();

        CardThumbnailHandle cardThumbnailHandle = new CardThumbnailHandle(cardThumbnail.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", cardThumbnailHandle.getId());

        // verify card details are displayed correctly
        assertCardDisplaysCard(expectedCard, cardThumbnailHandle);
    }
}
