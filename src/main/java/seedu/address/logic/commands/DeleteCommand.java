package seedu.address.logic.commands;

/**
 * Represents a delete command with hidden internal logic and the ability to be executed.
 */
public abstract class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";


    public static final String MESSAGE_USAGE = COMMAND_WORD + " employee/project"
            + ": Deletes the employee/project identified by the index number/project name used in"
            + " the displayed employee/project list.\n"
            + DeleteEmployeeCommand.MESSAGE_USAGE + "\n"
            + DeleteProjectCommand.MESSAGE_USAGE;
}
