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

import braintrain.testutil.CardBuilder;

public class CardTest {
    @Test
    public void equals() {
        // different type of object -> return false
        assertFalse(BELGIUM.equals(new Object()));

        // same object -> returns true
        assertTrue(BELGIUM.equals(BELGIUM));

        // different object -> returns false
        assertFalse(BELGIUM.equals(JAPAN));

        // same cores and optionals -> returns true
        Card belgiumCopy = new CardBuilder(BELGIUM).build();
        assertTrue(BELGIUM.equals(belgiumCopy));

        // same cores with modified optionals -> returns false
        Card modifiedCopy = new CardBuilder(JAPAN).withOptionals("Same characters as Kyoto").build();
        assertFalse(JAPAN.equals(modifiedCopy));

        // modify existing card to have same cores and optionals as another card -> returns true
        modifiedCopy = new CardBuilder(BELGIUM)
                .withCores(JAPAN_QUESTION, JAPAN_ANSWER)
                .withOptionals(JAPAN_HINT).build();
        assertTrue(JAPAN.equals(modifiedCopy));
    }

    @Test
    public void setAndGetCoresAndOptionals() {
        Card belgiumCopy = new CardBuilder(BELGIUM).build();
        Card japanCopy = new CardBuilder(JAPAN).build();
        assertNotEquals(belgiumCopy, japanCopy);

        belgiumCopy.setCores(japanCopy.getCores());
        assertNotEquals(belgiumCopy, japanCopy);

        belgiumCopy.setOptionals(japanCopy.getOptionals());
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
}
