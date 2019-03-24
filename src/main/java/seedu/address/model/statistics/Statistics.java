package seedu.address.model.statistics;
import javafx.scene.chart.XYChart;
/**
 * A Statistics Class tracks the key gameplay information.
 */
public abstract class Statistics {

    protected static int hitCount;
    protected static int missCount;
    protected static int movesLeft;
    protected static int enemyShipsDestroyed;
    protected static int attackCount;
    protected static final int TOTAL_MOVES = 10;
    // private int shipsLeft;
    // private int hatTricks // 3 in a row

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
        this.hitCount = 0;
        this.missCount = 0;
        this.enemyShipsDestroyed = 0;
        this.attackCount = 0;
        this.movesLeft = TOTAL_MOVES;
    }

    /**
     * increments the hit count by 1.
     * @return incremented hitCount
     */
    public int addHit() {
        ++this.hitCount;
        return this.hitCount;
    }

    /**
     * increments the num of enemy ships destroyed by 1.
     * @return number of enemy ships destroyed.
     */
    public int enemyShipsDestroyed() {
        ++this.enemyShipsDestroyed;
        return this.enemyShipsDestroyed;
    }

    public int getEnemyShipsDestroyed(){
        return this.enemyShipsDestroyed;
    }
    /**
     * increments the miss count by 1.
     * @return incremented missCount
     */
    public int addMiss() {
        ++this.missCount;
        return this.missCount;
    }

    /**
     * decrements the number of moves left.
     * @return the decremented number of moves
     */
    public int minusMove() {
        --this.movesLeft;
        return this.movesLeft;
    }

    public int addAttack(){
        ++this.attackCount;
        return this.attackCount;
    }
    public int getAttacksMade(){
        return this.attackCount;
    }
    public int getMovesLeft() {
        return this.movesLeft;
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
        return (double) hitCount / (double) (hitCount + missCount);
    }

    // ADD NEW METHOD TO GENERATE STATS DATA GRAPHS
    public XYChart.Series generateData() {
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.getData().add(new XYChart.Data("Attacks", getAttacksMade()));
        dataSeries1.getData().add(new XYChart.Data("Hits", getHitCount()));
        dataSeries1.getData().add(new XYChart.Data("Misses", getMissCount()));
        //dataSeries1.getData().add(new XYChart.Data("Accuracy", getMissCount()));
        dataSeries1.getData().add(new XYChart.Data("Ships Destroyed", getEnemyShipsDestroyed()));
        return dataSeries1;
    }

}
