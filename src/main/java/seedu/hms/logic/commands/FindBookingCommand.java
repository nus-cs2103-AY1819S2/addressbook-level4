package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.function.Predicate;

import seedu.hms.commons.core.Messages;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;

/**
 * Finds and lists all bookings in booking list whose payer's name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindBookingCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "fb";
    public static final String COMMAND_WORD = "find-booking";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all bookings belonging to the selected "
        + "customer\n"
        + "Parameters: "
        + "[" + PREFIX_IDENTIFICATION_NUMBER + "CUSTOMER_IDENTIFICATION_NUMBER] "
        + "[" + PREFIX_SERVICE + "SERVICE_TYPE] "
        + "[" + PREFIX_TIMING + "TIME_RANGE]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_IDENTIFICATION_NUMBER + "1234567 "
        + PREFIX_SERVICE + "GYM "
        + PREFIX_TIMING + "10 - 11";

    private final Predicate<Booking> bookingPredicate;
    private final BookingContainsPayerPredicate bookingContainsPayerPredicate;
    private final BookingWithTypePredicate bookingWithTypePredicate;
    private final BookingWithinTimePredicate bookingWithinTimePredicate;

    public FindBookingCommand(BookingContainsPayerPredicate bookingContainsPayerPredicate,
                              BookingWithTypePredicate bookingWithTypePredicate,
                              BookingWithinTimePredicate bookingWithinTimePredicate) {
        this.bookingPredicate = (bookingTested) -> bookingContainsPayerPredicate.test(bookingTested)
            && bookingWithTypePredicate.test(bookingTested)
            && bookingWithinTimePredicate.test(bookingTested);
        this.bookingContainsPayerPredicate = bookingContainsPayerPredicate;
        this.bookingWithinTimePredicate = bookingWithinTimePredicate;
        this.bookingWithTypePredicate = bookingWithTypePredicate;
    }

    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBookingList(bookingPredicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_BOOKINGS_LISTED_OVERVIEW, model.getFilteredBookingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindBookingCommand // instanceof handles nulls
            && bookingContainsPayerPredicate.equals(((FindBookingCommand) other).bookingContainsPayerPredicate)
            && bookingWithTypePredicate.equals(((FindBookingCommand) other).bookingWithTypePredicate)
            && bookingWithinTimePredicate.equals(((FindBookingCommand) other).bookingWithinTimePredicate)); // state
            // check
    }
}
