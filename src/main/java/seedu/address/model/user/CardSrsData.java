package seedu.address.model.user;

import java.time.Instant;
import java.util.HashMap;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.CsvLessonsStorage;

/**
 * Represents a Card which contains the data and hashcode that can be called by session
 */
public class CardSrsData {
    private static final Logger logger = LogsCenter.getLogger(CsvLessonsStorage.class);
    private int hashCode; // This corresponds to a Card's hashCode
    private int numOfAttempts; // Number of attempts for this Card by a User
    private int streak;
    private Instant srsDueDate; // SRS Due Date for a User
    private HashMap <Integer, CardSrsData> cardMap = new HashMap<>();

    /**
     * Constructor for the CardData
     * Takes in the hashCode, numOfAttempts, streak and the srsDueDate
     * @param hashCode this corresponds to a Card's hashCode
     * @param numOfAttempts this corresponds to the number of attempts
     * @param streak this corresponds to the number of correct answers consecutively
     * @param srsDueDate this corresponds to the space repetition storage due date
     */
    public CardSrsData(int hashCode, int numOfAttempts, int streak, Instant srsDueDate) {

        if (hashCode < 0) {
            logger.warning("Card Hashcode provided cannot be negative. Setting it to -1.");
            hashCode = -1;
        }

        this.hashCode = hashCode;

        if (numOfAttempts < 0) {
            logger.warning("Number of attempts provided cannot be negative. Setting it to -1.");
            numOfAttempts = -1;
        }

        this.numOfAttempts = numOfAttempts;

        if (streak < 0) {
            logger.warning("Streak provided cannot be negative. Setting it to -1.");
            streak = -1;
        }

        this.streak = streak;

        if (srsDueDate.toString().equals(null)) {
            logger.warning("SrsDueDate provided cannot be null. Setting it to null");
            srsDueDate = null;
        }
        if (srsDueDate.toString().equals("")) {
            logger.warning("SrsDueDate provided cannot be empty. Setting it to null");
            srsDueDate = null;
        }

        this.srsDueDate = srsDueDate;
    }
    /**
     * Function for session management to get list of card datas
     * @param hashCode must be given
     * @return card from hashmap
     */
    public CardSrsData getCard(int hashCode) {
        return cardMap.get(hashCode);
    }

    public void setCard(int hashCode, CardSrsData values) {
        cardMap.put(hashCode, values);
    }

    public int getHashCode() {
        return hashCode;
    }

    public int getNumOfAttempts() {
        return numOfAttempts;
    }

    public void setNumOfAttempts(int numOfAttempts) {
        this.numOfAttempts = numOfAttempts;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }
    /*@Override
    public boolean equals(Object obj) {
        CardSrsData other = (CardSrsData) obj;
        return other.getHashCode() == this.getHashCode();
    }*/
    public Instant getSrsDueDate() {
        return srsDueDate;
    }
}
