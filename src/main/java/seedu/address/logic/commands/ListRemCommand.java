package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListRemCommand extends Command {

    public static final String COMMAND_WORD = "listrem";
    public static final String COMMAND_ALIAS = "lr";

    public static final String MESSAGE_SUCCESS = "Listed all reminders\n";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String result = model.listRem();
        return new CommandResult(MESSAGE_SUCCESS + result, false, false);
    }
}
