package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command! Type \'help\' to see all commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_INVALID_RECORD_DISPLAYED_INDEX = "The record index provided is invalid.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_RECORDS_LISTED_OVERVIEW = "%1$d records listed!";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid.";
    public static final String MESSAGE_DUPLICATE_TASK = "An exact same task is already found in the records.";
    public static final String MESSAGE_DATE_CLASH = "The task's start date should not be after its end date.";
    public static final String MESSAGE_TIME_CLASH = "The tasks start time should not be before its end time if"
                                                 + " its' start date is the same as its end date.";
    public static final String MESSAGE_ONLY_GO_TO_MODE_COMMANDS = "This command can only be used in Patients mode. "
                                                                + "To return to Patients mode, use the back command.";
    public static final String MESSAGE_ONLY_PATIENT_MODE_COMMANDS = "This command can only be used in Records mode. "
                                                                + "To go to the Records mode, use the GoTo command.";
    public static final String MESSAGE_ONLY_TASK_OR_DATE_COMMANDS = "This command cannot be ran here.\n"
                                                                + "Only task related commands,"
                                                                + " dates (in dd-mm-yyyy format)\n help, exit, undo and"
                                                                + " redo commands can be used in this mode.";
    public static final String MESSAGE_IN_GO_TO_MODE = "Already in Records mode, "
                                                        + "please exit the Records mode using the back command first.";
    public static final String MESSAGE_IN_PATIENT_MODE = "Already in Patients Mode.";
    public static final String MESSAGE_NOTHING_DONE = "Cancelled, nothing has been done.";
    public static final String MESSAGE_CALENDAR_SHOWN = "Task Calendar is already displayed.";
    public static final String MESSAGE_CANNOT_RUN_IN_GOTO = "This command cannot be used in this mode as the Patients"
                                        + " list needs to be seen. Use the back command to return to the right mode.";
    public static final String MESSAGE_EMPTY_KEYWORD = "Keyword cannot be blank!";
    public static final String MESSAGE_NO_SEARCH_PARAMETER = "Find needs at least 1 parameter for searching!";
}
