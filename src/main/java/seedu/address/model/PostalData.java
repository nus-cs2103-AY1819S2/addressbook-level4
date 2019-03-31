package seedu.address.model;

/**
 * Represents the a memory model of a postal code.
 */
public class PostalData {

    private final int postal;
    private final double x;
    private final double y;

    public PostalData(int postal, double x, double y) {
        this.postal = postal;
        this.x = x;
        this.y = y;
    }

    public int getPostal() {
        return this.postal;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }


}
