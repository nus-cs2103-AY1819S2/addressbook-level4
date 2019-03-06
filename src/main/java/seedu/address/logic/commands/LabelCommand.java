package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Label a PDF file.
 */
public class LabelCommand extends Command {
    public static final String COMMAND_WORD = "label";

    public static final String MESSAGE_SUCCESS = "Label success!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

}
