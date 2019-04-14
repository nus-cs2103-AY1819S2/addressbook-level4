package seedu.address.model.quiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuizCardTest {

    private static final String QUESTION = "Japan";
    private static final String ANSWER = "Tokyo";
    private static final String QUESTION_HEADER = "Country";
    private static final String ANSWER_HEADER = "Capital";
    private static final QuizMode MODE = QuizMode.PREVIEW;

    private static final List<String> HINTS = Arrays.asList("JP", "Asia");

    private static final QuizCard VALID_QUIZCARD = new QuizCard(QUESTION, ANSWER, HINTS,
        QUESTION_HEADER, ANSWER_HEADER);
    private static final QuizCard VALID_QUIZCARD_INDEX =
        VALID_QUIZCARD.generateOrderedQuizCardWithIndex(1, QuizMode.PREVIEW);

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(null, ANSWER, HINTS, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(QUESTION, null, HINTS, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(QUESTION, ANSWER, null, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(QUESTION, ANSWER, HINTS, null, ANSWER_HEADER));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(QUESTION, ANSWER, HINTS, QUESTION_HEADER, null));
    }

    @Test
    public void constructor_invalidQuizCard_throwsIllegalArgumentException() {
        String invalidQn = "";
        String invalidAns = "";
        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(invalidQn, ANSWER, HINTS, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard("     ", ANSWER, HINTS, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(QUESTION, invalidAns, HINTS, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(QUESTION, "     ", HINTS, QUESTION_HEADER, ANSWER_HEADER));

    }

    @Test
    public void getQuestion() {
        assertEquals(QUESTION, VALID_QUIZCARD.getQuestion());

        assertEquals(QUESTION, VALID_QUIZCARD_INDEX.getQuestion());
    }

    @Test
    public void getAnswer() {
        assertEquals(ANSWER, VALID_QUIZCARD.getAnswer());

        assertEquals(ANSWER, VALID_QUIZCARD_INDEX.getAnswer());
    }

    @Test
    public void getOpt() {
        assertEquals(HINTS, VALID_QUIZCARD.getOpt());
    }

    @Test
    public void getIndex() {
        assertEquals(1, VALID_QUIZCARD_INDEX.getIndex());
    }

    @Test
    public void getQuizMode() {
        assertEquals(MODE, VALID_QUIZCARD_INDEX.getQuizMode());
    }

    @Test
    public void getQuestionHeader() {
        assertEquals(QUESTION_HEADER, VALID_QUIZCARD.getQuestionHeader());
    }

    @Test
    public void getAnswerHeader() {
        assertEquals(ANSWER_HEADER, VALID_QUIZCARD.getAnswerHeader());
    }

    @Test
    public void isCorrect() {
        assertTrue(VALID_QUIZCARD.isCorrect(ANSWER));

        assertFalse(VALID_QUIZCARD.isCorrect(" not answer"));

        assertFalse(VALID_QUIZCARD.isCorrect("       "));

        Assert.assertThrows(NullPointerException.class, () ->
            VALID_QUIZCARD.isCorrect(null));
    }

    @Test
    public void isWrongTwice() {
        VALID_QUIZCARD.isCorrect("wronganswer");
        assertFalse(VALID_QUIZCARD.isWrongTwice());

        VALID_QUIZCARD.isCorrect("wrongansweragain");
        assertTrue(VALID_QUIZCARD.isWrongTwice());
    }

    @Test
    public void toggleIsCardDifficult() {
        assertFalse(VALID_QUIZCARD.isCardDifficult());

        VALID_QUIZCARD.toggleIsCardDifficult();
        assertTrue(VALID_QUIZCARD.isCardDifficult());

        VALID_QUIZCARD.toggleIsCardDifficult();
        assertFalse(VALID_QUIZCARD.isCardDifficult());
    }

    @Test
    public void updateTotalAttemptsAndStreak() {
        QuizCard quizCardWithIndex = new QuizCard(QUESTION, ANSWER, HINTS,
            QUESTION_HEADER, ANSWER_HEADER);

        quizCardWithIndex.updateTotalAttemptsAndStreak(true);
        assertEquals(1, quizCardWithIndex.getTotalAttempts());
        assertEquals(1, quizCardWithIndex.getStreak());

        quizCardWithIndex.updateTotalAttemptsAndStreak(true);
        assertEquals(2, quizCardWithIndex.getTotalAttempts());
        assertEquals(2, quizCardWithIndex.getStreak());

        quizCardWithIndex.updateTotalAttemptsAndStreak(false);
        assertEquals(3, quizCardWithIndex.getTotalAttempts());
        assertEquals(0, quizCardWithIndex.getStreak());

    }

    @Test
    public void equals() {
        final QuizCard copyValidQuizCard = new QuizCard(QUESTION, ANSWER, HINTS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard diffQuizCard = new QuizCard("A", "B", HINTS,
            QUESTION_HEADER, ANSWER_HEADER);

        // same object
        assertEquals(VALID_QUIZCARD, VALID_QUIZCARD);

        // same value
        assertEquals(VALID_QUIZCARD, copyValidQuizCard);

        // different obj
        assertNotEquals(VALID_QUIZCARD, new Quiz(Arrays.asList(VALID_QUIZCARD, VALID_QUIZCARD), QuizMode.LEARN));

        // different types
        assertNotEquals("random things", VALID_QUIZCARD);

        // different values
        assertNotEquals(VALID_QUIZCARD, diffQuizCard);

        // same value but contains index
        assertNotEquals(VALID_QUIZCARD, VALID_QUIZCARD_INDEX);
    }

    @Test
    public void hashcode() {
        final QuizCard copyValidQuizCard = new QuizCard(QUESTION, ANSWER, HINTS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard diffQuizCard = new QuizCard("A", "B", HINTS,
            QUESTION_HEADER, ANSWER_HEADER);

        // same value
        assertEquals(VALID_QUIZCARD, copyValidQuizCard);
        assertEquals(VALID_QUIZCARD.hashCode(), copyValidQuizCard.hashCode());

        // different values
        assertNotEquals(VALID_QUIZCARD.hashCode(), diffQuizCard.hashCode());

        // same value but contains index
        assertNotEquals(VALID_QUIZCARD.hashCode(), VALID_QUIZCARD_INDEX);
    }

    @Test
    public void quizCardToString() {
        final QuizCard validQuizCard = new QuizCard(QUESTION, ANSWER, HINTS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard copyValidQuizCard = new QuizCard(QUESTION, ANSWER, HINTS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard diffQuizCard = new QuizCard("A", "B", HINTS,
            QUESTION_HEADER, ANSWER_HEADER);

        assertEquals(validQuizCard.toString(), copyValidQuizCard.toString());
        assertNotEquals(validQuizCard.toString(), diffQuizCard.toString());
        assertNotEquals(validQuizCard.toString(), VALID_QUIZCARD_INDEX.toString());
    }

}

