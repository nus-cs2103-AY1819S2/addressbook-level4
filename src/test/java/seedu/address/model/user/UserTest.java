package seedu.address.model.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.Instant;
import java.util.HashMap;

import org.junit.Test;

public class UserTest {
    private User user = new User();
    private Instant date = Instant.now();
    private final CardSrsData testCardData = new CardSrsData(1, 1, 1, date);
    private final HashMap<Integer, CardSrsData> testHashMap = new HashMap<>() {
        {
            put(1, testCardData);
        }
    };

    @Test
    public void addCard() {
        user.addCard(testCardData);
        CardSrsData expectedOutput = user.getCard(testCardData.getHashCode());
        assertEquals(testCardData, expectedOutput);
    }

    @Test
    public void deleteCard() {
        user.deleteCard(testCardData);
        CardSrsData expectedOutput = user.getCard(testCardData.getHashCode());
        assertNotEquals(testCardData, expectedOutput);
    }

    @Test
    public void setCard() {
        user.setCard(testCardData);
        CardSrsData expectedOutput = user.getCard(testCardData.getHashCode());
        assertEquals(testCardData, expectedOutput);
    }

    @Test
    public void getCard() {
        user.addCard(testCardData);
        CardSrsData expectedOutput = user.getCard(testCardData.getHashCode());
        assertEquals(testCardData.getHashCode(), expectedOutput.getHashCode());
    }

    @Test
    public void getCards() {
        HashMap<Integer, CardSrsData> expectedOutput = new HashMap<>();
        expectedOutput.put(1, testCardData);
        testHashMap.put(testCardData.getHashCode(), testCardData);
        assertEquals(testHashMap, expectedOutput);
    }
}
