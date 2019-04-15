package seedu.address.logic.commands;

/**
 * Represents a AddTo command with hidden internal logic and the ability to be executed.
 */
public abstract class AddToCommand extends Command {

    public static final String COMMAND_WORD = "addto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME employee/milestone/userstory/projecttask"
            + ": adds the employee/milestone/user story/project task into the selected project.\n";
}
