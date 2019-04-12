package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_LACK_FILTERNAME = "Filter Command need a name\n%1$s";
    public static final String MESSAGE_INFORMATION_WITHOUT_PREFIX =
        "All information need a prefix for this command. \n%1$s";
    public static final String MESSAGE_LACK_NAME = "Name field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_ADDRESS = "Address field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_EMAIL = "Email field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_GENDER = "Gender field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_GRADE = "Grade field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_JOBSAPPLY = "Jobs Apply field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_MAJOR = "Major field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_NRIC = "Nric field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_PHONE = "Phone field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_RACE = "Race field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_SCHOOL = "School field should not be empty.\n%1$s";

    public static final String MESSAGE_LACK_LISTNAME =
        "Filter Command in Display Job page need indicate job list\n%1$s";
    public static final String MESSAGE_REDUNDANT_LISTNAME =
        "Filter Command in All Jobs page no need indicate job list";
    public static final String MESSAGE_INVALID_RANGE =
        "Not a valid range, the right format should be value-value;value-value..." + "\n" +
            "For example: 1.2-1.3; 1.3-1.4\n%1$s";
    public static final String MESSAGE_CANOT_FOUND_TARGET_FILTER = "The filter you want to delete can not found";
    public static final String MESSAGE_REDUNDANT_FILTERNAME = "Filter name has already been used." + "\n" +
        "Filter Command need a unique name";

}
