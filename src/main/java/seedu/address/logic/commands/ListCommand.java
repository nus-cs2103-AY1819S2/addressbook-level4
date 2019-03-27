package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;

import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.ViewState;
import seedu.address.model.Model;

/**
 * Lists all decks to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all decks";
    private final ViewState viewState;

    public ListCommand(ViewState viewState) {
        this.viewState = viewState;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (model.isAtDecksView()) {
            ((DecksView)viewState).updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        } else if (model.isAtCardsView()) {
            ((CardsView)viewState).updateFilteredList(PREDICATE_SHOW_ALL_CARDS);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
