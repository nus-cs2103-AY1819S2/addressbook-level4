/* @@author randytqw */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";



    @Override
    public CommandResult execute(CurrentEdit current, Model model, CommandHistory history)
        throws CommandException {
        requireNonNull(current);
        Image initialImage = current.getTempImage();
        if (initialImage == null) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }

        if (!current.canUndoTemp()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        current.setUndoTemp();
        current.replaceTempWithOriginal();
        current.updateHistory();
        List<Command> tempHistory = current.getTempSubHistory();
        for (Command command :tempHistory) {
            command.execute(current, model, history);
        }
        current.displayTempImage();

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
