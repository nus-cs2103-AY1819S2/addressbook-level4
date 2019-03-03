package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all restaurants in the food diary to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "View your personalised statistics";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        int size = model.getSize();
        return new CommandResult(MESSAGE_SUCCESS + size);
    }
}
