package seedu.address.model.quiz;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.quiz.QuizMode.DIFFICULT;
import static seedu.address.model.quiz.QuizMode.LEARN;
import static seedu.address.model.quiz.QuizMode.PREVIEW;
import static seedu.address.model.quiz.QuizMode.REVIEW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuizTest {
    private List<QuizCard> validQuizCard;
    private int validQuizCardSize;

    @Before
    public void setUp() {
        final QuizCard quizCard1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"),
            "Country", "Capital");
        final QuizCard quizCard2 = new QuizCard("Hungary", "Budapest", Arrays.asList("BE", "Europe"),
            "Country", "Capital");
        validQuizCard = Arrays.asList(quizCard1, quizCard2);
        validQuizCardSize = validQuizCard.size();
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            new Quiz(null, LEARN));
    }

    @Test
    public void constructor_invalidQuizCard_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
            new Quiz(validQuizCard, null));

        Assert.assertThrows(IllegalArgumentException.class, () ->
            new Quiz(Arrays.asList(new QuizCard("", "", Arrays.asList("JP", "Asia"),
                "Country", "Capital")), LEARN));
    }

    @Test
    public void generate() {
        List<QuizCard> expectedPreview = new ArrayList<>();
        List<QuizCard> expectedReview = new ArrayList<>();
        List<QuizCard> expectedLearn = new ArrayList<>();

        QuizCard expectedCurrentCard;
        for (int i = 0; i < validQuizCardSize; i++) {
            expectedCurrentCard = validQuizCard.get(i);
            expectedPreview.add(expectedCurrentCard.generateOrderedQuizCardWithIndex(i, PREVIEW));
        }

        for (int i = 0; i < validQuizCardSize; i++) {
            expectedCurrentCard = validQuizCard.get(i);
            expectedReview.add(expectedCurrentCard.generateOrderedQuizCardWithIndex(i, REVIEW));
        }

        for (int i = 0; i < validQuizCardSize; i++) {
            expectedCurrentCard = validQuizCard.get(i);
            expectedReview.add(expectedCurrentCard.generateFlippedQuizCardWithIndex(i));
        }

        expectedLearn.addAll(expectedPreview);
        expectedLearn.addAll(expectedReview);

        // learn
        List<QuizCard> actualLearn = new Quiz(validQuizCard, LEARN).getGeneratedQuizCardList();
        assertEquals(6, actualLearn.size());
        assertEquals(expectedLearn, actualLearn);

        // review
        List<QuizCard> actualReview = new Quiz(validQuizCard, REVIEW).getGeneratedQuizCardList();
        assertEquals(4, actualReview.size());
        assertEquals(expectedReview, actualReview);

        // preview
        List<QuizCard> actualPreview = new Quiz(validQuizCard, PREVIEW).getGeneratedQuizCardList();
        assertEquals(2, actualPreview.size());
        assertEquals(expectedPreview, actualPreview);

        // difficult == preview
        List<QuizCard> actualDifficult = new Quiz(validQuizCard, DIFFICULT).getGeneratedQuizCardList();
        assertEquals(2, actualDifficult.size());
        assertEquals(expectedPreview, actualDifficult);
    }

    @Test
    public void isNextCard() {
        Quiz quiz = new Quiz(validQuizCard, LEARN);
        assertTrue(quiz.hasCardLeft());

        // get all cards
        quiz.getNextCard();
        quiz.getNextCard();
        quiz.getNextCard();
        quiz.getNextCard();
        quiz.getNextCard();
        quiz.getNextCard();

        // no cards left
        assertFalse(quiz.hasCardLeft());

    }

    @Test
    public void getNextCard() {
        // ------- learn -------
        Quiz quiz = new Quiz(validQuizCard, LEARN);

        // normal
        List<QuizCard> generated = quiz.getGeneratedQuizCardList();
        for (int i = 0; i < validQuizCardSize * 3; i++) {
            assertEquals(generated.get(i), quiz.getNextCard());
            assertEquals((i + 1) + "/" + validQuizCardSize * 3, quiz.getCurrentProgress());
        }

        // no more cards
        Assert.assertThrows(IndexOutOfBoundsException.class, quiz::getNextCard);

        // ------- review -------
        Quiz quizReview = new Quiz(validQuizCard, REVIEW);

        // normal
        List<QuizCard> generatedReview = quizReview.getGeneratedQuizCardList();
        for (int i = 0; i < validQuizCardSize * 2; i++) {
            assertEquals(generatedReview.get(i), quizReview.getNextCard());
            assertEquals((i + 1) + "/" + validQuizCardSize * 2, quizReview.getCurrentProgress());
        }

        // no more cards
        Assert.assertThrows(IndexOutOfBoundsException.class, quizReview::getNextCard);

        // ------- preview -------
        Quiz quizPreview = new Quiz(validQuizCard, PREVIEW);

        // normal
        List<QuizCard> generatedPreview = quizPreview.getGeneratedQuizCardList();
        assertEquals(generatedPreview.get(0), quizPreview.getNextCard());
        assertEquals(generatedPreview.get(1), quizPreview.getNextCard());
        assertEquals(2 + "/" + validQuizCardSize, quizPreview.getCurrentProgress());


        // no more cards
        Assert.assertThrows(IndexOutOfBoundsException.class, quizPreview::getNextCard);
    }

    @Test
    public void getCurrentQuizCard_null_throwsNullPointerException() {
        Quiz quiz = new Quiz(validQuizCard, LEARN);

        Assert.assertThrows(NullPointerException.class, quiz::getCurrentQuizCard);
    }

    @Test
    public void getCurrentQuizCard() {
        Quiz quiz = new Quiz(validQuizCard, LEARN);
        QuizCard expected = quiz.getNextCard();

        assertEquals(validQuizCard.get(0).generateOrderedQuizCardWithIndex(0, PREVIEW), quiz.getCurrentQuizCard());
        assertEquals(expected, quiz.getCurrentQuizCard());
    }

    @Test
    public void updateTotalAttemptsAndStreak() {
        final int index = 0;
        final String correctAnswer = "Tokyo";
        final String wrongAnswer = "wrong answer";

        // ------- learn -------
        List<QuizCard> expected = validQuizCard;
        QuizCard expectedCard1 = expected.get(index);
        expectedCard1.updateTotalAttemptsAndStreak(expectedCard1.isCorrect(correctAnswer));

        Quiz quiz = new Quiz(validQuizCard, LEARN);
        quiz.getNextCard();

        assertTrue(quiz.updateTotalAttemptsAndStreak(index, correctAnswer));
        assertEquals(expected, quiz.getOriginalQuizCardList());
        assertEquals(1, quiz.getQuizTotalAttempts());
        assertEquals(1, quiz.getQuizTotalCorrectQuestions());

        // test wrong answer
        expectedCard1.updateTotalAttemptsAndStreak(expectedCard1.isCorrect(wrongAnswer));
        quiz.getNextCard();
        assertFalse(quiz.updateTotalAttemptsAndStreak(index, wrongAnswer));
        assertEquals(expected, quiz.getOriginalQuizCardList());
        assertEquals(2, quiz.getQuizTotalAttempts());
        assertEquals(1, quiz.getQuizTotalCorrectQuestions());

        // ------- Review -------
        Quiz quizReview = new Quiz(validQuizCard, REVIEW);
        quizReview.getNextCard();
        quizReview.updateTotalAttemptsAndStreak(index, correctAnswer);

        assertEquals(expected, quizReview.getOriginalQuizCardList());

        // ------- Preview -------
        Quiz quizPreview = new Quiz(validQuizCard, REVIEW);
        quizPreview.getNextCard();
        quizPreview.updateTotalAttemptsAndStreak(index, correctAnswer);

        assertEquals(expected, quizPreview.getOriginalQuizCardList());
    }

    @Test
    public void end() {
        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(0, 1, 1, 0));

        // quiz just started
        Quiz quiz = new Quiz(validQuizCard, LEARN);
        assertFalse(quiz.isQuizDone());

        // test 2 question
        quiz.getNextCard();
        quiz.updateTotalAttemptsAndStreak(0, "Tokyo");
        quiz.getNextCard();
        quiz.updateTotalAttemptsAndStreak(1, "wrong answer");
        quiz.setQuizDone();

        assertEquals(expected, quiz.end());
        assertTrue(quiz.isQuizDone());
    }

    @Test
    public void toggleIsCardDifficult() {
        final int index = 0;

        Quiz quiz = new Quiz(validQuizCard, LEARN);
        quiz.getNextCard();

        assertTrue(quiz.toggleIsCardDifficult(index));
        assertFalse(quiz.toggleIsCardDifficult(index));
    }

    @Test
    public void equals() {
        Quiz quiz = new Quiz(validQuizCard, LEARN);

        // same value -> returns true
        Quiz quizCopy = new Quiz(validQuizCard, LEARN);
        assertEquals(quiz, quizCopy);

        // same object -> returns true
        assertEquals(quiz, quiz);

        // different object -> returns false
        assertNotEquals(quiz, new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"),
            "Country", "Capital"));

        // different types -> returns false
        assertNotEquals(5, quiz);
    }

    @Test
    public void completeFlow() {
        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(0, 2, 2, 0));
        expected.add(Arrays.asList(1, 2, 1, 0));

        Quiz quiz = new Quiz(validQuizCard, LEARN);
        // Preview questions and answers
        quiz.getNextCard();
        quiz.getNextCard();

        assertTrue(quiz.getNextCard().isCorrect("Tokyo"));
        quiz.updateTotalAttemptsAndStreak(0, "Tokyo");

        assertFalse(quiz.getNextCard().isCorrect("wrong answer"));
        quiz.updateTotalAttemptsAndStreak(1, "wrong answer");

        assertTrue(quiz.getNextCard().isCorrect("Japan"));
        quiz.updateTotalAttemptsAndStreak(0, "Japan");

        assertTrue(quiz.getNextCard().isCorrect("Hungary"));
        quiz.updateTotalAttemptsAndStreak(1, "Hungary");

        assertEquals(expected, quiz.end());
    }

}
