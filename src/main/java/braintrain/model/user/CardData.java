package braintrain.model.user;

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
     * Takes in the hash, number of attempmts, streak and the duedate
     * @param hash
     * @param attempts
     * @param consecutive
     * @param date
     */
    public CardData(int hash, int attempts, int consecutive, Instant date) {
        hashCode = hash;
        numOfAttempts = attempts;
        streak = consecutive;
        srsDueDate = date;
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

    public void setNumOfAttempts(int numOfAttempts) {
        this.numOfAttempts += numOfAttempts;
    }

    public Instant setSrsDueDate(Instant srsDueDate) {
        return srsDueDate;
    }
}
