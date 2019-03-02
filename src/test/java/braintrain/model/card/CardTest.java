package braintrain.model.card;

import static braintrain.testutil.TypicalCards.BELGIUM;
import static braintrain.testutil.TypicalCards.JAPAN;
import static braintrain.testutil.TypicalCards.JAPAN_ANSWER;
import static braintrain.testutil.TypicalCards.JAPAN_HINT;
import static braintrain.testutil.TypicalCards.JAPAN_QUESTION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import braintrain.model.card.exceptions.MissingCoreException;
import braintrain.model.card.exceptions.MissingOptionalException;
import braintrain.testutil.Assert;
import braintrain.testutil.CardBuilder;

/**
 * Tests the {@code Card} object (100% coverage).
 */
public class CardTest {
    @Test
    public void equals() {
        // Different type of object -> return false
        assertFalse(BELGIUM.equals(new Object()));

        // Same object -> returns true
        assertTrue(BELGIUM.equals(BELGIUM));

        // Different object -> returns false
        assertFalse(BELGIUM.equals(JAPAN));

        // Same cores and optionals -> returns true
        Card belgiumCopy = new CardBuilder(BELGIUM).build();
        assertTrue(BELGIUM.equals(belgiumCopy));

        // Same cores with modified optionals -> returns false
        Card modifiedCopy = new CardBuilder(JAPAN).withOptionals("Same characters as Kyoto").build();
        assertFalse(JAPAN.equals(modifiedCopy));

        // Modify existing card to have same cores and optionals as another card -> returns true
        modifiedCopy = new CardBuilder(BELGIUM)
                .withCores(JAPAN_QUESTION, JAPAN_ANSWER)
                .withOptionals(JAPAN_HINT).build();
        assertTrue(JAPAN.equals(modifiedCopy));

        // Same cores and optionals but different order for cores -> returns false
        modifiedCopy = new CardBuilder(JAPAN).withCores(JAPAN_ANSWER, JAPAN_QUESTION).build();
        assertFalse(JAPAN.equals(modifiedCopy));
    }

    @Test
    public void setAndGetCoresAndOptionals() {
        Card belgiumCopy = new CardBuilder(BELGIUM).build();
        Card japanCopy = new CardBuilder(JAPAN).build();
        // These two cards have different cores and optionals and should not be equal.
        assertNotEquals(belgiumCopy, japanCopy);

        belgiumCopy.setCores(japanCopy.getCores());
        // Despite having the same cores, the two cards still have different optionals.
        assertNotEquals(belgiumCopy, japanCopy);

        belgiumCopy.setOptionals(japanCopy.getOptionals());
        // Both cards have the same cores and optionals, and should be treated as equivalents.
        assertEquals(belgiumCopy, japanCopy);
    }

    @Test
    public void setAndGetCoreAndOptional() {
        Card belgiumCopy = new CardBuilder(BELGIUM).build();
        Card japanCopy = new CardBuilder(JAPAN).build();
        assertNotEquals(belgiumCopy, japanCopy);

        belgiumCopy.setCore(0, japanCopy.getCore(0));
        assertEquals(belgiumCopy.getCore(0), japanCopy.getCore(0));

        belgiumCopy.setOptional(0, japanCopy.getOptional(0));
        assertEquals(belgiumCopy.getOptional(0), japanCopy.getOptional(0));
    }

    @Test
    public void cardToString() {
        Card belgiumCopy = new CardBuilder(BELGIUM).build();
        Card newCard = new Card(belgiumCopy.getCores(), belgiumCopy.getOptionals());
        // newCard should be a copy of belgiumCopy
        assertEquals(belgiumCopy, newCard);
        // since both cards are identical, their string representation should be the same
        assertEquals(belgiumCopy.toString(), newCard.toString());
    }

    @Test
    public void getCore_invalidIndex_throwsMissingCoreException() {
        Assert.assertThrows(MissingCoreException.class, MissingCoreException.generateMessage(0), () -> {
            Card testCard = new CardBuilder(JAPAN).build();
            testCard.setCore(0, "");
            testCard.getCore(0);
        });
    }

    @Test
    public void getOptional_invalidIndex_throwsMissingOptionalException() {
        Assert.assertThrows(MissingOptionalException.class, MissingOptionalException.generateMessage(0), () -> {
            Card testCard = new CardBuilder(JAPAN).build();
            testCard.setOptional(0, "");
            testCard.getOptional(0);
        });
    }
}
