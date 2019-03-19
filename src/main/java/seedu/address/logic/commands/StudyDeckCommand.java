package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.UniqueDeckList;

/**
 * Selects a deck identified using its displayed index.
 */
public class StudyDeckCommand extends Command {

    public static final String COMMAND_WORD = "study";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Enters the session using a deck.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_STUDY_DECK_SUCCESS = "Entered study mode";

    private Index targetIndex;
    private DecksView viewState;

    public StudyDeckCommand(Index targetIndex, DecksView viewState) {
        this.targetIndex = targetIndex;
        this.viewState = viewState;
    }

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);

        List<Deck> filteredDeckList = viewState.filteredDecks;

        if (targetIndex.getZeroBased() >= filteredDeckList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        if (filteredDeckList.get(targetIndex.getZeroBased()).isEmpty() ) {
            throw new CommandException(Messages.MESSAGE_EMPTY_DECK);
        }


        model.studyDeck(filteredDeckList.get(targetIndex.getZeroBased()));

        return new StudyPanelCommand(String.format(MESSAGE_STUDY_DECK_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudyDeckCommand // instanceof handles nulls
                && targetIndex.equals(((StudyDeckCommand) other).targetIndex)); // state check
    }
}
