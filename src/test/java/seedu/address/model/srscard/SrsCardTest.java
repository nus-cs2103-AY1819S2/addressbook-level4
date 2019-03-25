package seedu.address.model.srscard;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.SrsCardBuilder.DEFAULT_CARDSRSDATA;
import static seedu.address.testutil.TypicalCards.CARD_BELGIUM;

import java.time.Instant;

import org.junit.Test;

import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.user.CardSrsData;
import seedu.address.testutil.Assert;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.SrsCardBuilder;

public class SrsCardTest {
    private static final Card card = CARD_BELGIUM;
    private static final CardSrsData cardData = DEFAULT_CARDSRSDATA;
    private static final Lesson lesson = new LessonBuilder().build();
    @Test
    public void constructor_invalidInput_throwsIllegalArgumentException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCard(null, null, null));
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCard(card, null, null));
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCard(null, null, lesson));
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCard(card, null, lesson));
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCard(null, cardData, null));
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCard(card, cardData, null));
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCard(null, cardData, lesson));
    }

    @Test
    public void fieldsGetter() {
        SrsCard currentSrsCard = new SrsCardBuilder().build();
        assertEquals(CARD_BELGIUM, currentSrsCard.getCard());
        assertEquals(DEFAULT_CARDSRSDATA, currentSrsCard.getCardData());
        assertEquals(new LessonBuilder().build(), currentSrsCard.getLesson());
    }
    @Test
    public void checkCardData() {
        SrsCard currentSrsCard = new SrsCardBuilder().build();
        //assertEquals(CARD_BELGIUM.hashCode(), currentSrsCard.getHashcode());
        assertEquals(1, currentSrsCard.getNumOfAttempts());
        assertEquals(1, currentSrsCard.getStreak());
        assertEquals(Instant.ofEpochMilli(123), currentSrsCard.getSrsDueDate());
    }
}
