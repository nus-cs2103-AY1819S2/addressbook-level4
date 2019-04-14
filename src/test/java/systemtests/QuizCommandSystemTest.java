package systemtests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.core.QuizState;

public class QuizCommandSystemTest extends CardCollectionSystemTest {
    @Test
    public void quiz() {
        executeCommand("quiz");
        assertEquals(QuizState.QUIZ_MODE_FRONT, (int) getModelQuizMode());
        assertEquals(false, getModelIsQuizSrs());
        executeCommand("show");
        assertEquals(QuizState.QUIZ_MODE_BOTH, (int) getModelQuizMode());
        int size = getModelQuizFlashcardSize();
        executeCommand("good");
        assertEquals(size - 1, getModelQuizFlashcardSize());
        executeCommand("exit");
        assertEquals(QuizState.NOT_QUIZ_MODE, (int) getModelQuizMode());
    }

    @Test
    public void quizReview() {
        executeCommand("quiz review");
        assertEquals(QuizState.QUIZ_MODE_FRONT, (int) getModelQuizMode());
        assertEquals(false, getModelIsQuizSrs());
        executeCommand("show");
        assertEquals(QuizState.QUIZ_MODE_BOTH, (int) getModelQuizMode());
        int size = getModelQuizFlashcardSize();
        executeCommand("good");
        assertEquals(size - 1, getModelQuizFlashcardSize());
        executeCommand("exit");
        assertEquals(QuizState.NOT_QUIZ_MODE, (int) getModelQuizMode());
    }

    @Test
    public void quizSrs() {
        executeCommand("quiz srs");
        assertEquals(QuizState.QUIZ_MODE_FRONT, (int) getModelQuizMode());
        assertEquals(true, getModelIsQuizSrs());
        executeCommand("show");
        assertEquals(QuizState.QUIZ_MODE_BOTH, (int) getModelQuizMode());
        int size = getModelQuizFlashcardSize();
        executeCommand("good");
        assertEquals(size - 1, getModelQuizFlashcardSize());
        executeCommand("exit");
        assertEquals(QuizState.NOT_QUIZ_MODE, (int) getModelQuizMode());
        executeCommand("quiz srs");
        assertEquals(size - 1, getModelQuizFlashcardSize());
    }
}
