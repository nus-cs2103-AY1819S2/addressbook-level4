package seedu.address.model.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;

public class CardSrsDataTest {

    private Instant date = Instant.now();
    private CardSrsData testCardData = new CardSrsData(1, 1, 1, date, true);
    private CardSrsData expectedCardData = new CardSrsData(1, 1, 1, date, true);
    private CardSrsData card = new CardSrsData(1, 1, 1, date, true);
    private int outCardInt;
    private String outCardString;

    @Test
    public void setAndGetCardTest() {
        card.setCard(testCardData.getHashCode(), testCardData);
        CardSrsData outCard = card.getCard(1);

        assertEquals(expectedCardData, outCard);
    }

    @Test
    public void getHashCodeTest() {
        outCardInt = card.getHashCode();

        assertEquals(expectedCardData.getHashCode(), outCardInt);
    }

    @Test
    public void setAndGetNumOfAttemptsTest() {
        card.setNumOfAttempts(testCardData.getNumOfAttempts());
        outCardInt = card.getNumOfAttempts();

        assertEquals(expectedCardData.getNumOfAttempts(), outCardInt);
    }

    @Test
    public void setAndGetStreakTest() {
        card.setStreak(testCardData.getStreak());
        outCardInt = card.getStreak();

        assertEquals(expectedCardData.getStreak(), outCardInt);
    }


    @Test
    public void getSrsDueDateTest() {
        outCardString = testCardData.getSrsDueDate().toString();
        assertEquals(expectedCardData.getSrsDueDate().toString(), outCardString);
    }
    @Test
    public void equalsTest() {
        CardSrsData card1 = new CardSrsData(1, 1, 1, date, true);
        CardSrsData card2 = new CardSrsData(1, 1, 1, date, true);
        CardSrsData card3 = new CardSrsData(20, 20, 20, date, false);

        assertTrue("Card is the same", card1.equals(card2));
        assertFalse("Card is not the same", card3.equals(card1));
    }
}
