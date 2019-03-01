package braintrain.model.quiz;

import static braintrain.commons.util.AppUtil.checkArgument;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import braintrain.model.quiz.exceptions.NotInitialisedException;

/**
 * Represents a quiz that stores a list of QuizCard
 */
public class Quiz {

    public static final String MESSAGE_CONSTRAINTS = "Mode must only be learn/review/preview";

    private List<QuizCard> currentSession;
    private List<QuizCard> generatedSession;
    private Mode mode; // learn/review/preview
    private QuizCard currentQuizCard;
    private int currentCardIndex;
    private int generatedCardSize;

    /**
     * Different types of mode supported in Quiz.
     * Learn: sees both the question and answer first then get tested.
     * Review: only get tested.
     * Preview: sees both question and answer but not tested.
     */
    public enum Mode {
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
        this.currentCardIndex = -1;
        this.generatedCardSize = -1;
    }

    /**
     * Generates a list of cards based on the chosen cards given by session.
     */
    public List<QuizCard> generate() {
        QuizCard currentCard;
        generatedSession = new ArrayList<>();

        if (mode != Mode.PREVIEW) {
            for (int i = 0; i < currentSession.size(); i++) {
                currentCard = currentSession.get(i);
                generatedSession.add(new QuizCard(i, currentCard.getQuestion(), currentCard.getAnswer()));
            }

            for (int i = 0; i < currentSession.size(); i++) {
                currentCard = currentSession.get(i);
                generatedSession.add(new QuizCard(i, currentCard.getAnswer(), currentCard.getQuestion()));
            }

        } else {
            generatedSession = currentSession;
        }

        generatedCardSize = generatedSession.size();
        return generatedSession;
    }

    /**
     * Returns true if there is card left in quiz
     */
    public boolean isNextCard() {
        return currentCardIndex < (generatedCardSize - 1);
    }

    public QuizCard getNextCard() throws NotInitialisedException {
        if (generatedCardSize == -1) {
            throw new NotInitialisedException("Cards have not been generated, please run generate first");
        }

        currentCardIndex++;

        if (currentCardIndex < generatedCardSize) {
            currentQuizCard = generatedSession.get(currentCardIndex);
            return currentQuizCard;
        }

        throw new IndexOutOfBoundsException("No cards left.");
    }
}
