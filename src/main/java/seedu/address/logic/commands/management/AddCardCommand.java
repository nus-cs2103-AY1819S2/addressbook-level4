package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_OPENED_LESSON;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE;
import static seedu.address.logic.parser.Syntax.PREFIX_OPTIONAL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which adds a {@link Card} to the
 * opened {@link Lesson} object.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
 */
public class AddCardCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "addCard";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a card to the opened lesson. "
            + "\nParameters: "
            + PREFIX_CORE + "CORE " + PREFIX_CORE + "CORE "
            + "[" + PREFIX_CORE + "CORE]... "
            + "[" + PREFIX_OPTIONAL + "OPTIONAL]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CORE + "Australia "
            + PREFIX_CORE + "Canberra "
            + PREFIX_CORE + "English "
            + PREFIX_OPTIONAL + "Starts with C\n";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Added card:\n%1$s";
    /**
     * Feedback message displayed to the user when attempting to add a card to the opened lesson, and
     * the card does not have the same number of cores as the lesson's core headers.
     */
    public static final String MESSAGE_INVALID_CORE_COUNT =
            "The card has an invalid number of cores.\n"
            + "It needs to have %1$d cores as specified by the lesson.";
    /**
     * The card to be added when {@link #execute(Model, CommandHistory)} is called.
     */
    private final Card toAdd;
    /**
     * Constructs a {@link ManagementCommand} to add the specified {@link Card} to the
     * opened {@link Lesson} object.
     *
     * @param toAdd the {@link Card} to be added
     */
    public AddCardCommand(Card toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    /**
            * Executes the command which adds a {@link Card} to the opened {@link Lesson} object.
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

        try {
            mgtModel.addCardToOpenedLesson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (IllegalArgumentException e) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_CORE_COUNT,
                            mgtModel.getOpenedLessonCoreHeaders().size(),
                            mgtModel), e);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_NO_OPENED_LESSON, e);
        }
    }

    /**
     * When a card is added, the lesson needs to be saved to the hard disk.
     *
     * @return true given that a save to disk is required.
     */
    @Override
    public boolean isSaveRequired() {
        return true;
    }

    /**
     * Returns true if {@code other} is the same object or if it is also an {@link AddCardCommand}
     * attempting to add the same card.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also an {@link AddCardCommand}
     * attempting to add the same card.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCardCommand // instanceof handles nulls
                && toAdd.equals(((AddCardCommand) other).toAdd));
    }
}
