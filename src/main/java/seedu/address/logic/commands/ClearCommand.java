package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.GradTrak;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Grad Trak has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setGradTrak(new GradTrak());
        model.commitGradTrak();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
