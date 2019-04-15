package seedu.address.logic.commands;

/**
 * Represents an add command with hidden internal logic and the ability to be executed.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " employee/project "
            + ": Adds and employee/project to the Pocket Project application.\n\n"
            + AddEmployeeCommand.MESSAGE_USAGE + "\n\n"
            + AddProjectCommand.MESSAGE_USAGE;

}
