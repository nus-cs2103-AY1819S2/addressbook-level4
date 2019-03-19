package seedu.address.model;

/**
 * Represents the a memory model of a postal code.
 */
public class PostalData {

    private final String postal;
    private final String x;
    private final String y;

    public PostalData(String postal, String x, String y) {
        this.postal = postal;
        this.x = x;
        this.y = y;
    }
}
