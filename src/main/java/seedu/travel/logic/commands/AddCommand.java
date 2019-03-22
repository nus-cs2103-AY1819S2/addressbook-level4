package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_COUNTRY_CODE;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.travel.logic.CommandHistory;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.model.Model;
import seedu.travel.model.place.Place;

/**
 * Adds a place to the travel book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a place to the travel book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_COUNTRY_CODE + "COUNTRY CODE "
            + PREFIX_RATING + "RATING "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "National University of Singapore "
            + PREFIX_COUNTRY_CODE + "SGP "
            + PREFIX_RATING + "4 "
            + PREFIX_DESCRIPTION
            + "The National University of Singapore is an autonomous research university in Singapore. "
            + PREFIX_ADDRESS + "21 Lower Kent Ridge Road, Singapore 119077 "
            + PREFIX_TAG + "school "
            + PREFIX_TAG + "studiesHere";

    public static final String MESSAGE_SUCCESS = "New place added: %1$s";
    public static final String MESSAGE_DUPLICATE_PLACE = "This place already exists in the travel book";

    private final Place toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Place}
     */
    public AddCommand(Place place) {
        requireNonNull(place);
        toAdd = place;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPlace(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLACE);
        }

        model.addPlace(toAdd);
        model.commitTravelBuddy();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
