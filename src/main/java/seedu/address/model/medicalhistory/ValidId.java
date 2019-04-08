package seedu.address.model.medicalhistory;

/**
 * Represents valid id of patient and doctor in medical history.
*/
public class ValidId {
    public static final String MESSAGE_CONSTRAINTS =
            "A id for patient or doctor must be an integer larger than 0";

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidId(int id) {
        if (id > 0) {
            return true;
        }
        return false;
    }
}
