package seedu.address.model.statistics;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.scene.chart.XYChart;
import seedu.address.battle.result.AttackResult;
import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.Storage;

/**
 * A Statistics Class tracks the key game play information.
 * @author bos10
 */
public abstract class Statistics {

    private static final Logger logger = LogsCenter.getLogger(Statistics.class);
    protected int hitCount;
    protected int missCount;
    protected int movesMade;
    protected int enemyShipsDestroyed;
    protected int attackCount;
    protected Storage storage;


    /**
     * initialization of class will begin with default configuration.
     */
    public Statistics() {
        defaultConfig();
    }
    /**
     * assigns the class attributes with default values of a NEW game
     */
    private void defaultConfig() {
        logger.info("Statistics Initialized");
        this.hitCount = 0;
        this.missCount = 0;
        this.enemyShipsDestroyed = 0;
        this.attackCount = 0;
        this.movesMade = 0;
    }

    /**
     * increments the hit count by 1.
     * @return incremented hitCount
     */
    public int addHit() {
        ++this.hitCount;
        logger.info("Increment Successful Hit to " + this.hitCount);
        return this.hitCount;
    }

    /**
     * increments the num of enemy ships destroyed by 1.
     * @return number of enemy ships destroyed.
     */
    public int enemyShipsDestroyed() {
        ++this.enemyShipsDestroyed;
        logger.info("Increment Ships Destroyed to " + this.enemyShipsDestroyed);
        return this.enemyShipsDestroyed;
    }

    /**
     * @return the number of enemy Ships destroyed by player
     */
    public int getEnemyShipsDestroyed() {
        return this.enemyShipsDestroyed;
    }
    /**
     * increments the miss count by 1.
     * @return incremented missCount
     */
    public int addMiss() {
        ++this.missCount;
        logger.info("Increment Miss to " + this.missCount);
        return this.missCount;
    }

    /**
     * decrements the number of moves left.
     * @return the decremented number of moves
     */
    public int addMove() {
        ++this.movesMade;
        logger.info("Increment moves to : " + this.movesMade);
        return this.movesMade;
    }

    /**
     * increments number of attack by 1.
     * @return the current number of attacks.
     */
    public int addAttack() {
        ++this.attackCount;
        logger.info("Increment attacks made to  " + this.attackCount);
        return this.attackCount;
    }
    public int getAttacksMade() {
        return this.attackCount;
    }
    public int getMovesMade() {
        return this.movesMade;
    }

    public int getHitCount() {
        return this.hitCount;
    }
    public int getMissCount() {
        return this.missCount;
    }
    /**
     * returns the hit-miss percentage of the user.
     */
    public double getAccuracy() {
        if (hitCount == 0 && missCount == 0) {
            return 0;
        }
        return (double) hitCount / (double) (hitCount + missCount);
    }

    public void setHitCount(int num) {
        this.hitCount = num;
    }

    public void setMissCount(int num) {
        this.missCount = num;
    }

    public void setMovesMade(int num) {
        this.movesMade = num;
    }

    public void setEnemyShipsDestroyed(int num) {
        this.enemyShipsDestroyed = num;
    }

    public void setAttackCount(int num) {
        this.attackCount = num;
    }

    /**
     * extracts the result from AttackResult string and add to stats.
     * @param res , the result of the attack made.
     */
    public void addResultToStats(AttackResult res) {
        if (res.isHit()) {
            addHit();
            if (res.isDestroy()) {
                enemyShipsDestroyed();
            }
        } else {
            addMiss();
        }
    }

    /**
     * This will generate the required data format for the bar charts.
     * @return the formatted data.
     */
    public XYChart.Series<String, Number> generateData() {
        logger.info("Generating Statistical Data");
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
        dataSeries1.getData().add(new XYChart.Data<>("Attacks", getAttacksMade()));
        dataSeries1.getData().add(new XYChart.Data<>("Hits", getHitCount()));
        dataSeries1.getData().add(new XYChart.Data<>("Misses", getMissCount()));
        dataSeries1.getData().add(new XYChart.Data<>("Ships Destroyed", getEnemyShipsDestroyed()));
        return dataSeries1;
    }

    public Storage getStorage() {
        return this.storage;
    }

    /**
     * This will store the statisticsData into Storage component
     * @param statisticsData
     * @throws IOException
     */
    public void saveToStorage(PlayerStatistics statisticsData) throws IOException {
        logger.info("Saving Statistics Data to Storage");
        this.storage.saveStatisticsData(statisticsData);
    }

    /**
     * Set the storage location inside the PlayerStatistics Object.
     * @param storage
     */
    public void setStorage(Storage storage) {
        logger.info("Set Storage Location for Statistics");
        this.storage = storage;
    }

    /**
     * Resets the statistics data with the default configuration.
     */
    public void resetData() {
        logger.info("Rest Data");
        defaultConfig();
    }
}
