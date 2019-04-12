package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL;
import static seedu.address.model.Model.PREDICATE_SHOW_UNVISITED_RESTAURANTS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.comparators.SortDistanceComparator;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Postal;

/**
 * Lists all restaurants that have yet to be visited in the food diary to the user.
 */
public class ListUnvisitedCommand extends Command {

    public static final String COMMAND_WORD = "listUnvisited";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List unvisited "
            + "Parameters: "
            + PREFIX_POSTAL + "POSTAL CODE ";

    public static final String MESSAGE_SUCCESS = "Listed all unvisited restaurants";

    private int postal;

    public ListUnvisitedCommand(Postal postal) {
        this.postal = Integer.parseInt(postal.value);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        requireNonNull(model);
        if (model.getPostalData(postal).isPresent()) {
            model.updateFilteredRestaurantListAndSort(PREDICATE_SHOW_UNVISITED_RESTAURANTS,
                    new SortDistanceComparator(model, postal));
        } else {
            model.updateFilteredRestaurantList(PREDICATE_SHOW_UNVISITED_RESTAURANTS);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListUnvisitedCommand // instanceof handles nulls
                && postal == ((ListUnvisitedCommand) other).postal);
    }


}
