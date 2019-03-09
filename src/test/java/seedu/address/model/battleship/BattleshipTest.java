package seedu.address.model.battleship;

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
}
