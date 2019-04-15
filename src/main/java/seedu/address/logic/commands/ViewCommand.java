package seedu.address.logic.commands;

/**
 * An abstract class to generalise all types of view commands
 * Note that the {@code execute} command is not defined here so any classes inheriting from this has to implement it
 */
public abstract class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = "view employee/project INDEX"
            + "\n"
            + ViewEmployeeCommand.MESSAGE_USAGE
            + "\n"
            + ViewProjectCommand.MESSAGE_USAGE;
}
