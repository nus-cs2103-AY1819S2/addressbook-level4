package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;
import seedu.knowitall.model.ReadOnlyCardFolder;

/**
 * Deletes a folder identified using it's displayed index from the home directory.
 */
public class DeleteFolderCommand extends Command {

    public static final String COMMAND_WORD = "deletefolder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the card folder identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FOLDER_SUCCESS = "Deleted Card Folder: %1$s";

    private final Index targetIndex;

    public DeleteFolderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getState() != State.IN_HOMEDIR) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER);
        }
        List<ReadOnlyCardFolder> cardFolderList = model.getCardFolders();

        if (targetIndex.getZeroBased() >= cardFolderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX);
        }

        ReadOnlyCardFolder cardFolderToDelete = cardFolderList.get(targetIndex.getZeroBased());

        model.deleteFolder(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_DELETE_FOLDER_SUCCESS, cardFolderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFolderCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteFolderCommand) other).targetIndex)); // state check
    }
}
