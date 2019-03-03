package braintrain.model.session;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import braintrain.model.card.Card;
import braintrain.model.card.SRSCard;
import braintrain.model.lesson.Lesson;
import braintrain.model.user.CardData;

/**
 * Represents a model of cards manager.
 */
public class SRSCardsManager {
    private Lesson lesson;
    private List<CardData> cardData;
    private List<SRSCard> srsCards;
    private List<List<Integer>> quizInformation;

    public SRSCardsManager(Lesson lesson, List<CardData> cardData) {
        this.lesson = lesson;
        this.cardData = cardData;
    }
    public SRSCardsManager(List<SRSCard> srsCards, List<List<Integer>> quizInformation) {
        this.quizInformation = quizInformation;
        this.srsCards = srsCards;
    }

    /**
     * Sorts all cards in this lesson based on their srsDueDate.
     */
    public List<SRSCard> generate() {
        List<Card> cards = lesson.getCards();
        List<SRSCard> srsCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            Card currentCard = cards.get(i);
            SRSCard srsCard = new SRSCard();
            for (int j = 0; j < cardData.size(); j++) {
                if (currentCard.hashCode() == cardData.get(j).getHashCode()) {
                    srsCard = new SRSCard(currentCard, cardData.get(j), lesson);
                    break;
                }
            }
            Instant currentsrsDueDate = srsCard.getSrsDueDate();
            if (srsCards.size() == 0) {
                srsCards.add(srsCard);
            }
            for (int k = 0; k < srsCards.size(); k++) {
                if (currentsrsDueDate.compareTo(srsCards.get(k).getSrsDueDate()) < 0) {
                    srsCards.add(k, srsCard);
                }
            }
        }

        return srsCards;
    }

    /**
     * Updates fields of each cardData class based on the result of quiz system.
     */
    public List<CardData> update() {
        List<CardData> updatedCardData = new ArrayList<>();
        for (int i = 0; i < srsCards.size(); i++) { //terminal value to be changed
            int currentHashCode = srsCards.get(i).getHashcode();
            int currentNumOfAttempts = srsCards.get(i).getNumOfAttempts() + quizInformation.get(i).get(1);
            int currentStreak = srsCards.get(i).getStreak() + quizInformation.get(i).get(2);
            //TODO: update srsduedate. put the unchanged value for now.
            updatedCardData.add(new CardData(currentHashCode, currentNumOfAttempts, currentStreak,
                    srsCards.get(i).getSrsDueDate()));
        }

        return updatedCardData;
    }
}
