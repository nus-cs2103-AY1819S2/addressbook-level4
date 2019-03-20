package seedu.address.model;

/**
 * Representing the worklistid and the id is increasing based on the id history.
 */
public class WorkListId {

    private static int idHist = 0;

    private int thisId;

    /**
     * Constructing the class, and pass down the ID number.
     */
    public WorkListId() {
        thisId = idHist + 1;
    }

    public int getId() {
        return this.thisId;
    }
}
