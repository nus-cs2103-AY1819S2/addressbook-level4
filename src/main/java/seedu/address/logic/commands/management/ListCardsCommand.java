package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which lists all {@link Card} objects
 * in the opened {@link Lesson} object.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
 */
public class ListCardsCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "listCards";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all cards in the opened lesson.\n"
            + "Example: " + COMMAND_WORD;
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Listed %1$s card(s):\n";
    /**
     * Feedback message displayed to the user if there are no cards found and hence no listing.
     */
    public static final String MESSAGE_NO_CARDS = "No cards found.";

    /**
     * Constructs a list of {@link Card} objects for display purposes.
     *
     * @param cards the list of {@link Card} objects
     * @return a String representing {@code cards}
     */
    public String buildList(List<String> coreHeaders, List<String> optionalHeaders,
                            List<Card> cards) {
        if (cards.isEmpty()) {
            return MESSAGE_NO_CARDS;
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format(MESSAGE_SUCCESS, cards.size()));
            builder.append("[No.]").append(coreHeaders).append(optionalHeaders).append('\n');

            int i = 1;
            for (Card card : cards) {
                builder.append("[ ").append(i).append(" ]").append(card.toPrint()).append("\n");
                i++;
            }

            return builder.toString();
        }
    }

    /**
     * Executes the command which lists all {@link Card} objects in the opened {@link Lesson}
     * object.
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
            ArrayList<Card> cards = new ArrayList<>(mgtModel.getOpenedLessonCards());
            List<String> coreHeaders = mgtModel.getOpenedLessonCoreHeaders();
            List<String> optionalHeaders = mgtModel.getOpenedLessonOptionalHeaders();
            return new CommandResult(buildList(coreHeaders, optionalHeaders, cards));
        } catch (NullPointerException e) {
            return new CommandResult(MESSAGE_NO_CARDS);
        }
    }

    /**
     * Returns true if {@code other} is the same object or if it is also an {@link ListCardsCommand}.
     * All {@link ListCardsCommand} objects are the same.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also an {@link ListCardsCommand}.
     * All {@link ListCardsCommand} objects are the same.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCardsCommand);
    }
}
