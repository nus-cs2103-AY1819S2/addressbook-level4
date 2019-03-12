package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.CuisineContainsKeywordsPredicate;
import seedu.address.model.restaurant.categories.Cuisine;

public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all restaurants whose category matches any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: CUISINE\n"
            + "Example: " + COMMAND_WORD + " Fast Food";

    private final CuisineContainsKeywordsPredicate predicate;

    public FilterCommand(CuisineContainsKeywordsPredicate predicate) { this.predicate = predicate; }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return null;
    }
}
