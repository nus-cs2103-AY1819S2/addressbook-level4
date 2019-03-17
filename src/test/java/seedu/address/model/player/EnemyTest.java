package seedu.address.model.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EnemyTest {

    public final Enemy testPlayer = new Enemy();

    //Lucy TO-FIX:
    /*  @Test

    public void constructor_default() {
        Player newPlayer1 = new Player();
        Player newPlayer2 = new Player("Player1", 5);
        assertEquals(newPlayer1, newPlayer2);
    }
    */

    @Test
    public void test_getName() {
        assertEquals("EnemyPlayer", testPlayer.getName());
    }

    @Test
    public void test_getFleetSize() {
        assertEquals(8, testPlayer.getFleetSize());
    }

    @Test
    public void test_getFleetContents() {
        Player newPlayer1 = new Player("Bob", 5, 2, 1);
        assertEquals(newPlayer1.getFleetContents(), testPlayer.getFleetContents());
    }

    @Test
    public void test_getMapGrid() {
        Player newPlayer1 = new Player("Bob", 5, 2, 1);
        assertEquals(newPlayer1.getMapGrid(), testPlayer.getMapGrid());
    }

    @Test public void test_getTargetHistory() {
        Player newPlayer1 = new Player("Bob", 5, 2, 1);
        assertEquals(newPlayer1.getTargetHistory(), testPlayer.getTargetHistory());
    }
}
