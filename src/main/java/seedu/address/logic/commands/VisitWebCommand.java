package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;

/**
 * Visits a restaurant website identified using it's displayed index from the food diary.
 */
public class VisitWebCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Visits a restaurant website identified using it's displayed index from the food diary.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_RESTAURANT_SUCCESS = "Visited Website of Restaurant: %1$s";

    private final Index targetIndex;

    public VisitWebCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Restaurant> filteredRestaurantList = model.getFilteredRestaurantList();

        if (targetIndex.getZeroBased() >= filteredRestaurantList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        model.setSelectedRestaurant(filteredRestaurantList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_RESTAURANT_SUCCESS, targetIndex.getOneBased()));

    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitWebCommand // instanceof handles nulls
                && targetIndex.equals(((VisitWebCommand) other).targetIndex)); // state check
    }
}
