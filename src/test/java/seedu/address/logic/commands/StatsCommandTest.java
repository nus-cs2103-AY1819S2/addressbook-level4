package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.statistics.PlayerStatistics;

/**
 * The StatsCommandTest will test the methods of the statistics class
 */
public class StatsCommandTest {

    @Test
    public void test_addHit() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(1, p1.addHit());
        assertEquals(2, p1.addHit());
    }

    @Test
    public void test_addMiss() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(1, p1.addMiss());
        assertEquals(2, p1.addMiss());
    }

    @Test
    public void test_minusMove() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(9, p1.minusMove());
        assertEquals(8, p1.minusMove());
    }

    @Test
    public void test_getMovesLeft() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(10, p1.getMovesLeft());
        p1.minusMove();
        assertEquals(9, p1.getMovesLeft());
    }

    @Test
    public void test_getHitCount() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, p1.getHitCount());
        p1.addHit();
        assertEquals(1, p1.getHitCount());
    }

    @Test
    public void test_getMissCount() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, p1.getMissCount());
        p1.addMiss();
        assertEquals(1, p1.getMissCount());
    }

    @Test
    public void test_getAccuracy() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, (int) p1.getAccuracy());
        p1.addAttack();
        p1.addHit();
        assertEquals(1, (int) p1.getAccuracy());
    }

    @Test
    public void test_getEnemyShipsDestroyed() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, (int) p1.getEnemyShipsDestroyed());
    }
}