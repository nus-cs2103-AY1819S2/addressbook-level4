package seedu.address.model.session;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCards.CARD_BELGIUM;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;
import seedu.address.testutil.Assert;
import seedu.address.testutil.SessionBuilder;
import seedu.address.testutil.SrsCardBuilder;

public class SessionTest {
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_with_name())
                        .withName("").build_without_srsCards());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build()).withName("").build_with_name());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_without_count()).withName("").build_without_count());
    }

    @Test
    public void constructor_invalidMode_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build()).withMode(null).build());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build()).withMode(null).buildIndex_without_srsCards());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_without_srsCards())
                        .withMode(null).build_without_srsCards());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build()).withMode(null).build_with_name());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_without_count()).withMode(null).build_without_count());
    }

    @Test
    public void constructor_invalidCount_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build()).withCount(0).build());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build()).withCount(0).build_with_name());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().buildIndex_without_srsCards())
                        .withCount(0).buildIndex_without_srsCards());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_without_srsCards())
                        .withCount(0).build_without_srsCards());
    }

    @Test
    public void generateSession() {
        SrsCard currentSrsCard = new SrsCardBuilder().build();
        Session newSession = new Session("Capitals", 1, QuizMode.LEARN, List.of(currentSrsCard));
        List<QuizCard> quizCards = newSession.generateSession();
        assertEquals(quizCards.get(0).getQuestion(), CARD_BELGIUM.getCore(0));
        assertEquals(quizCards.get(0).getAnswer(), CARD_BELGIUM.getCore(1));
        newSession.setCount(15);
        assertEquals(15, newSession.getCount());
        newSession.setCount(1);
        assertEquals(currentSrsCard.getHashcode(), newSession.getQuizSrsCards().get(0).getHashcode());
    }

    @Test
    public void updateUser() {
        SrsCard currentSrsCard = new SrsCardBuilder().build();
        Session newSession = new Session("Capitals", 1, QuizMode.LEARN, List.of(currentSrsCard));
        List<List<Integer>> quizInformation = new ArrayList<>();
        quizInformation.add(List.of(0, 1, 1, 0));
        CardSrsData actual = new CardSrsData(currentSrsCard.getHashcode(), 2, 2,
                Instant.ofEpochMilli(123).plus(Duration.ofHours(5)), false);
        assertEquals(newSession.updateUserProfile(quizInformation).get(0).getHashCode(), actual.getHashCode());
    }
}
