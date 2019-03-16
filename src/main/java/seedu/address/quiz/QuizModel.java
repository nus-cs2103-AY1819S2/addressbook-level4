package seedu.address.quiz;

import java.util.List;

/**
 * The API of the QuizModel component.
 */
public interface QuizModel {
    /**
     * Sets the {@code Quiz} information.
     */
    void init(Quiz quiz);

    /**
     * Returns if there is still card left in {@code Quiz}.
     */
    boolean hasCardLeft();

    /**
     * Returns the next card in line for {@code Quiz}.
     */
    QuizCard getNextCard();

    /**
     * Returns the user current progress in {@code Quiz}.
     */
    String getCurrentProgress();

    /**
     * Returns the current QuizCard in {@code Quiz}.
     */
    QuizCard getCurrentQuizCard();

    /**
     * Update the totalAttempts and streak of a specified card in the current session.
     * @param index of the current {@code QuizCard}
     * @param answer user input
     */
    void updateTotalAttemptsAndStreak(int index, String answer);

    /**
     * Returns total attempts in this {@code Quiz}.
     */
    int getQuizTotalAttempts();

    /**
     * Returns the total correct questions attempted in this {code Quiz}.
     */
    int getQuizTotalCorrectQuestions();

    /**
     * Returns if User is done with {@code Quiz}.
     */
    boolean isDone();

    /**
     * Returns data needed by {@code Session} when {@code Quiz} end.
     */
    List<List<Integer>> end();
}
