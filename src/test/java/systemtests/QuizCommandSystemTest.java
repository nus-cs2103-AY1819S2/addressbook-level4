package systemtests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.core.QuizState;

public class QuizCommandSystemTest extends CardCollectionSystemTest {
    @Test
    public void quiz() {
        executeCommand("quiz");
        assertEquals(QuizState.QUIZ_MODE_FRONT, (int) getModelQuizMode());
        executeCommand("show");
        assertEquals(QuizState.QUIZ_MODE_BOTH, (int) getModelQuizMode());
        int size = getModelQuizFlashcardSize();
        executeCommand("good");
        assertEquals(size - 1, getModelQuizFlashcardSize());
        executeCommand("exit");
        assertEquals(QuizState.NOT_QUIZ_MODE, (int) getModelQuizMode());
    }
}
