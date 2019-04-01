package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.NoInternetException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;

/**
 * Visits a restaurant website identified using it's displayed index from the food diary.
 */
public class VisitWebCommand extends Command {

    public static final String COMMAND_WORD = "visitWeb";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Visits a restaurant website identified using it's displayed index from the food diary\n"
            + "or you can simply type any url to display it on our browser!"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Parameters: URL (must be a valid URL)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " www.jollibee.com.ph";


    public static final String MESSAGE_VISIT_RESTAURANT_SUCCESS = "Displaying Website of Restaurant: %1$s";
    public static final String MESSAGE_VISIT_WEBLINK = "Displaying website : %1$s";

    private final Index targetIndex;
    private final Weblink weblink;

    public VisitWebCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.weblink = null;
    }

    public VisitWebCommand(Weblink weblink) {
        this.targetIndex = null;
        this.weblink = weblink;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // assert that either one parameters must be present
        assert(targetIndex != null || weblink != null);

        if (targetIndex != null) {
            requireNonNull(model);

            List<Restaurant> filteredRestaurantList = model.getFilteredRestaurantList();

            if (targetIndex.getZeroBased() >= filteredRestaurantList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
            }

            // if original weblink is removed, prompt user to change weblink
            Weblink weblink = filteredRestaurantList.get(targetIndex.getZeroBased()).getWeblink();
            try {
                if (Weblink.isNotValidWeblinkUrl(weblink.value)) {
                    throw new CommandException(String.format(Messages.MESSAGE_CHANGE_WEBLINK, weblink.value));
                }
            } catch (NoInternetException e) {
                return new CommandResult(e.getMessage());
            }

            model.setSelectedRestaurant(filteredRestaurantList.get(targetIndex.getZeroBased()));
            return new CommandResult(String.format(MESSAGE_VISIT_RESTAURANT_SUCCESS, targetIndex.getOneBased()));
        } else {
            // if params of visitWeb command is a Weblink, pass weblink to CommandResult
            return new CommandResult(String.format(MESSAGE_VISIT_WEBLINK, weblink.value), weblink);
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitWebCommand // instanceof handles nulls
                && targetIndex.equals(((VisitWebCommand) other).targetIndex)); // state check
    }
}
