package seedu.address.logic.commands;

/**
 * Represents a Update command which allows updating statuses of parts of the project.
 */
public abstract class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME userstory/projecttask"
            + ": updates the status of the user story/project task in the selected project.\n";
}

