package seedu.address.model.session;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCards.CARD_BELGIUM;

import java.util.List;

import org.junit.Test;

import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.srscard.SrsCard;
import seedu.address.testutil.Assert;
import seedu.address.testutil.SessionBuilder;
import seedu.address.testutil.SrsCardBuilder;

public class SessionTest {
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build()).withName("").build());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_without_srsCards())
                        .withName("").build_without_srsCards());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_without_count()).withName("").build_without_count());
    }

    @Test
    public void constructor_invalidMode_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build()).withMode(null).build());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_without_srsCards())
                        .withMode(null).build_without_srsCards());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_without_count()).withMode(null).build_without_count());
    }

    @Test
    public void constructor_invalidCount_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build()).withCount(0).build());
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new SessionBuilder(new SessionBuilder().build_without_srsCards())
                        .withCount(0).build_without_srsCards());
    }

    @Test
    public void generateSession() {
        SrsCard currentSrsCard = new SrsCardBuilder().build();
        Session newSession = new Session("01-01-Learn", 1, Quiz.Mode.LEARN, List.of(currentSrsCard));
        List<QuizCard> quizCards = newSession.generateSession();
        assertEquals(quizCards.get(0).getQuestion(), CARD_BELGIUM.getCore(0));
        assertEquals(quizCards.get(0).getAnswer(), CARD_BELGIUM.getCore(1));
    }
}
