package seedu.address.logic.parser;

/**
 * Represents the mode for AddPersonCommand, EditPersonCommand and DeletePersonCommand.
 * @author Lookaz
 */
public enum CommandMode {

    HEALTH_WORKER,
    PATIENT,
    REQUEST,
    OTHERS,
    INVALID;

    public static final String MODE_HEALTHWORKER = "1";
    public static final String MODE_PATIENT = "2";
    public static final String MODE_REQUEST = "3";
    public static final String MODE_OTHERS = "4";
    public static final String MODE_INVALID = "5";
}
