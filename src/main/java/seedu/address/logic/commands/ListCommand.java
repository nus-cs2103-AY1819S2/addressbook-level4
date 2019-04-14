package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ListViewState;
import seedu.address.model.Model;

/**
 * Lists all decks to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_DECK_SUCCESS = "Listed all decks";
    public static final String MESSAGE_CARD_SUCCESS = "Listed all cards";

    private final ListViewState listViewState;

    public ListCommand(ListViewState listViewState) {
        this.listViewState = listViewState;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        listViewState.updateFilteredList(unused -> true);
        if (model.isAtDecksView()) {
            return new CommandResult(MESSAGE_DECK_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_CARD_SUCCESS);
        }

    }
}
