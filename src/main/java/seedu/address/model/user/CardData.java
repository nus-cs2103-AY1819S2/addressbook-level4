package seedu.address.model.user;

import java.time.Instant;

/**
 * Represents a Card which contains the data and hashcode that can be called by session
 */
public class CardData {
    private int hashCode; // This corresponds to a Card's hashCode
    private int numOfAttempts; // Number of attempts for this Card by a User
    private int streak;
    private Instant srsDueDate; // SRS Due Date for a User

    /**
     * Constructor for the CardData
     * Takes in the hashCode, numOfAttempts, streak and the srsDueDate
     * @param hashCode this corresponds to a Card's hashCode
     * @param numOfAttempts this corresponds to the number of attempts
     * @param streak this corresponds to the number of correct answers consecutively
     * @param srsDueDate this corresponds to the space repetition storage due date
     */
    public CardData(int hashCode, int numOfAttempts, int streak, Instant srsDueDate) {
        this.hashCode = hashCode;
        this.numOfAttempts = numOfAttempts;
        this.streak = streak;
        this.srsDueDate = srsDueDate;
    }

    public int getHashCode() {
        return hashCode;
    }

    public int getnumOfAttempts() {
        return numOfAttempts;
    }

    public Instant getSrsDueDate() {
        return srsDueDate;
    }

    public int getNumOfAttempts() {
        return numOfAttempts;
    }

    public void setNumOfAttempts(int numOfAttempts) {
        this.numOfAttempts = numOfAttempts;
    }

    public void updateNumOfAttempts(int numOfAttempts) {
        this.numOfAttempts += numOfAttempts;
    }

    public int getStreak() {
        return streak;
    }
    public void setStreak(int streak) {
        this.streak = streak;
    }
}
