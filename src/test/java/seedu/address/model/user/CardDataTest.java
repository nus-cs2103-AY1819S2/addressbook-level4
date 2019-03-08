package braintrain.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;

public class CardDataTest {
    private Instant instant = Instant.ofEpochMilli(123);
    private CardData cardData = new CardData(1, 1, 1, instant );

    @Test
    public void equals() {
        //Different type of object -> returns false
        assertFalse(cardData.equals(new Object()));

        //Same type of object -> returns true
        assertTrue(cardData.equals(cardData));

    }
}
