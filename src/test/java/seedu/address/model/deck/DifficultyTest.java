package seedu.address.model.deck;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;

import org.assertj.core.internal.Diff;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.CardBuilder;

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
