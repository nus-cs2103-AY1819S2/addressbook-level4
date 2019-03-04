package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class RecommendationCommand extends Command {
    public static final String COMMAND_WORD = "recommendation";

    public static final String MESSAGE_SUCCESS = "Recommendated OutFit Given";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
