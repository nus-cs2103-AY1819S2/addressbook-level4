package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class CountCommand extends Command {

    public static final String COMMAND_WORD = "count";

    public static final String MESSAGE_SUCCESS = "Counted the number of persons: %d";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getFilteredPersonList().size()));
    }
}
