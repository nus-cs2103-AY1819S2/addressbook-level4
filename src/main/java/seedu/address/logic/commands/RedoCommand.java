/* @@author randytqw */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;


/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(CurrentEdit currentEdit,
                                 Model model, CommandHistory history) throws CommandException {
        requireNonNull(currentEdit);
        Image initialImage = currentEdit.getTempImage();
        if (initialImage == null) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }


        if (!currentEdit.canRedoTemp()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        Command command = currentEdit.getCommandTemp();
        command.execute(currentEdit, model, history);
        currentEdit.setRedoTemp();
        currentEdit.updateHistory();

        currentEdit.displayTempImage();


        return new CommandResult(MESSAGE_SUCCESS);
    }
}
