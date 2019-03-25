package seedu.address.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;

public class CardSrsDataTest {

    private Instant date = Instant.now();
    private CardSrsData testCardData = new CardSrsData(1, 1, 1, date);

    @Test
    public void constructor_invalidHashCode_throwsIllegalArgumentException(){

      //  Assert.assertThrows(IllegalArgumentException.class, () -> )

    }
    @Test
    public void equals() {
        //Different type of object -> returns false
        assertFalse(testCardData.equals(new Object()));

        //Same type of object -> returns true
        assertTrue(testCardData.equals(testCardData));
    }
}
