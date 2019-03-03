package braintrain.model.session;

import java.util.ArrayList;
import java.util.List;

import braintrain.model.card.SrsCard;
import braintrain.quiz.QuizCard;


/**
 * Represents a session that stores cards based on srs data.
 */
public class Session {
    private static final int CARD_COUNT_MINIMUM = 5;

    private String name;
    private Mode mode;
    private int cardCount;
    private List<QuizCard> quizCards;
    private List<SrsCard> srsCards;

    /**
     * Pass different types of mode supported in Quiz.
     * Learn: sees both the question and answer first then get tested.
     * Review: only get tested.
     * Preview: sees both question and answer but not tested.
     */
    enum Mode {
        LEARN,
        REVIEW,
        PREVIEW
    }

    public Session(String name, int cardCount, Mode mode, List<SrsCard> srsCards) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid name");
        }
        if (cardCount < CARD_COUNT_MINIMUM) {
            throw new IllegalArgumentException("CardCount should not be less than five in a single session");
        }
        if ((mode != Mode.LEARN) & (mode != Mode.REVIEW) & (mode != Mode.PREVIEW)) {
            throw new IllegalArgumentException("Invalid mode");
        }

        quizCards = new ArrayList<>();

        this.name = name;
        this.cardCount = cardCount;
        this.mode = mode;
        this.srsCards = srsCards;
    }

    public Session(String name, Mode mode, List<SrsCard> srsCards) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid name");
        }
        if ((mode != Mode.LEARN) & (mode != Mode.REVIEW) & (mode != Mode.PREVIEW)) {
            throw new IllegalArgumentException("Invalid mode");
        }

        quizCards = new ArrayList<>();

        this.name = name;
        this.cardCount = 10;
        this.mode = mode;
        this.srsCards = srsCards;
    }

    /**
     * Generate a list of quizCards that will pass to quiz system.
     */
    public List<QuizCard> generate() {
        SrsCard currentCard;
        for (int i = 0; i < cardCount; i++) {
            currentCard = srsCards.get(i);
            quizCards.add(new QuizCard(currentCard.getQuestion(), currentCard.getAnswer()));
        }
        return quizCards;
    }

    public Mode getMode() {
        return mode;
    }
}
