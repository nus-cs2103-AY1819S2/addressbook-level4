package seedu.address.model.equipment;

public class WorkListID {

    private static int IDhis = 0;

    private int thisID;

    /**
     * Constructing the class, and pass down the ID number.
     */
    public WorkListID() {
        thisID = IDhis + 1;
    }

    public int getID() {
        return this.thisID;
    }
}
