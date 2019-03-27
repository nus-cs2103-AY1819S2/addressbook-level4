package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardFolder;
import seedu.address.model.Model;

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
        if (!model.isInFolder()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }
        model.resetCardFolder(new CardFolder(model.getActiveCardFolder().getFolderName()));
        model.commitActiveCardFolder();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
