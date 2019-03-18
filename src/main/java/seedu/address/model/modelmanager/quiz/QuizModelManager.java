package seedu.address.model.modelmanager.quiz;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.session.Session;

/**
 * Represents the in-memory management of quiz data
 */
public class QuizModelManager implements QuizModel {
    private static final Logger logger = LogsCenter.getLogger(QuizModelManager.class);

    private Quiz quiz;
    private Session session;

    /**
     * Initialises empty QuizModelManager
     */
    public QuizModelManager() {
        super();

        logger.fine("Initializing empty constructor");
    }

    // todo include session
    @Override
    public List<QuizCard> generateSession() {
        return session.generateSession();
    }

    @Override
    public Quiz.Mode getMode() {
        return session.getMode();
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
    public void init(Quiz quiz) {
        requireAllNonNull(quiz);
        this.quiz = quiz;
    }

    @Override
    public void initWithSession(Quiz quiz, Session session) {
        requireAllNonNull(quiz, session);
        this.quiz = quiz;
        this.session = session;
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
    public QuizCard getCurrentQuizCard() {
        return quiz.getCurrentQuizCard();
    }

    @Override
    public void updateTotalAttemptsAndStreak(int index, String answer) {
        quiz.updateTotalAttemptsAndStreak(index, answer);
    }

    @Override
    public boolean isDone() {
        try {
            return quiz.isDone();
        } catch (NullPointerException e) {
            logger.info("quiz not initialised");
            return true;
        }
    }

    @Override
    public List<List<Integer>> end() {
        return quiz.end();
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
