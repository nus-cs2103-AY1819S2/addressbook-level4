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

    private static final QuizCard VALID_QUIZCARD = new QuizCard(QUESTION, ANSWER, FIELDS_OPTIONALS,
        QUESTION_HEADER, ANSWER_HEADER);
    private static final QuizCard VALID_QUIZCARD_INDEX = new QuizCard(1, QUESTION, ANSWER, QuizMode.PREVIEW);

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(null, null, null, null, null));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(null, null, null, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(0, null, null, MODE));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(0, QUESTION, null, MODE));

    }

    @Test
    public void constructor_invalidQuizCard_throwsIllegalArgumentException() {
        String invalidQn = "";
        String invalidAns = "";
        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(invalidQn, ANSWER, FIELDS_OPTIONALS, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard("     ", ANSWER, FIELDS_OPTIONALS, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(QUESTION, invalidAns, FIELDS_OPTIONALS, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(QUESTION, "     ", FIELDS_OPTIONALS, QUESTION_HEADER, ANSWER_HEADER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(0, invalidQn, invalidAns, MODE));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(1, "     ", ANSWER, MODE));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(2, invalidQn, ANSWER, MODE));

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
        assertEquals(FIELDS_OPTIONALS, VALID_QUIZCARD.getOpt());

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
    public void getQuestionHeader_invalidIndex_throwAssertionError() {
        Assert.assertThrows(AssertionError.class, VALID_QUIZCARD_INDEX::getQuestionHeader);
    }

    @Test
    public void getQuestionHeader() {
        assertEquals(QUESTION_HEADER, VALID_QUIZCARD.getQuestionHeader());
    }

    @Test
    public void getAnswerHeader_invalidIndex_throwAssertionError() {
        Assert.assertThrows(AssertionError.class, VALID_QUIZCARD_INDEX::getAnswerHeader);
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
        QuizCard quizCardWithIndex = new QuizCard(QUESTION, ANSWER, FIELDS_OPTIONALS,
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
        final QuizCard copyValidQuizCard = new QuizCard(QUESTION, ANSWER, FIELDS_OPTIONALS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard diffQuizCard = new QuizCard("A", "B", FIELDS_OPTIONALS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard cardWithIndex = new QuizCard(0, QUESTION, ANSWER, MODE);

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
        assertNotEquals(VALID_QUIZCARD, cardWithIndex);
    }

    @Test
    public void hashcode() {
        final QuizCard copyValidQuizCard = new QuizCard(QUESTION, ANSWER, FIELDS_OPTIONALS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard diffQuizCard = new QuizCard("A", "B", FIELDS_OPTIONALS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard cardWithIndex = new QuizCard(0, QUESTION, ANSWER, MODE);

        // same value
        assertEquals(VALID_QUIZCARD, copyValidQuizCard);
        assertEquals(VALID_QUIZCARD.hashCode(), copyValidQuizCard.hashCode());

        // different values
        assertNotEquals(VALID_QUIZCARD.hashCode(), diffQuizCard.hashCode());

        // same value but contains index
        assertNotEquals(VALID_QUIZCARD.hashCode(), cardWithIndex);
    }

    @Test
    public void quizCardToString() {
        final QuizCard copyValidQuizCard = new QuizCard(QUESTION, ANSWER, FIELDS_OPTIONALS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard diffQuizCard = new QuizCard("A", "B", FIELDS_OPTIONALS,
            QUESTION_HEADER, ANSWER_HEADER);
        final QuizCard cardWithIndex = new QuizCard(0, QUESTION, ANSWER, MODE);

        assertEquals(VALID_QUIZCARD.toString(), copyValidQuizCard.toString());
        assertNotEquals(VALID_QUIZCARD.toString(), diffQuizCard.toString());
        assertNotEquals(VALID_QUIZCARD.toString(), cardWithIndex.toString());
    }

}
