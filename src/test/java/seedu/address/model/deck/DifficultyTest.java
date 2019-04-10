package seedu.address.model.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class DifficultyTest {

    @Test
    public void constructor() {
        Difficulty difficulty = new Difficulty();
        assertEquals(difficulty.getDifficulty(), 0);
        assertEquals(difficulty.getNumberOfAttempts(), 0);
    }

    @Test
    public void constructorWithParams() {
        Difficulty difficulty = new Difficulty(20, 33);
        assertEquals(difficulty.getTotalRating(), 33);
        assertEquals(difficulty.getNumberOfAttempts(), 20);
        assertEquals(difficulty.getDifficulty(), 1.65);
    }

    @Test
    public void addDifficulty_integerRating_success() {
        Difficulty difficulty = new Difficulty(19, 40);
        difficulty.addDifficulty(5);
        assertEquals(difficulty.getTotalRating(), 45);
        assertEquals(difficulty.getNumberOfAttempts(), 20);
        assertEquals(difficulty.getDifficulty(), 2.25);
    }
}
