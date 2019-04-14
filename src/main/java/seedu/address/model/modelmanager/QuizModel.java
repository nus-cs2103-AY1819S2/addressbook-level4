package seedu.address.model.modelmanager;

import java.util.List;

import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.session.Session;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.User;

/**
 * The API of the QuizModel component.
 */
public interface QuizModel extends Model {

    /**
     * Return mode of {@link Session}.
     */
    QuizMode getMode();
    /**
     *
     */
    int getIndex();

    /**
     * Return card count of {@link Session}.
     */
    int getCount();

    /**
     * Return name of {@link Session}.
     */
    String getName();

    /**
     * Return a list of SrsCards for updating user progress.
     */
    List<SrsCard> getQuizSrsCards();

    /**
     * Sets the {@link Quiz} and {@link Session} information.
     */
    void init(Quiz quiz, Session session);

    /**
     * Returns if there is still {@link QuizCard} left in {@link Quiz}.
     */
    boolean hasCardLeft();

    /**
     * Returns the next {@link QuizCard} in line for {@link Quiz}.
     */
    QuizCard getNextCard();

    /**
     * Returns the user current progress in {@link Quiz}.
     */
    String getCurrentProgress();

    /**
     * Returns the current {@link QuizCard} in {@link Quiz}.
     */
    QuizCard getCurrentQuizCard();

    /**
     * Update the totalAttempts and streak of a specified {@link QuizCard} in the current quiz.
     * @param index of the current {@link QuizCard}
     * @param answer user input
     * @return true if correct
     */
    boolean updateTotalAttemptsAndStreak(int index, String answer);

    /**
     * Returns total attempts in this {@link Quiz}.
     */
    int getQuizTotalAttempts();

    /**
     * Returns the total correct questions attempted in this {@link Quiz}.
     */
    int getQuizTotalCorrectQuestions();

    /**
     * Toggles between if the card labeled difficult.
     * @param index of the current {@link QuizCard}
     * @return result after toggling
     */
    boolean toggleIsCardDifficult(int index);

    /**
     * Returns list of optional of the {@link QuizCard}.
     */
    List<String> getOpt();

    /**
     * Returns if User is done with {@link Quiz}.
     */
    boolean isQuizDone();

    /**
     * Returns data needed by {@link Session} when {@link Quiz} end.
     */
    List<List<Integer>> end();

    /**
     * Updates user profile after quiz ends.
     * @param quizInformation from quiz.
     */
    void updateUserProfile(List<List<Integer>> quizInformation);

    /**
     * Returns the list of original {@link QuizCard} that includes streak and total attempts.
     */
    List<QuizCard> getQuizCardList();

    /**
     * Returns true to display quiz result, otherwise false.
     */
    boolean isResultDisplay();

    /**
     * Sets result display to display quiz result or not.
     */
    void setResultDisplay(boolean resultDisplay);

    /**
     * Returns {@link User} data from {@link ManagementModel}.
     */
    User getManagementModelUser();
}
