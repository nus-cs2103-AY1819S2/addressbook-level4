package seedu.address.logic.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.statistics.PlayerStatistics;

/**
 * The StatisticsTest will test the methods of the statistics class
 */
public class StatisticsTest {

    @Test
    public void test_defaultConfig() {
       // PlayerStatistics playerStats1 = new PlayerStatistics();
        //PlayerStatistics playerStats2 = new PlayerStatistics();
        //assertEquals(new PlayerStatistics(), new PlayerStatistics());
    }

    @Test
    public void test_addHit() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(1, p1.addHit()); // Why not working?
    }

    @Test
    public void test_addMiss() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(1, p1.addMiss());
    }

    @Test
    public void test_minusMove() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(9, p1.minusMove());
    }

    @Test
    public void test_getMovesLeft() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(10, p1.getMovesLeft());
    }

    @Test
    public void test_getHitCount() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, p1.getHitCount()); // can add more variation
    }

    @Test
    public void test_getMissCount() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, p1.getMissCount());
    }


}
