package braintrain.model.quiz;

import static braintrain.commons.util.AppUtil.checkArgument;

import static java.util.Objects.requireNonNull;

import java.util.List;


/**
 * Represents a quiz that stores a list of QuizCard
 */
public class Quiz {

    public static final String MESSAGE_CONSTRAINTS = "Mode must only be learn/review/preview";

    private List<List<Integer>> returnSession; //return format list [index, totalAttempts, streak]
    private List<QuizCard> currentSession;
    private Mode mode; // learn/review/preview
    private QuizCard currentQuizCard;

    /**
     * Different types of mode supported in Quiz
     */
    enum Mode {
        LEARN,
        REVIEW,
        PREVIEW
    }

    /**
     * Build constructor from session
     * @param session contains a list of question, answer and list of optional
     */
    public Quiz(List<QuizCard> session, Mode mode) {
        requireNonNull(session);
        checkArgument(mode != null, MESSAGE_CONSTRAINTS);

        this.currentSession = session;
        this.mode = mode;
    }

}
