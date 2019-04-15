package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BillModel;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;

/**
 * Generates the bill for a customer who is identified using displayed index from hms book
 */
public class GenerateBillForCustomerCommand extends BillCommand {

    public static final String COMMAND_ALIAS = "gb-c";
    public static final String COMMAND_WORD = "generate-bill-customer";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates total bill for the customer identified by the index number\n used in the displayed "
        + "customer list.\n"
        + "Parameters: INDEX\n"
        + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_GENERATE_BILL_SUCCESS = "%1$s";

    private final Predicate<Booking> bookingPredicate;
    private final Predicate<Reservation> reservationPredicate;
    private final Bill bill;


    public GenerateBillForCustomerCommand(BookingContainsPayerPredicate bookingContainsPayerPredicate,
                                          ReservationContainsPayerPredicate reservationContainsPayerPredicate,
                                          Bill bill) {

        this.bookingPredicate = bookingContainsPayerPredicate;
        this.reservationPredicate = reservationContainsPayerPredicate;
        this.bill = bill;
    }


    @Override

    public CommandResult execute(BillModel model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        model.updateFilteredBookingList(bookingPredicate);
        model.updateFilteredReservationList(reservationPredicate);
        model.updateBill(bill);

        return new CommandResult(String.format(MESSAGE_GENERATE_BILL_SUCCESS, this.bill));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof GenerateBillForCustomerCommand // instanceof handles nulls
            && bookingPredicate.equals(((GenerateBillForCustomerCommand) other).bookingPredicate)) // state check
            && reservationPredicate.equals(((GenerateBillForCustomerCommand) other).reservationPredicate);
    }
}
