package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.battle.AttackDestroyedShip;
import seedu.address.logic.battle.AttackHit;
import seedu.address.logic.battle.AttackMissed;
import seedu.address.logic.battle.AttackResult;
import seedu.address.model.player.Player;
import seedu.address.model.statistics.PlayerStatistics;
import seedu.address.testutil.TypicalIndexes;

/**
 * The StatisticsTest will test the methods of the statistics class.
 * Contains integration tests (interaction with Model, Player, Storage and Battleship commands)
 * @author bos10
 */
public class StatisticsTest {
    private PlayerStatistics p1;
    private Player alice;
    private Player bob;

    @Before
    public void setUp() {
        p1 = new PlayerStatistics();
        alice = new Player("Alice", 5, 2, 1);
        bob = new Player("Bob", 5, 2, 1);
    }

    @Test
    public void execute_addHit_success() {
        assertEquals(1, p1.addHit());
        assertEquals(2, p1.addHit());
    }

    @Test
    public void execute_addMiss_success() {
        assertEquals(1, p1.addMiss());
        assertEquals(2, p1.addMiss());
    }

    @Test
    public void execute_addMove_success() {
        assertEquals(1, p1.addMove());
        assertEquals(2, p1.addMove());
    }

    @Test
    public void execute_getMovesLeft_success() {
        assertEquals(0, p1.getMovesMade());
        p1.addMove();
        assertEquals(1, p1.getMovesMade());
    }

    @Test
    public void execute_getHitCount_success() {
        assertEquals(0, p1.getHitCount());
        p1.addHit();
        assertEquals(1, p1.getHitCount());
    }

    @Test
    public void execute_getMissCount_success() {
        assertEquals(0, p1.getMissCount());
        p1.addMiss();
        assertEquals(1, p1.getMissCount());
    }

    @Test
    public void execute_getAccuracy_success() {
        assertEquals(0, (int) p1.getAccuracy());
        p1.addAttack();
        p1.addHit();
        assertEquals(1, (int) p1.getAccuracy());
    }

    @Test
    public void execute_getEnemyShipsDestroyed_success() {
        assertEquals(0, p1.getEnemyShipsDestroyed());
    }

    @Test
    public void execute_addResultToStats_hitSuccess() {
        AttackResult res = new AttackHit(alice, bob, TypicalIndexes.COORDINATES_A2);
        p1.addResultToStats(res);
        assertEquals(p1.getHitCount(), 1);
    }

    @Test
    public void execute_addResultToStats_missSuccess() {
        AttackResult res = new AttackMissed(alice, bob, TypicalIndexes.COORDINATES_A2);
        p1.addResultToStats(res);
        assertEquals(p1.getMissCount(), 1);
    }

    @Test
    public void execute_addResultToStats_attackDestroyedSuccess() {
        AttackResult res = new AttackDestroyedShip(alice, bob, TypicalIndexes.COORDINATES_A2, "name");
        p1.addResultToStats(res);
        assertEquals(p1.getHitCount(), 1);
        assertEquals(p1.getEnemyShipsDestroyed(), 1);
    }

    @Test
    public void generateData() {
        //TODO: STUB TEST
        p1.generateData();
        assertTrue(true);
    }

    // STORAGE COMPONENT FOR STATS //

    // take in statsData void
    @Test
    public void execute_saveToStorage_success(){
        // WRITE TESTS
    }

    @Test
    public void execute_setStorage_success(){
        // WRITE TESTS
    }
}
