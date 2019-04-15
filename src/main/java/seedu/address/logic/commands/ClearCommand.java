package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.PocketProject;

/**
 * Clears the pocket project.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Pocket Project has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setPocketProject(new PocketProject());
        model.commitPocketProject();
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
