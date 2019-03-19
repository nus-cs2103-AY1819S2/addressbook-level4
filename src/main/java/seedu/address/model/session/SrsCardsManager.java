package seedu.address.model.session;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.card.Card;
import seedu.address.model.card.SrsCard;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.user.CardSrsData;

/**
 * Represents a management of cards manager.
 */
public class SrsCardsManager {
    private Lesson lesson;
    private List<CardSrsData> cardData;
    private List<SrsCard> srsCards;
    private List<List<Integer>> quizInformation;

    public SrsCardsManager(Lesson lesson, List<CardSrsData> cardData) {
        this.lesson = lesson;
        this.cardData = cardData;
    }

    public SrsCardsManager(List<SrsCard> srsCards, List<List<Integer>> quizInformation) {
        this.quizInformation = quizInformation;
        this.srsCards = srsCards;
    }

    /**
     * Sorts all cards in this lesson based on their srsDueDate.
     */
    public List<SrsCard> sort() {
        List<Card> cards = lesson.getCards();
        List<SrsCard> srsCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            Card currentCard = cards.get(i);
            SrsCard srsCard = new SrsCard();
            for (int j = 0; j < cardData.size(); j++) {
                if (currentCard.hashCode() == cardData.get(j).getHashCode()) {
                    srsCard = new SrsCard(currentCard, cardData.get(j), lesson);
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
    public List<CardSrsData> updateCardData() {
        List<CardSrsData> updatedCardData = new ArrayList<>();
        HashMap<SrsCard, Integer> memoryBoxes = new HashMap<>();
        for (int i = 0; i < quizInformation.size(); i++) { //terminal value to be changed
            int currentHashCode = srsCards.get(i).getHashcode();
            int currentNumOfAttempts = srsCards.get(i).getNumOfAttempts() + quizInformation.get(i).get(1);
            int currentStreak = srsCards.get(i).getStreak() + quizInformation.get(i).get(2);

            Instant currentSrsDueDate = Instant.now();
            int currentLevel = memoryBoxes.get(srsCards.get(i));
            if (quizInformation.get(i).get(1).equals(quizInformation.get(i).get(2)) && currentLevel != 5) {
                memoryBoxes.put(srsCards.get(i), currentLevel + 1);
            } else if (quizInformation.get(i).get(1) > quizInformation.get(i).get(2) && currentLevel != 1) {
                memoryBoxes.put(srsCards.get(i), currentLevel - 1);
            }

            if (memoryBoxes.get(srsCards.get(i)) == 1) {
                currentSrsDueDate.plus(Duration.ofHours(1));
            } else if (memoryBoxes.get(srsCards.get(i)) == 2) {
                currentSrsDueDate.plus(Duration.ofHours(5));
            } else if (memoryBoxes.get(srsCards.get(i)) == 3) {
                currentSrsDueDate.plus(Duration.ofHours(12));
            } else if (memoryBoxes.get(srsCards.get(i)) == 4) {
                currentSrsDueDate.plus(Duration.ofHours(24));
            } else if (memoryBoxes.get(srsCards.get(i)) == 5) {
                currentSrsDueDate.plus(Duration.ofHours(48));
            }

            updatedCardData.add(new CardSrsData(currentHashCode, currentNumOfAttempts, currentStreak,
                    currentSrsDueDate));
        }

        return updatedCardData;
    }
}
