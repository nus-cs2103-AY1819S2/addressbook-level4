package seedu.equipment.model;

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
        idHist++;
    }

    /**
     * Returns true if both WorkListIds have the same id.
     * This defines a stronger notion of equality between two WorkListIds.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof WorkListId)) {
            return false;
        }

        WorkListId otherWorkListId = (WorkListId) other;
        return otherWorkListId.getId() == getId();
    }

    public int getId() {
        return this.thisId;
    }
}
