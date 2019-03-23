package seedu.address.model.statistics;
/**
 * A Statistics Class tracks the key gameplay information.
 */
public abstract class Statistics {

    protected static int hitCount;
    protected static int missCount;
    protected static int movesLeft;
    protected static int enemyShipDestroyed;
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
        this.enemyShipDestroyed = 0;
        this.movesLeft = 10; // magic number for now
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
    public int enemyShipDestroyed() {
        ++this.enemyShipDestroyed;
        return this.enemyShipDestroyed;
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

}
