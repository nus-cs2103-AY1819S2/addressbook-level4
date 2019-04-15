package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.NoInternetException;
import seedu.address.commons.util.WebUtil;
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
                checkUrl(weblink);
            } catch (NoInternetException e) {
                return new CommandResult(e.getMessage());
            }

            return new CommandResult(String.format(MESSAGE_VISIT_RESTAURANT_SUCCESS, targetIndex.getOneBased()),
                    weblink);
        } else {
            // if params of visitWeb command is a Weblink, pass weblink to CommandResult
            return new CommandResult(String.format(MESSAGE_VISIT_WEBLINK, weblink.value), weblink);
        }
    }

    /**
     * Checks if url is valid using isNotValidWeblinkUrl method from Weblink
     * @param weblink
     * @throws NoInternetException when internet connection fails
     * @throws CommandException when url is invalid
     */
    private void checkUrl(Weblink weblink) throws NoInternetException, CommandException {

        boolean notValid = !WebUtil.isUrlValid(weblink.value);
        boolean weblinkIsDefault = weblink.isDefault();
        if (notValid && weblinkIsDefault) {
            throw new CommandException(String.format(Messages.MESSAGE_CHANGE_WEBLINK, "Weblink"));
        } else if (notValid) {
            throw new CommandException(String.format(Messages.MESSAGE_CHANGE_WEBLINK, weblink.toString()));
        }
    }


    @Override
    public boolean equals(Object other) {

        boolean result;

        if (!(other instanceof VisitWebCommand)) {
            return false;
        }

        if (targetIndex != null) {
            result = targetIndex.equals(((VisitWebCommand) other).targetIndex);
        } else {
            assert weblink != null;
            result = weblink.equals(((VisitWebCommand) other).weblink);
        }

        return other == this || result; // state check
    }
}
