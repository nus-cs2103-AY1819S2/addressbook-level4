package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.function.Predicate;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BillModel;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;

/**
 * Generates the bill for a customer who is identified using displayed index from hms book
 */
public class GenerateBillForBookingCommand extends BillCommand {

    public static final String COMMAND_ALIAS = "gb-b";
    public static final String COMMAND_WORD = "generate-bill-booking";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates booking bill for the customer identified by the index number\n used in the displayed "
        + "customer list.\n"
        + "Parameters: INDEX "
        + "[" + PREFIX_SERVICE + "SERVICE NAME] "
        + "[" + PREFIX_TIMING + "TIMING(HH - HH in 24 hour format)]\n "
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_SERVICE + "GYM "
        + PREFIX_TIMING + "10 - 11 ";


    public static final String MESSAGE_GENERATE_BILL_SUCCESS = "Booking bill generated for customer: %1$s ";

    private final Predicate<Booking> bookingPredicate;
    private final Bill bill;
    private final BookingContainsPayerPredicate bookingContainsPayerPredicate;
    private final BookingWithinTimePredicate bookingWithinTimePredicate;
    private final BookingWithTypePredicate bookingWithTypePredicate;

    public GenerateBillForBookingCommand(BookingContainsPayerPredicate bookingContainsPayerPredicate,
                                         BookingWithTypePredicate bookingWithTypePredicate,
                                         BookingWithinTimePredicate bookingWithinTimePredicate,
                                         Bill bill) {

        this.bookingPredicate = (bookingTested) -> bookingContainsPayerPredicate.test(bookingTested)
            && bookingWithTypePredicate.test(bookingTested)
            && bookingWithinTimePredicate.test(bookingTested);
        this.bill = bill;
        this.bookingContainsPayerPredicate = bookingContainsPayerPredicate;
        this.bookingWithinTimePredicate = bookingWithinTimePredicate;
        this.bookingWithTypePredicate = bookingWithTypePredicate;
    }


    @Override

    public CommandResult execute(BillModel model, CommandHistory history) throws CommandException {

        requireNonNull(model);

        model.updateFilteredBookingList(bookingPredicate);
        model.updateBill(bill);
        model.commitHotelManagementSystem();


        return new CommandResult(String.format(MESSAGE_GENERATE_BILL_SUCCESS, bill.getCustomer().getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof GenerateBillForBookingCommand // instanceof handles nulls
            && bookingContainsPayerPredicate.equals(((GenerateBillForBookingCommand) other)
            .bookingContainsPayerPredicate)
            && bookingWithTypePredicate.equals(((GenerateBillForBookingCommand) other).bookingWithTypePredicate)
            && bookingWithinTimePredicate.equals(((GenerateBillForBookingCommand) other).bookingWithinTimePredicate));
    }
}

