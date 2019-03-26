package seedu.address.model.session;

import static java.time.temporal.ChronoUnit.HOURS;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;

/**
 * Represents a management of cards manager.
 */
public class SrsCardsManager {
    private Lesson lesson;
    private HashMap<Integer, CardSrsData> cardData;
    private List<SrsCard> srsCards;
    private List<List<Integer>> quizInformation;
    private Instant currentDate;
    public SrsCardsManager(Lesson lesson, HashMap<Integer, CardSrsData> cardData) {
        requireAllNonNull(lesson, cardData);
        this.lesson = lesson;
        this.cardData = cardData;
    }

    public SrsCardsManager(List<SrsCard> srsCards, List<List<Integer>> quizInformation, Instant currentDate) {
        requireAllNonNull(srsCards, quizInformation);
        this.quizInformation = quizInformation;
        this.srsCards = srsCards;
        this.currentDate = currentDate;
    }

    /**
     * Sorts all cards in this lesson based on their srsDueDate.
     */
    public List<SrsCard> sort() {
        List<Card> cards = lesson.getCards();
        List<SrsCard> srsCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            SrsCard srsCard;
            Card currentCard = cards.get(i);
            if (!cardData.containsKey(currentCard.hashCode())) {
                srsCard = new SrsCard(currentCard, new CardSrsData(currentCard.hashCode(),
                        0, 0, Instant.now()) , lesson);
            } else {
                srsCard = new SrsCard(currentCard, cardData.get(currentCard.hashCode()), lesson);
            }
            Instant currentSrsDueDate = srsCard.getSrsDueDate();
            int currentSize = srsCards.size();
            int update = 0;
            if (srsCards.size() == 0) {
                srsCards.add(srsCard);
            } else {
                for (int k = 0; k < currentSize; k++) {
                    if (currentSrsDueDate.compareTo(srsCards.get(k).getSrsDueDate()) < 0) {
                        srsCards.add(k, srsCard);
                        update = 1;
                        break;
                    }
                }
                if (update == 0) {
                    srsCards.add(srsCard);
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
        for (int i = 0; i < quizInformation.size(); i++) {
            Instant srsDueDate = srsCards.get(i).getSrsDueDate();
            if (srsDueDate.compareTo(currentDate) < 0) {
                memoryBoxes.put(srsCards.get(i), 1);
            } else if (currentDate.until(srsDueDate, HOURS) < 1) {
                memoryBoxes.put(srsCards.get(i), 2);
            } else if (currentDate.until(srsDueDate, HOURS) < 5) {
                memoryBoxes.put(srsCards.get(i), 3);
            } else if (currentDate.until(srsDueDate, HOURS) < 12) {
                memoryBoxes.put(srsCards.get(i), 4);
            } else {
                memoryBoxes.put(srsCards.get(i), 5);
            }

            int currentHashCode = srsCards.get(i).getHashcode();
            int currentNumOfAttempts = srsCards.get(i).getNumOfAttempts() + quizInformation.get(i).get(1);
            int currentStreak = srsCards.get(i).getStreak() + quizInformation.get(i).get(2);

            int currentLevel = memoryBoxes.get(srsCards.get(i));
            if (quizInformation.get(i).get(1).equals(quizInformation.get(i).get(2)) && currentLevel != 5) {
                memoryBoxes.put(srsCards.get(i), currentLevel + 1);
            } else if (quizInformation.get(i).get(1) > quizInformation.get(i).get(2) && currentLevel != 1) {
                memoryBoxes.put(srsCards.get(i), currentLevel - 1);
            }

            Instant updatedSrsDueDate = Instant.MIN;
            if (memoryBoxes.get(srsCards.get(i)) == 1) {
                updatedSrsDueDate = currentDate.plus(Duration.ofHours(1));
            } else if (memoryBoxes.get(srsCards.get(i)) == 2) {
                updatedSrsDueDate = currentDate.plus(Duration.ofHours(5));
            } else if (memoryBoxes.get(srsCards.get(i)) == 3) {
                updatedSrsDueDate = currentDate.plus(Duration.ofHours(12));
            } else if (memoryBoxes.get(srsCards.get(i)) == 4) {
                updatedSrsDueDate = currentDate.plus(Duration.ofHours(24));
            } else if (memoryBoxes.get(srsCards.get(i)) == 5) {
                updatedSrsDueDate = currentDate.plus(Duration.ofHours(48));
            }

            updatedCardData.add(new CardSrsData(currentHashCode, currentNumOfAttempts, currentStreak,
                    updatedSrsDueDate));
        }
        return updatedCardData;
    }
}
