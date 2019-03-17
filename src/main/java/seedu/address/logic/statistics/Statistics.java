package seedu.address.logic.statistics;

public abstract class Statistics {

    protected int hitCount;
    protected int missCount;
    protected int movesLeft;
    // private int shipsLeft;
    // private int hatTricks // 3 in a row

    public Statistics() {
        defaultConfig();
    }
    /*
     * assigns the class attributes with default values of a NEW game
     */
    private void defaultConfig() {
        this.hitCount = 0;
        this.missCount = 0;
        this.movesLeft = 10; // magic number for now
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

    public double getAccuracy() {
        return (double) hitCount / (double) hitCount+(double) missCount;
    }

}
