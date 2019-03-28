package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NO_OPENED_LESSON;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE;
import static seedu.address.logic.parser.Syntax.PREFIX_OPTIONAL;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which deletes a {@link Card} from the
 * opened {@link Lesson} object.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
 */
public class DeleteCardCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "deleteCard";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the card at the specified INDEX of the card list.\n"
            + "To view the card list, enter \'listCards\'.\n"
            + "Parameter: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Deleted card.";
    /**
     * The index of the card to be deleted when {@link #execute(Model, CommandHistory)}
     * is called.
     */
    private final Index targetIndex;

    /**
     * Creates an DeleteCardCommand to delete the specified {@link Card}.
     *
     * @param targetIndex the index of the {@link Card} to be deleted
     */
    public DeleteCardCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command which deletes a {@link Card} from the opened {@link Lesson} object.
     *
     * @param model the {@link ManagementModel} the command should operate on.
     * @param history {@link CommandHistory} which the command should operate on.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException if the {@code model} passed in is not a {@link ManagementModel}
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ManagementModel mgtModel = requireManagementModel(model);
        int toDeleteIndex = targetIndex.getZeroBased();

        try {
            mgtModel.deleteCardFromOpenedLesson(toDeleteIndex);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IllegalArgumentException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX,
                    targetIndex.getOneBased()), e);
        }
    }

    /**
     * When a card is deleted, the updated list of {@link Lesson} objects needs
     * to be saved to the hard disk.
     *
     * @return true given that a save to disk is required.
     */
    @Override
    public boolean isSaveRequired() {
        return true;
    }

    /**
     * Returns true if {@code other} is the same object or if it is also an {@link DeleteCardCommand}
     * attempting to delete the same card.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also an {@link DeleteCardCommand}
     * attempting to delete the same card.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCardCommand // instanceof handles nulls
                && targetIndex.getZeroBased() == ((DeleteCardCommand) other).targetIndex.getZeroBased());
    }
}