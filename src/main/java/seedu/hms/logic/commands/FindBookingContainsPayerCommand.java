package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_BOOKINGS;

import seedu.hms.commons.core.Messages;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.BookingContainsPayerPredicate;

/**
 * Finds and lists all bookings in booking list whose payer's name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindBookingContainsPayerCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "fbcp";
    public static final String COMMAND_WORD = "findbookingcontainspayer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all bookings which is paid by the selected "
            + "customer\n"
            + "Parameters: CUSTOMER_IDENTIFICATION_NUMBER\n"
            + "Example: " + COMMAND_WORD + " 1234567";

    private final BookingContainsPayerPredicate predicate;

    public FindBookingContainsPayerCommand(BookingContainsPayerPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
        model.updateFilteredBookingList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BOOKINGS_LISTED_OVERVIEW, model.getFilteredBookingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindBookingContainsPayerCommand // instanceof handles nulls
                && predicate.equals(((FindBookingContainsPayerCommand) other).predicate)); // state check
    }
}
