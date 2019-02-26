package braintrain.model.quiz;

import static braintrain.commons.util.AppUtil.checkArgument;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a quiz that stores a list of QuizCard
 */
public class Quiz {

    public static final String MESSAGE_CONSTRAINTS = "Mode must only be learn/review/preview";

    private List<List<Integer>> returnSession; //return format list [index, totalAttempts, streak]
    private List<QuizCard> currentSession;
    private List<QuizCard> generatedSession;
    private Mode mode; // learn/review/preview
    private QuizCard currentQuizCard;

    /**
     * Different types of mode supported in Quiz.
     * Learn: sees both the question and answer first then get tested.
     * Review: only get tested.
     * Preview: sees both question and answer but not tested.
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

    /**
     * Generates a list of cards based on the chosen cards given by session.
     */
    public List<QuizCard> generate() {
        QuizCard currentCard;

        if (mode != Mode.PREVIEW) {
            generatedSession = new ArrayList<>();
            for (int i = 0; i < currentSession.size(); i++) {
                currentCard = currentSession.get(i);
                generatedSession.add(new QuizCard(i, currentCard.getQuestion(), currentCard.getAnswer()));
            }

            for (int i = 0; i < currentSession.size(); i++) {
                currentCard = currentSession.get(i);
                generatedSession.add(new QuizCard(i, currentCard.getAnswer(), currentCard.getQuestion()));
            }
        }

        return generatedSession;
    }
}
