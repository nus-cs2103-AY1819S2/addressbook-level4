package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Increases the budget limit for the month or week by specified amount
 */
public class IncreaseCommand extends Command {

    public static final String COMMAND_WORD = "increase";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Increases the budget for week/month "
            + "by the amount specified. "
            + "Existing budget will be changed accordingly.\n"
            + "Parameters: " + PREFIX_AMOUNT;


    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Increase command not implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
    
}