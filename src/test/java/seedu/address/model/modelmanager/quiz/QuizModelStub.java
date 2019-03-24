package seedu.address.model.modelmanager.quiz;

import java.util.List;

import seedu.address.model.session.Session;

/**
 * A default quiz model stub that has all of the methods failing.
 */
public class QuizModelStub implements QuizModel {
    /**
     * This method should not be called.
     */
    public List<QuizCard> generateSession() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * This method should not be called.
     */
    public Quiz.Mode getMode() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * This method should not be called.
     */
    public int getCount() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * This method should not be called.
     */
    public String getName() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Sets the {@code Quiz} information.
     */
    public void init(Quiz quiz) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Sets the {@code Quiz} and {@code Session} information.
     */
    public void initWithSession(Quiz quiz, Session session) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Returns if there is still card left in {@code Quiz}.
     */
    public boolean hasCardLeft() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Returns the next card in line for {@code Quiz}.
     */
    public QuizCard getNextCard() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getCurrentProgress() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Returns the current QuizCard in {@code Quiz}.
     */
    public QuizCard getCurrentQuizCard() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Update the totalAttempts and streak of a specified card in the current session.
     * @param index of the current {@code QuizCard}
     * @param answer user input
     */
    public boolean updateTotalAttemptsAndStreak(int index, String answer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getQuizTotalAttempts() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getQuizTotalCorrectQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isQuizDone() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Returns if User is done with {@code Quiz}.
     */
    public boolean isDone() {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Returns data needed by {@code Session} when {@code Quiz} end.
     */
    public List<List<Integer>> end() {
        throw new AssertionError("This method should not be called.");
    }
}
