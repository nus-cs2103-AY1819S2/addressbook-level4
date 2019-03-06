package seedu.address.quiz;

import seedu.address.commons.util.AppUtil;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a quiz that stores a list of QuizCard
 */
public class Quiz {

    public static final String MESSAGE_CONSTRAINTS = "Mode must only be learn/review/preview";

    private List<QuizCard> currentSession;
    private List<QuizCard> generatedSession;
    private Mode mode;
    private QuizCard currentQuizCard;
    private int currentCardIndex;
    private int generatedCardSize;
    private boolean isDone; // Indicates if User is done with this quiz

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
        AppUtil.checkArgument(mode != null, MESSAGE_CONSTRAINTS);

        this.currentSession = session;
        this.mode = mode;
        this.currentCardIndex = -1;
        this.generatedCardSize = -1;
        this.isDone = false;

        generate();
    }

    /**
     * Generates a list of cards based on the chosen cards given by session.
     * R
     */
    public List<QuizCard> generate() {
        generatedSession = new ArrayList<>();

        switch (mode) {
        case PREVIEW:
            generatePreview();
            break;
        case LEARN:
            // Learn is a combination of Preview + Review
            generatePreview();
            generateReview();
            break;
        case REVIEW:
            generateReview();
            break;
        default:
            break;
        }

        generatedCardSize = generatedSession.size();
        return generatedSession;
    }

    /**
     * Generates a list of card with the mode Review
     */
    private void generateReview() {
        QuizCard currentCard;
        for (int i = 0; i < currentSession.size(); i++) {
            currentCard = currentSession.get(i);
            generatedSession.add(new QuizCard(i, currentCard.getQuestion(), currentCard.getAnswer(), Mode.REVIEW));
        }

        for (int i = 0; i < currentSession.size(); i++) {
            currentCard = currentSession.get(i);
            generatedSession.add(new QuizCard(i, currentCard.getAnswer(), currentCard.getQuestion(), Mode.REVIEW));
        }
    }

    /**
     * Generates a list of card with the mode Preview see but don't need to answer.
     */
    private void generatePreview() {
        QuizCard currentCard;

        for (int i = 0; i < currentSession.size(); i++) {
            currentCard = currentSession.get(i);
            generatedSession.add(new QuizCard(i, currentCard.getQuestion(), currentCard.getAnswer(), Mode.PREVIEW));
        }
    }

    /**
     * Returns true if there is card left in quiz.
     */
    public boolean hasCardLeft() {
        return currentCardIndex < (generatedCardSize - 1);
    }

    /**
     * Returns the next card in line.
     */
    public QuizCard getNextCard() {
        currentCardIndex++;

        if (currentCardIndex < generatedCardSize) {
            currentQuizCard = generatedSession.get(currentCardIndex);
            return currentQuizCard;
        }

        throw new IndexOutOfBoundsException("No cards left.");
    }

    public QuizCard getCurrentQuizCard() {
        requireNonNull(currentQuizCard);
        return currentQuizCard;
    }

    /**
     * Update the totalAttempts and streak of a specified card in the current session.
     * @param index of the card
     * @param answer user input
     */
    public void updateTotalAttemptsAndStreak(int index, String answer) {
        QuizCard sessionCard = currentSession.get(index);
        sessionCard.updateTotalAttemptsAndStreak(currentQuizCard.isCorrect(answer));
    }

    /**
     * Returns the current session.
     */
    public List<QuizCard> getCurrentSession() {
        return currentSession;
    }

    /**
     * Format data needed by Session
     * @return a list of index of card, total attempts and streak in this session.
     */
    public List<List<Integer>> end() {
        this.isDone = true;

        List<List<Integer>> session = new ArrayList<>();
        QuizCard card;
        for (int i = 0; i < currentSession.size(); i++) {
            card = currentSession.get(i);
            session.add(Arrays.asList(i, card.getTotalAttempts(), card.getStreak()));
        }

        return session;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof Quiz)) {
            return false;
        }

        // state check
        Quiz other = (Quiz) obj;
        return other.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentSession, generatedSession, mode,
            currentQuizCard, currentCardIndex, isDone);
    }

}
