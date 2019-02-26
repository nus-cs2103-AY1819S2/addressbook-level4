package braintrain.model.quiz;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import braintrain.testutil.Assert;

public class QuizTest {
    private static final QuizCard QUIZCARD_1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
    private static final QuizCard QUIZCARD_2 = new QuizCard("Hungary", "Budapest");
    private static final List<QuizCard> VALID_QUIZCARD = Arrays.asList(QUIZCARD_1, QUIZCARD_2);

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            new Quiz(null, Quiz.Mode.LEARN));
    }

    @Test
    public void constructor_invalidQuizCard_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
            new Quiz(VALID_QUIZCARD, null));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new Quiz(Arrays.asList(new QuizCard("", "")), Quiz.Mode.LEARN));
    }

    @Test
    public void generate() {
        // learn
        List<QuizCard> expected = new ArrayList<>();
        QuizCard actualCurrentCard;
        for (int i = 0; i < VALID_QUIZCARD.size(); i++) {
            actualCurrentCard = VALID_QUIZCARD.get(i);
            expected.add(new QuizCard(i, actualCurrentCard.getQuestion(), actualCurrentCard.getAnswer()));
        }

        for (int i = 0; i < VALID_QUIZCARD.size(); i++) {
            actualCurrentCard = VALID_QUIZCARD.get(i);
            expected.add(new QuizCard(i, actualCurrentCard.getAnswer(), actualCurrentCard.getQuestion()));
        }

        List<QuizCard> actualLearn = new Quiz(VALID_QUIZCARD, Quiz.Mode.LEARN).generate();

        assertEquals(4, actualLearn.size());
        assertEquals(expected, actualLearn);

        // review
        List<QuizCard> actualReview = new Quiz(VALID_QUIZCARD, Quiz.Mode.REVIEW).generate();
        assertEquals(4, actualReview.size());
        assertEquals(expected, actualReview);

        // preview
        assertEquals(new Quiz(VALID_QUIZCARD, Quiz.Mode.PREVIEW).generate(), VALID_QUIZCARD);
    }

}
