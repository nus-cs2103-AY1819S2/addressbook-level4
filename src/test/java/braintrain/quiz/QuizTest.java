package braintrain.quiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import braintrain.testutil.Assert;

import org.junit.Test;

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
        QuizCard expectedCurrentCard;
        for (int i = 0; i < VALID_QUIZCARD.size(); i++) {
            expectedCurrentCard = VALID_QUIZCARD.get(i);
            expected.add(new QuizCard(i, expectedCurrentCard.getQuestion(), expectedCurrentCard.getAnswer()));
        }

        for (int i = 0; i < VALID_QUIZCARD.size(); i++) {
            expectedCurrentCard = VALID_QUIZCARD.get(i);
            expected.add(new QuizCard(i, expectedCurrentCard.getAnswer(), expectedCurrentCard.getQuestion()));
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

    @Test
    public void isNextCard() {
        Quiz quiz = new Quiz(VALID_QUIZCARD, Quiz.Mode.LEARN);
        assertTrue(quiz.isNextCard());

        // get all cards
        quiz.getNextCard();
        quiz.getNextCard();
        quiz.getNextCard();
        quiz.getNextCard();

        // no cards left
        assertFalse(quiz.isNextCard());

    }

    @Test
    public void getNextCard() {
        // ------- learn -------
        Quiz quiz = new Quiz(VALID_QUIZCARD, Quiz.Mode.LEARN);

        // normal
        List<QuizCard> generated = quiz.generate();
        assertEquals(generated.get(0), quiz.getNextCard());
        assertEquals(generated.get(1), quiz.getNextCard());
        assertEquals(generated.get(2), quiz.getNextCard());
        assertEquals(generated.get(3), quiz.getNextCard());

        // no more cards
        Assert.assertThrows(IndexOutOfBoundsException.class, () ->
            quiz.getNextCard());

        // ------- review -------
        Quiz quizReview = new Quiz(VALID_QUIZCARD, Quiz.Mode.REVIEW);

        // normal
        List<QuizCard> generatedReview = quizReview.generate();
        assertEquals(generatedReview.get(0), quizReview.getNextCard());
        assertEquals(generatedReview.get(1), quizReview.getNextCard());
        assertEquals(generatedReview.get(2), quizReview.getNextCard());
        assertEquals(generatedReview.get(3), quizReview.getNextCard());

        // no more cards
        Assert.assertThrows(IndexOutOfBoundsException.class, () ->
            quizReview.getNextCard());

        // ------- preview -------
        Quiz quizPreview = new Quiz(VALID_QUIZCARD, Quiz.Mode.PREVIEW);

        // normal
        List<QuizCard> generatedPreview = quizPreview.generate();
        assertEquals(generatedPreview.get(0), quizPreview.getNextCard());
        assertEquals(generatedPreview.get(1), quizPreview.getNextCard());

        // no more cards
        Assert.assertThrows(IndexOutOfBoundsException.class, () ->
            quizPreview.getNextCard());
    }

    @Test
    public void updateTotalAttemptsandStreak() {
        final int index = 0;
        final String correctAnswer = "Tokyo";
        final String wrongAnswer = "wrong answer";
        final QuizCard card1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));

        // ------- learn -------
        List<QuizCard> expected = quizCards;
        QuizCard expectedCard1 = expected.get(index);
        expectedCard1.updateTotalAttemptsandStreak(expectedCard1.isCorrect(correctAnswer));

        Quiz quiz = new Quiz(quizCards, Quiz.Mode.LEARN);
        quiz.updateTotalAttemptsandStreak(index, correctAnswer);

        assertEquals(expected, quiz.getCurrentSession());

        // test wrong answer
        expectedCard1.updateTotalAttemptsandStreak(expectedCard1.isCorrect(wrongAnswer));
        quiz.updateTotalAttemptsandStreak(index, wrongAnswer);
        assertEquals(expected, quiz.getCurrentSession());

        // ------- Review -------
        Quiz quizReview = new Quiz(quizCards, Quiz.Mode.REVIEW);
        quizReview.updateTotalAttemptsandStreak(index, correctAnswer);

        assertEquals(expected, quizReview.getCurrentSession());

        // ------- Preview -------
        Quiz quizPreview = new Quiz(quizCards, Quiz.Mode.REVIEW);
        quizPreview.updateTotalAttemptsandStreak(index, correctAnswer);

        assertEquals(expected, quizPreview.getCurrentSession());
    }

    @Test
    public void end() {
        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(0, 1, 1));
        expected.add(Arrays.asList(1, 1, 0));

        // quiz just started
        Quiz quiz = new Quiz(VALID_QUIZCARD, Quiz.Mode.LEARN);
        assertFalse(quiz.isEnd());

        quiz.updateTotalAttemptsandStreak(0, "Tokyo");
        quiz.updateTotalAttemptsandStreak(1, "wrong answer");

        assertEquals(expected, quiz.end());
        assertTrue(quiz.isEnd());
    }

}
