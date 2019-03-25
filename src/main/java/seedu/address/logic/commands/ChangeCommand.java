package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.AnswerCommandResultType;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCardFolder;

/**
 * Selects a folder identified using it's displayed index in the home directory. Also used to navigate from
 * within a folder back to the home directory.
 */
public class ChangeCommand extends Command {

    public static final String COMMAND_WORD = "change";

    // TODO: Include use case for changing to home directory
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the card folder that the user is in.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_EXIT_FOLDER_SUCCESS = "Returned to home";
    private static final String MESSAGE_ENTER_FOLDER_SUCCESS = "Entered Card Folder: %1$s";

    private Index targetIndex;
    private final boolean toHome;

    public ChangeCommand(Index targetIndex) {
        toHome = false;
        this.targetIndex = targetIndex;
    }

    public ChangeCommand() {
        toHome = true;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<ReadOnlyCardFolder> cardFolderList = model.getCardFolders();

        if (toHome) {
            if (!model.isInFolder()) {
                throw new CommandException(Messages.MESSAGE_ILLEGAL_COMMAND_NOT_IN_FOLDER);
            }
            model.exitFoldersToHome();
            return new CommandResult(MESSAGE_EXIT_FOLDER_SUCCESS,
                    false, false, false, true, false, null, false, AnswerCommandResultType.NOT_ANSWER_COMMAND);
        } else {
            if (model.isInFolder()) {
                throw new CommandException(Messages.MESSAGE_ILLEGAL_COMMAND_NOT_IN_HOME);
            }

            if (targetIndex.getZeroBased() >= cardFolderList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX);
            }
            model.setActiveCardFolderIndex(targetIndex.getZeroBased());
            return new CommandResult(String.format(MESSAGE_ENTER_FOLDER_SUCCESS, targetIndex.getOneBased()),
                    false, false, true, false, false, null, false, AnswerCommandResultType.NOT_ANSWER_COMMAND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeCommand // instanceof handles nulls
                && targetIndex.equals(((ChangeCommand) other).targetIndex)); // state check
    }
}
