package braintrain.model.quiz;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import braintrain.testutil.Assert;

public class QuizCardTest {

    private static final String QUESTION = "Japan";
    private static final String ANSWER = "Tokyo";

    private static final List<String> FIELDS_OPTIONALS = Arrays.asList("JP", "Asia");
    private static final List<String> FIELDS_OPTIONALS_EMPTY = Arrays.asList("", "");

    private static final QuizCard VALID_QUIZCARD_NO_OPT = new QuizCard(QUESTION, ANSWER);
    private static final QuizCard VALID_QUIZCARD = new QuizCard(QUESTION, ANSWER, FIELDS_OPTIONALS);
    private static final QuizCard VALID_QUIZCARD_INDEX = new QuizCard(1, QUESTION, ANSWER);

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
            new QuizCard(0, null, null));

        Assert.assertThrows(NullPointerException.class, () ->
            new QuizCard(0, QUESTION, null));
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
            new QuizCard(0, invalidQn, invalidAns));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(1, "     ", ANSWER));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new QuizCard(2, invalidQn, ANSWER));

    }

    @Test
    public void getQuestion() {
        assertEquals(QUESTION, VALID_QUIZCARD.getQuestion());

        assertEquals(QUESTION, VALID_QUIZCARD_NO_OPT.getQuestion());

        assertEquals(QUESTION, VALID_QUIZCARD_INDEX.getQuestion());
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

}
