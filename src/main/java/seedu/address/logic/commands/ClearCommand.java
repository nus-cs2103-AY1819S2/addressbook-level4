package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.RestOrRant;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clearAll";
    public static final String COMMAND_ALIAS = "cAll";
    public static final String MESSAGE_SUCCESS = "RestOrRant has been cleared!";


    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) {
        requireNonNull(model);
        model.setRestOrRant(new RestOrRant());
        model.updateMode();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
