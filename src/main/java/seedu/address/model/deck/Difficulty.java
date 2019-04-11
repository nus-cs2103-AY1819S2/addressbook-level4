package seedu.address.model.deck;

/**
 * Represents a card's difficulty.
 */
public class Difficulty {

    /**
     * Constructs a {@code Difficulty}.
     */
    private int numberOfAttempts;
    private int totalRating;

    public Difficulty() {
        numberOfAttempts = 0;
        totalRating = 0;
    }

    public Difficulty(int numberOfAttempts, int totalRating) {
        this.totalRating = totalRating;
        this.numberOfAttempts = numberOfAttempts;
    }

    /**
     * Includes the current rating into
     * the Difficulty class which computes
     * average difficulty of card.
     */
    public void addDifficulty(int rating) {
        totalRating += rating;
        numberOfAttempts += 1;
    }

    public double getDifficulty() {
        return numberOfAttempts == 0 ? 0 : (double) totalRating / (double) numberOfAttempts;
    }

    public int getTotalRating() {
        return this.totalRating;
    }

    public int getNumberOfAttempts() {
        return this.numberOfAttempts;
    }
}
