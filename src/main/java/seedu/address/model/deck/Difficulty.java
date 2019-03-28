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
    public void addDifficulty(int rating) {
        totalRating += rating;
        numberOfAttempts += 1;
    }

    public int getDifficulty() {return totalRating / numberOfAttempts};


}
