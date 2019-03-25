package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hms.commons.core.Messages;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.BookingWithTypePredicate;

/**
 * Finds and lists all bookings in booking list whose payer's name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindBookingWithTypeCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "fbwt";
    public static final String COMMAND_WORD = "findbookingwithtype";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all bookings which is the type selected\n"
            + "Parameters: SERVICE_TYPE\n"
            + "Example: " + COMMAND_WORD + " GYM";

    private final BookingWithTypePredicate predicate;

    public FindBookingWithTypeCommand(BookingWithTypePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBookingList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BOOKINGS_LISTED_OVERVIEW, model.getFilteredBookingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindBookingWithTypeCommand // instanceof handles nulls
                && predicate.equals(((FindBookingWithTypeCommand) other).predicate)); // state check
    }
}
