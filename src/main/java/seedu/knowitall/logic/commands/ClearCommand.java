package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;

/**
 * Clears the card folder.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "card folder has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // Name of CardFolder is preserved in clear operation
        if (model.getState() != State.IN_FOLDER) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }
        model.resetCardFolder(new CardFolder(model.getActiveCardFolder().getFolderName()));
        model.commitActiveCardFolder();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
