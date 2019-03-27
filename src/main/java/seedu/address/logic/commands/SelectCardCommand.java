package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;

/**
 * Selects a card identified using its displayed index.
 */
public class SelectCardCommand extends SelectCommand {

    private CardsView cardsView;

    public SelectCardCommand(CardsView cardsView, Index targetIndex) {
        super(targetIndex);
        this.cardsView = cardsView;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Card> filteredCardList = cardsView.filteredCards;

        if (targetIndex.getZeroBased() >= filteredCardList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        model.setSelectedItem(filteredCardList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCardCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCardCommand) other).targetIndex)); // state check
    }
}
