package seedu.address.model.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Objects;

import org.junit.Test;

import seedu.address.model.cell.Address;
import seedu.address.model.tag.Tag;
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
    public void testDefaultConstructors() {
        Battleship battleshipOne = new Battleship();
        Battleship battleshipTwo = new Battleship(new Name("destroyer"), new HashSet<>());
        Battleship battleshipThree = new Battleship(new Name("destroyer"), 2, 2);
        Battleship battleshipFour = new Battleship(new Name("destroyer"));

        HashSet<Tag> emptySet = new HashSet<>();
        Battleship battleshipOneShadow = new Battleship(new Name("placeholder"), 2, 2, emptySet);
        Battleship battleshipTwoShadow = new Battleship(new Name("destroyer"), 2, 2, emptySet);
        Battleship battleshipThreeShadow = new Battleship(new Name("destroyer"), 2, 2, emptySet);
        Battleship battleshipFourShadow = new Battleship(new Name("destroyer"), 2, 2, emptySet);

        assertEquals(battleshipOne, battleshipOneShadow);
        assertEquals(battleshipTwo, battleshipTwoShadow);
        assertEquals(battleshipThree, battleshipThreeShadow);
        assertEquals(battleshipFour, battleshipFourShadow);
    }

    @Test
    public void testToString() {
        Battleship battleship = new Battleship();
        assertEquals(battleship.toString(), "placeholder");
    }

    @Test
    public void testHash() {
        Battleship battleship = new Battleship();
        assertEquals(battleship.hashCode(), Objects.hash(
                battleship.getName(),
                battleship.getId(),
                battleship.getLength(),
                battleship.getLife(),
                battleship.getTags()
        ));
    }

    @Test
    public void testIsSameBattleship() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship();
        Battleship battleshipTwo = new DestroyerBattleship();
        Battleship battleshipThree = battleshipOne;

        // test equality
        assertFalse(battleshipOne.isSameBattleship(battleshipTwo));
        assertTrue(battleshipOne.isSameBattleship(battleshipThree));
    }

    @Test
    public void testDifferentNameDifferentBattleship() {

        // create variables
        Battleship battleshipOne = new DestroyerBattleship();;
        Battleship battleshipTwo = new CruiserBattleship();

        // test equality
        assertFalse(battleshipOne.equals(battleshipTwo));
    }

    @Test
    public void testSameNameDifferentBattleship() {

        // create variables
        Battleship battleshipOne = new DestroyerBattleship();
        Battleship battleshipTwo = new DestroyerBattleship();

        // test equality
        assertTrue(battleshipOne.equals(battleshipTwo));
    }

    @Test
    public void testSameNameSameBattleship() {

        // create variables
        Battleship battleshipOne = new DestroyerBattleship();
        Battleship battleshipTwo = battleshipOne;

        // test equality
        assertTrue(battleshipOne.equals(battleshipTwo));
    }

    @Test
    public void testBattleshipLength() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship();
        assertEquals(battleshipOne.getLength(), 3);
    }

    @Test
    public void testBattleshipLife() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship();
        assertEquals(battleshipOne.getLife(), 3);
    }

    @Test
    public void testBattleshipReduceLife() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship();
        int initialLife = battleshipOne.getLife();

        battleshipOne.reduceLife();

        assertEquals(battleshipOne.getLife(), initialLife - 1);
    }

    @Test
    public void testBattleshipDestroyed() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship();
        int initialLife = battleshipOne.getLife();

        for (int i = 0; i < initialLife; i++) {
            battleshipOne.reduceLife();
        }

        assertEquals(battleshipOne.isDestroyed(), true);
    }

    @Test
    public void testAicraftCarrier() {
        Battleship aircraftCarrier = new AircraftCarrierBattleship();
        assertEquals(aircraftCarrier.getLength(), 5);
    }

    @Test
    public void testDestroyer() {
        Battleship destroyerBattleship = new DestroyerBattleship();
        assertEquals(destroyerBattleship.getLength(), 3);
    }

    @Test
    public void testCruiser() {
        Battleship cruiserBattleship = new CruiserBattleship();
        assertEquals(cruiserBattleship.getLength(), 2);
    }
}
