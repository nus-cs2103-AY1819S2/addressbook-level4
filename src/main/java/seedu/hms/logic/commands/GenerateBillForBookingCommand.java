package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BillModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;

/**
 * Generates the bill for a customer who is identified using displayed index from hms book
 */
public class GenerateBillForBookingCommand extends BillCommand {

    public static final String COMMAND_ALIAS = "gb-b";
    public static final String COMMAND_WORD = "generatebill-booking";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates reservation bill for the customer identified by the identification number\n"
        + "used in the displayed "
        + "customer list.\n"
        + "Parameters: IDENTIFICATION NO\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GENERATE_BILL_SUCCESS = "Bill: %1$s";

    private final Predicate<Booking> bookingPredicate;


    public GenerateBillForBookingCommand(BookingContainsPayerPredicate bookingContainsPayerPredicate,
                                         BookingWithTypePredicate bookingWithTypePredicate,
                                         BookingWithinTimePredicate bookingWithinTimePredicate) {

        this.bookingPredicate = (bookingTested) -> bookingContainsPayerPredicate.test(bookingTested)
            && bookingWithTypePredicate.test(bookingTested)
            && bookingWithinTimePredicate.test(bookingTested);
    }


    @Override

    public CommandResult execute(BillModel model, CommandHistory history) throws CommandException {

        requireNonNull(model);

        model.updateFilteredBookingList(bookingPredicate);
        ObservableList<Booking> bookingObservableList = model.getFilteredBookingList();

        double amount = model.generateBillForBooking(bookingObservableList);


        return new CommandResult(String.format(MESSAGE_GENERATE_BILL_SUCCESS, amount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof GenerateBillForBookingCommand // instanceof handles nulls
            && bookingPredicate.equals(((GenerateBillForBookingCommand) other).bookingPredicate)); // state check
    }
}
