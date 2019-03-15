package seedu.address.model.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.cell.Address;
import seedu.address.testutil.Assert;

public class BattleshipTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void testIsSameBattleship() {
        // create variables
        Battleship battleshipOne = new Battleship(new Name("one"));
        Battleship battleshipTwo = new Battleship(new Name("one"));
        Battleship battleshipThree = battleshipOne;

        // test equality
        assertFalse(battleshipOne.isSameBattleship(battleshipTwo));
        assertTrue(battleshipOne.isSameBattleship(battleshipThree));
    }

    @Test
    public void testDifferentNameDifferentBattleship() {

        // create variables
        Battleship battleshipOne = new Battleship(new Name("one"));
        Battleship battleshipTwo = new Battleship(new Name("two"));

        // test equality
        assertFalse(battleshipOne.equals(battleshipTwo));
    }

    @Test
    public void testSameNameDifferentBattleship() {

        // create variables
        Battleship battleshipOne = new Battleship(new Name("one"));
        Battleship battleshipTwo = new Battleship(new Name("one"));

        // test equality
        assertTrue(battleshipOne.equals(battleshipTwo));
    }

    @Test
    public void testSameNameSameBattleship() {

        // create variables
        Battleship battleshipOne = new Battleship(new Name("one"));
        Battleship battleshipTwo = battleshipOne;

        // test equality
        assertTrue(battleshipOne.equals(battleshipTwo));
    }

    @Test
    public void testBattleshipLength() {
        // create variables
        Battleship battleshipOne = new Battleship(new Name("one"));
        assertEquals(battleshipOne.getLength(), 2);
    }

    @Test
    public void testBattleshipLife() {
        // create variables
        Battleship battleshipOne = new Battleship(new Name("one"));
        assertEquals(battleshipOne.getLife(), 2);
    }

    @Test
    public void testBattleshipReduceLife() {
        // create variables
        Battleship battleshipOne = new Battleship(new Name("one"));
        int initialLife = battleshipOne.getLife();

        battleshipOne.reduceLife();

        assertEquals(battleshipOne.getLife(), initialLife - 1);
    }

    @Test
    public void testBattleshipDestroyed() {
        // create variables
        Battleship battleshipOne = new Battleship(new Name("one"));
        int initialLife = battleshipOne.getLife();

        for (int i = 0; i < initialLife; i++) {
            battleshipOne.reduceLife();
        }

        assertEquals(battleshipOne.isDestroyed(), true);

    }
}
