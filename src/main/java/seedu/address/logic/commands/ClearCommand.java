package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DocX;
import seedu.address.model.Model;

/**
 * Clears the docX.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "docX has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setDocX(new DocX());
        model.commitDocX();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
