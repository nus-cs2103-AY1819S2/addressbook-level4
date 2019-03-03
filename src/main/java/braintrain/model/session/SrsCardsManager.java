package braintrain.model.session;

import java.util.List;

import braintrain.model.card.SrsCard;
import braintrain.model.lesson.Lesson;
import braintrain.model.user.CardData;

/**
 * Represents
 */
public class SrsCardsManager {
    private Lesson lesson;
    private List<SrsCard> srsCards;
    private List<List<Integer>> quizInformation;

    public SrsCardsManager() {

    }

    /**
     * Sorts all cards in this lesson based on their srsDueDate.
     */
    public List<SrsCard> generate() {

    }

    /**
     * Updates fields of each cardData class based on the result of quiz system.
     */
    public List<CardData> update() {

    }
}
