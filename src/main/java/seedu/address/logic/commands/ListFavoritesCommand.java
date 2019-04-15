package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.PersonIsFavoritePredicate;

/**
 * Filters all people in the person card list displaying only favorites.
 */
public class ListFavoritesCommand extends Command {

    public static final String COMMAND_WORD = "listFav";
    public static final String MESSAGE_SUCCESS = "Listed all favorites";

    private PersonIsFavoritePredicate predicate = null;

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        predicate = new PersonIsFavoritePredicate(model);
        model.updateFilteredPersonList(predicate);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
