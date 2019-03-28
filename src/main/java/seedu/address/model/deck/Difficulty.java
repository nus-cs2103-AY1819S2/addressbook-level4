package seedu.address.model.deck;

/**
 * Represents a card's difficulty.
 */
public class Difficulty {

    /**
     * Constructs a {@code Difficulty}.
     */
    private int numberOfAttempts;
    private int totalDifficulty;

    public Difficulty() {
        numberOfAttempts = 0;
        totalDifficulty = 0;
    }
    public addDifficulty(int rating) {
        totalDifficulty += rating;
        numberOfAttempts += 1;
    }

    public int getDifficulty() {return totalDifficulty / numberOfAttempts};


}
