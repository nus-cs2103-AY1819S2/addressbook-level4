package seedu.address.model.equipment;

/**
 * Representing the worklistid and the id is increasing based on the id history.
 */
public class WorkListID {

    private static int idHito = 0;

    private int thisId;

    /**
     * Constructing the class, and pass down the ID number.
     */
    public WorkListID() {
        thisID = IDhis + 1;
    }

    public int getId() {
        return this.thisId;
    }
}
