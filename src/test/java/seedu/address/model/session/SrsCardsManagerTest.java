package seedu.address.model.session;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.SrsCardBuilder.DEFAULT_CARDSRSDATA;
import static seedu.address.testutil.TypicalCards.CARD_BELGIUM;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.user.CardSrsData;
import seedu.address.testutil.Assert;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.SrsCardBuilder;

public class SrsCardsManagerTest {
    private Lesson lesson = new LessonBuilder().build();
    private HashMap<Integer, CardSrsData> cardData = new HashMap<>();
    private List<List<Integer>> quizInformation = new ArrayList<>();
    private List<SrsCard> srsCards = List.of(new SrsCardBuilder().build(),
            new SrsCardBuilder(new SrsCard(CARD_JAPAN, new CardSrsData(CARD_JAPAN.hashCode(), 1, 1,
                    Instant.ofEpochMilli(1233)), lesson)).build());

    @Test
    public void constructor_sort_throwsNullPointerException() {
        cardData.put(CARD_BELGIUM.hashCode(), DEFAULT_CARDSRSDATA);
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCardsManager(lesson, null));
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCardsManager(null, cardData));
    }
    @Test
    public void constructor_update_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCardsManager(null, quizInformation));
        Assert.assertThrows(NullPointerException.class, () ->
                new SrsCardsManager(srsCards, null));
    }
    @Test
    public void checkSort() {
        cardData.put(CARD_BELGIUM.hashCode(), DEFAULT_CARDSRSDATA);
        CardSrsData cardSrsDataJapan = new CardSrsData(CARD_JAPAN.hashCode(), 1, 1,
                Instant.ofEpochMilli(1233));
        cardData.put(CARD_JAPAN.hashCode(), cardSrsDataJapan);
        SrsCardsManager currentManager = new SrsCardsManager(lesson, cardData);
        List<SrsCard> srsCards = currentManager.sort();
        List<SrsCard> expected = new ArrayList<>();
        expected.add(new SrsCardBuilder().build());
        expected.add(new SrsCard(CARD_JAPAN, cardSrsDataJapan, lesson));
        assertEquals(expected, srsCards);
    }
    @Test
    public void checkSortReverse() {
        cardData.put(CARD_BELGIUM.hashCode(), DEFAULT_CARDSRSDATA);
        CardSrsData cardSrsDataJapan = new CardSrsData(CARD_JAPAN.hashCode(), 1, 1,
                Instant.ofEpochMilli(1233));
        cardData.put(CARD_JAPAN.hashCode(), cardSrsDataJapan);
        SrsCardsManager currentManager = new SrsCardsManager(new LessonBuilder(
                new Lesson("Capitals", List.of("Country", "Capital"), List.of("Hint"), 0,
                        1, List.of(CARD_JAPAN, CARD_BELGIUM))).build(),
                        cardData);
        List<SrsCard> srsCards = currentManager.sort();
        List<SrsCard> expected = new ArrayList<>();
        expected.add(new SrsCardBuilder().build());
        expected.add(new SrsCard(CARD_JAPAN, cardSrsDataJapan, lesson));
        assertEquals(expected, srsCards);
    }
    /*@Test
    public void checkUpdate() {
        quizInformation.add(List.of(0, 1, 1));
        quizInformation.add(List.of(1, 1, 0));
        SrsCardsManager currentManager = new SrsCardsManager(srsCards, quizInformation);
        List<CardSrsData> cardData = currentManager.updateCardData();
        List<CardSrsData> expected = new ArrayList<>();
        expected.add(new CardSrsData(CARD_BELGIUM.hashCode(), 2, 2, Instant.ofEpochMilli(123)
                .plus(Duration.ofHours(1))));
        expected.add(new CardSrsData(CARD_JAPAN.hashCode(), 2, 1, Instant.ofEpochMilli(1233)
                .plus(Duration.ofHours())));
        assertEquals(expected, cardData);
    }*/
}
