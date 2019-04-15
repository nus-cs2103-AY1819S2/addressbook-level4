package seedu.address.logic.commands;

/**
 * An abstract class to generalise all types of list commands
 * Note that the {@code execute} command is not defined here so any classes inheriting from this has to implement it
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = ListEmployeeCommand.MESSAGE_USAGE
            + "\n"
            + ListProjectCommand.MESSAGE_USAGE;


}
