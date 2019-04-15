package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.card.Card;

/**
 * Selects a card identified using it's displayed index from the card folder.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the card identified by the index number used in the displayed card list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_CARD_SUCCESS = "Selected Card: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getState() != Model.State.IN_FOLDER) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }

        List<Card> filteredCardList = model.getActiveFilteredCards();

        if (targetIndex.getZeroBased() >= filteredCardList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        model.setSelectedCard(filteredCardList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_CARD_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
