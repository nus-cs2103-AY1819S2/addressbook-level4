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

    private static final List<String> FIELDS_OPTIONALS = Arrays.asList("JP", "Asia");
    private static final List<String> FIELDS_OPTIONALS_EMPTY = Arrays.asList("", "");

    private static final QuizCard VALID_QUIZCARD_NO_OPT = new QuizCard(QUESTION, ANSWER);
    private static final QuizCard VALID_QUIZCARD = new QuizCard(QUESTION, ANSWER, FIELDS_OPTIONALS);
    private static final QuizCard VALID_QUIZCARD_INDEX = new QuizCard(1, QUESTION, ANSWER, QuizMode.PREVIEW);
    private static final QuizCard VALID_QUIZCARD_HEADER = new QuizCard(QUESTION, ANSWER, FIELDS_OPTIONALS,
        QUESTION_HEADER, ANSWER_HEADER);
    private static final QuizCard VALID_QUIZCARD_HEADER_NO_OPT = new QuizCard(QUESTION, ANSWER,
        QUESTION_HEADER, ANSWER_HEADER);
    private static final QuizCard VALID_QUIZCARD_HEADER_INDEX = new QuizCard(1, QUESTION, ANSWER, QuizMode.PREVIEW,
        QUESTION_HEADER, ANSWER_HEADER);

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(null, null));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(QUESTION, null));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(null, null, null));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(QUESTION, null, FIELDS_OPTIONALS));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(QUESTION, ANSWER, null));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(0, null, null, MODE));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(0, QUESTION, null, MODE));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(QUESTION, ANSWER, FIELDS_OPTIONALS, QUESTION_HEADER, null));
    }

    @Test
    public void constructor_invalidQuizCard_throwsIllegalArgumentException() {
        String invalidQn = "";
        String invalidAns = "";
        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(invalidQn, invalidAns));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard("     ", ANSWER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(invalidQn, ANSWER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(invalidQn, invalidAns, FIELDS_OPTIONALS_EMPTY));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(invalidQn, ANSWER, FIELDS_OPTIONALS_EMPTY));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard("     ", invalidAns, FIELDS_OPTIONALS_EMPTY));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(QUESTION, invalidAns, FIELDS_OPTIONALS_EMPTY));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(0, invalidQn, invalidAns, MODE));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(1, "     ", ANSWER, MODE));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(2, invalidQn, ANSWER, MODE));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(2, invalidQn, ANSWER, MODE, QUESTION_HEADER, ANSWER_HEADER));

    }

    @Test
    public void getQuestion() {
        assertEquals(QUESTION, VALID_QUIZCARD.getQuestion());

        assertEquals(QUESTION, VALID_QUIZCARD_NO_OPT.getQuestion());

        assertEquals(QUESTION, VALID_QUIZCARD_INDEX.getQuestion());

        assertEquals(QUESTION, VALID_QUIZCARD_HEADER_INDEX.getQuestion());
    }

    @Test
    public void getAnswer() {
        assertEquals(ANSWER, VALID_QUIZCARD.getAnswer());

        assertEquals(ANSWER, VALID_QUIZCARD_NO_OPT.getAnswer());

        assertEquals(ANSWER, VALID_QUIZCARD_INDEX.getAnswer());
    }

    @Test
    public void getOpt() {
        assertEquals(FIELDS_OPTIONALS, VALID_QUIZCARD.getOpt());

        assertEquals(null, VALID_QUIZCARD_NO_OPT.getOpt());

        assertEquals(null, VALID_QUIZCARD_INDEX.getOpt());
    }

    @Test
    public void getIndex() {
        assertEquals(1, VALID_QUIZCARD_INDEX.getIndex());
    }

    @Test
    public void getIndex_invalidIndex_throwsAssertionError() {
        // do not contain actual index.
        Assert.assertThrows(AssertionError.class, () ->
            VALID_QUIZCARD.getIndex());
    }

    @Test
    public void getQuizMode_invalidQuizMode_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            VALID_QUIZCARD.getQuizMode());
    }

    @Test
    public void getQuizMode() {
        assertEquals(MODE, VALID_QUIZCARD_INDEX.getQuizMode());
    }

    @Test
    public void getQuestionHeader() {
        assertEquals(QUESTION_HEADER, VALID_QUIZCARD_HEADER.getQuestionHeader());

        assertEquals(QUESTION_HEADER, VALID_QUIZCARD_HEADER_NO_OPT.getQuestionHeader());
    }

    @Test
    public void getAnswerHeader() {
        assertEquals(ANSWER_HEADER, VALID_QUIZCARD_HEADER.getAnswerHeader());
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
        QuizCard quizCardWithIndex = VALID_QUIZCARD;

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
        final QuizCard anotherValidQuizCard = new QuizCard(QUESTION, ANSWER);
        final QuizCard quizCardWithAb = new QuizCard("A", "B");
        final QuizCard cardWithIndex = new QuizCard(0, QUESTION, ANSWER, MODE);

        // same object
        assertEquals(VALID_QUIZCARD, VALID_QUIZCARD);

        // same value
        assertEquals(VALID_QUIZCARD_NO_OPT, anotherValidQuizCard);

        // different obj
        assertNotEquals(VALID_QUIZCARD, new Quiz(Arrays.asList(VALID_QUIZCARD, VALID_QUIZCARD), QuizMode.LEARN));

        // different types
        assertNotEquals("random things", VALID_QUIZCARD);

        // different values
        assertNotEquals(VALID_QUIZCARD, quizCardWithAb);

        // same value but contains index
        assertNotEquals(VALID_QUIZCARD, cardWithIndex);
    }

    @Test
    public void hashcode() {
        final QuizCard anotherValidQuizCard = new QuizCard(QUESTION, ANSWER);
        final QuizCard quizCardWithAb = new QuizCard("A", "B");
        final QuizCard cardWithIndex = new QuizCard(0, QUESTION, ANSWER, MODE);

        // same value
        assertEquals(VALID_QUIZCARD_NO_OPT.hashCode(), anotherValidQuizCard.hashCode());

        // different values
        assertNotEquals(VALID_QUIZCARD.hashCode(), quizCardWithAb.hashCode());

        // same value but contains index
        assertNotEquals(VALID_QUIZCARD.hashCode(), cardWithIndex);
    }

    @Test
    public void quizCardToString() {
        final QuizCard anotherValidQuizCard = new QuizCard(QUESTION, ANSWER);
        final QuizCard quizCardWithAb = new QuizCard("A", "B");

        assertEquals(VALID_QUIZCARD_NO_OPT.toString(), anotherValidQuizCard.toString());
        assertNotEquals(VALID_QUIZCARD_NO_OPT.toString(), quizCardWithAb.toString());
    }

}
