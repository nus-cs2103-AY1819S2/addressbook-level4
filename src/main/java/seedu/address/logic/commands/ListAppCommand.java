package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListAppCommand extends Command {

    public static final String COMMAND_WORD = "listapp";

    public static final String MESSAGE_SUCCESS = "Listed all appointments\n";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String result = model.listApp();
        return new CommandResult(MESSAGE_SUCCESS + result, false, false);
    }
}
