package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class GenerateCommand extends Command {

    public static final String COMMAND_ALIAS = "generate";
    public static final String COMMAND_WORD = "g";
    public static final String MESSAGE_SUCCESS = "Graph has been successfully generated!";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
