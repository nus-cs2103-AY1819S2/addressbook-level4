package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;
import seedu.knowitall.model.card.Card;

/**
 * Allows user to be shown the answer for the currently displayed card, card will be marked as wrong.
 */
public class RevealCommand extends Command {

    public static final String COMMAND_WORD = "reveal";

    public static final String MESSAGE_REVEAL_SUCCESS = "Answer revealed";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getState() != State.IN_TEST) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        }
        if (model.isCardAlreadyAnswered()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REVEAL_COMMAND);
        }
        Card cardToMark = model.getCurrentTestedCard();

        boolean isAttemptCorrect = false;
        model.setCardAsAnswered();

        Card scoredCard = model.createScoredCard(cardToMark, isAttemptCorrect);
        model.setCard(cardToMark, scoredCard);
        model.updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);
        model.commitActiveCardFolder();

        return new CommandResult(MESSAGE_REVEAL_SUCCESS, CommandResult.Type.ANSWER_REVEAL);
    }

}
