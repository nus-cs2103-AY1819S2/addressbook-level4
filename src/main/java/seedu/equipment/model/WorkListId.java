package seedu.equipment.model;

import static java.util.Objects.requireNonNull;

import seedu.equipment.commons.util.AppUtil;

/**
 * Representing the worklistid and the id is increasing based on the id history.
 */
public class WorkListId {

    //private static int idHist = 0;

    //private int thisId;

    public static final String MESSAGE_CONSTRAINTS =
            "WorkList id should only contain numbers, and it should be at least 1 digits long and should "
                    + "not begin with 0";
    public static final String VALIDATION_REGEX = "[1-9]\\d{0,}";
    public final String value;

    /**
     * Constructing the class, and pass down the ID number.
     */
    //public WorkListId() {
    //    thisId = idHist + 1;
    //    idHist++;
    //}

    /**
     * Constructing the class, and pass the ID number.
     */
    public WorkListId(String id) {
        requireNonNull(id);
        AppUtil.checkArgument(isValidWorkListId(id), MESSAGE_CONSTRAINTS);
        value = id;
    }

    /**
     * Returns true if a given WorkListId is a valid WorkListId.
     */
    public static boolean isValidWorkListId(String test) {
        //System.out.println("This id " + test + " is " + test.matches(VALIDATION_REGEX));
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both WorkListIds have the same id.
     * This defines a stronger notion of equality between two WorkListIds.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkListId // instanceof handles nulls
                && value.equals(((WorkListId) other).value)); // state check
    }

    /**
     * Change the String value to integer.
     * @return integer equals to value, throw exception is cannot convert.
     */
    public int getIntId() throws NumberFormatException {
        return Integer.parseInt(this.value);
    }
}
