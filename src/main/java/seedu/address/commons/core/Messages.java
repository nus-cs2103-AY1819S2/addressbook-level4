package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_LACK_FILTERNAME = "Filter Command need a name";
    public static final String MESSAGE_LACK_LISTNAME = "Filter Command in Display Job page need indicate job list";
    public static final String MESSAGE_REDUNDANT_LISTNAME = "Filter Command in All Jobs page no need indicate job list";
    public static final String MESSAGE_INVALID_RANGE =
            "Not a valid range, the right format should be value-value;value-value..." + "\n"
                    + "For example: 1.2-1.3; 1.3-1.4";
    public static final String MESSAGE_CANOT_FOUND_TARGET_FILTER = "The filter you want to delete can not found";
    public static final String MESSAGE_REDUNDANT_FILTERNAME = "Filter name has already been used." + "\n"
            + "Filter Command need a unique name";

}
