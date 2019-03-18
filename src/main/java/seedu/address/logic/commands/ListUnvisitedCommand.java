package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_UNVISITED_RESTAURANTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all restaurants that have yet to be visited in the food diary to the user.
 */
public class ListUnvisitedCommand extends Command {
    public static final String COMMAND_WORD = "listUnvisited";

    public static final String MESSAGE_SUCCESS = "Listed all unvisited restaurants";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_UNVISITED_RESTAURANTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
