package braintrain.quiz;

import static braintrain.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import braintrain.commons.core.LogsCenter;

/**
 * Represents the in-memory model of quiz data
 */
public class QuizModelManager implements QuizModel {
    private static final Logger logger = LogsCenter.getLogger(QuizModelManager.class);

    private Quiz quiz;
    private boolean ended;

    /**
     * Initialises empty QuizModelManager
     */
    public QuizModelManager() {
        super();

        logger.fine("Initializing empty constructor");
        this.ended = true;
    }

    // todo include session
    @Override
    public void init(Quiz quiz) {
        requireAllNonNull(quiz);
        this.quiz = quiz;
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
    public void updateTotalAttemptsandStreak(int index, String answer) {
        quiz.updateTotalAttemptsandStreak(index, answer);
    }

    @Override
    public boolean isEnd() {
        try {
            return quiz.isDone();
        } catch (NullPointerException e) {
            logger.info("quiz not initialised");
            return ended;
        }
    }

    @Override
    public List<List<Integer>> end() {
        return quiz.end();
    }
}
