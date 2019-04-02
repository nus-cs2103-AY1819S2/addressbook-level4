package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPENING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBLINK;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;

/**
 * Adds a restaurant to the food diary.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a restaurant to the food diary. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]... "
            + PREFIX_WEBLINK + "WEBLINK "
            + PREFIX_OPENING_HOURS + "OPENING_HOURS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "KFC "
            + PREFIX_PHONE + "68765432 "
            + PREFIX_EMAIL + "kfc@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "fastfood "
            + PREFIX_TAG + "friedChicken "
            + PREFIX_WEBLINK + "www.kfc.com.sg "
            + PREFIX_OPENING_HOURS + "1000 to 2130";

    public static final String MESSAGE_SUCCESS = "New restaurant added: %1$s";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This restaurant already exists in the food diary";

    private final Restaurant toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Restaurant}
     */
    public AddCommand(Restaurant restaurant) {
        requireNonNull(restaurant);
        toAdd = restaurant;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasRestaurant(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESTAURANT);
        }

        model.addRestaurant(toAdd);
        model.commitFoodDiary();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
