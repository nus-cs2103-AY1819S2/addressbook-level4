package seedu.address.model.modelmanager;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.session.Session;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;

/**
 * Represents the in-memory management of quiz data
 */
public class QuizModelManager implements QuizModel {
    private static final Logger logger = LogsCenter.getLogger(QuizModelManager.class);

    private ManagementModel managementModel;
    private Quiz quiz;
    private Session session;
    private boolean isResultDisplay;

    /**
     * Initialises QuizModelManager with ManagementModel
     * @param managementModel instance of ManagementModel
     */
    public QuizModelManager(ManagementModel managementModel) {
        super();
        this.managementModel = managementModel;

        logger.fine("Initializing constructor with ManagementModel");
    }

    /**
     * Initialises empty QuizModelManager
     */
    public QuizModelManager() {
        super();

        logger.fine("Initializing empty constructor");
    }

    //=========== Session ==================================================================================

    @Override
    public QuizMode getMode() {
        return session.getMode();
    }
    @Override
    public int getIndex() {
        return session.getLessonIndex();
    }

    @Override
    public int getCount() {
        return session.getCount();
    }

    @Override
    public String getName() {
        return session.getName();
    }
    @Override
    public List<SrsCard> getQuizSrsCards() {
        return session.getQuizSrsCards();
    }

    @Override
    public void updateUserProfile(List<List<Integer>> quizInformation) {
        List<CardSrsData> cardSrsDataList = session.updateUserProfile(quizInformation);

        for (CardSrsData cardSrsData : cardSrsDataList) {
            managementModel.addCardSrsData(cardSrsData);
        }
    }

    //=========== Quiz ==================================================================================

    @Override
    public void init(Quiz quiz, Session session) {
        requireAllNonNull(quiz, session);
        this.quiz = quiz;
        this.session = session;
        this.isResultDisplay = false;
    }

    @Override
    public boolean hasCardLeft() {
        return quiz.hasCardLeft();
    }

    @Override
    public QuizCard getNextCard() {
        return quiz.getNextCard();
    }

    @Override
    public String getCurrentProgress() {
        return quiz.getCurrentProgress();
    }

    @Override
    public QuizCard getCurrentQuizCard() {
        return quiz.getCurrentQuizCard();
    }

    @Override
    public List<QuizCard> getQuizCardList() {
        return quiz.getOriginalQuizCardList();
    }

    @Override
    public boolean updateTotalAttemptsAndStreak(int index, String answer) {
        return quiz.updateTotalAttemptsAndStreak(index, answer);
    }

    @Override
    public int getQuizTotalAttempts() {
        return quiz.getQuizTotalAttempts();
    }

    @Override
    public int getQuizTotalCorrectQuestions() {
        return quiz.getQuizTotalCorrectQuestions();
    }

    @Override
    public boolean toggleIsCardDifficult(int index) {
        return quiz.toggleIsCardDifficult(index);
    }

    @Override
    public List<String> getOpt() {
        return quiz.getOpt();
    }

    @Override
    public boolean isQuizDone() {
        if (quiz != null) {
            return quiz.isQuizDone();
        }

        // By default if no quiz is running, it will be considered as done
        return true;
    }

    @Override
    public boolean isResultDisplay() {
        return isResultDisplay;
    }

    @Override
    public void setResultDisplay(boolean resultDisplay) {
        if (!resultDisplay) {
            quiz.setQuizDone();
        }

        isResultDisplay = resultDisplay;
    }

    @Override
    public List<List<Integer>> end() {
        return quiz.end();
    }

    @Override
    public User getManagementModelUser() {
        return managementModel.getUser();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof QuizModelManager)) {
            return false;
        }

        // state check
        QuizModelManager other = (QuizModelManager) obj;
        return quiz.equals(other.quiz);
    }

}
