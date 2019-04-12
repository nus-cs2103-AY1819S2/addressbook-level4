package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;

import java.util.function.Predicate;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BillModel;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithDatePredicate;
import seedu.hms.model.reservation.ReservationWithTypePredicate;

/**
 * Generates the bill for a customer who is identified using displayed index from hms book
 */
public class GenerateBillForReservationCommand extends BillCommand {

    public static final String COMMAND_ALIAS = "gb-r";
    public static final String COMMAND_WORD = "generate-bill-reservation";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates reservation bill for the customer identified by the index number\nused in the displayed "
        + "customer list.\n"
        + "Parameters: INDEX "
        + "[" + PREFIX_ROOM + "ROOM TYPE] "
        + "[" + PREFIX_DATES + "DATES(DD/MM/YYYY - DD/MM/YYYY)]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_ROOM + "SINGLE ROOM "
        + "[" + PREFIX_DATES + "12/12/2019 - 14/12/2019]";

    public static final String MESSAGE_GENERATE_BILL_FOR_RESERVATION_SUCCESS = "Reservation bill generated for "
        + "customer: %1$s";

    private final Predicate<Reservation> reservationPredicate;
    private final Bill bill;
    private final ReservationContainsPayerPredicate reservationContainsPayerPredicate;
    private final ReservationWithTypePredicate reservationWithTypePredicate;
    private final ReservationWithDatePredicate reservationWithDatePredicate;


    public GenerateBillForReservationCommand(ReservationContainsPayerPredicate reservationContainsPayerPredicate,
                                             ReservationWithTypePredicate reservationWithTypePredicate,
                                             ReservationWithDatePredicate reservationWithDatePredicate,
                                             Bill bill) {

        this.reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested)
            && reservationWithDatePredicate.test(reservationTested);
        this.bill = bill;
        this.reservationContainsPayerPredicate = reservationContainsPayerPredicate;
        this.reservationWithTypePredicate = reservationWithTypePredicate;
        this.reservationWithDatePredicate = reservationWithDatePredicate;
    }


    @Override

    public CommandResult execute(BillModel model, CommandHistory history) throws CommandException {

        requireNonNull(model);

        model.updateFilteredReservationList(reservationPredicate);
        model.updateBill(bill);
        model.commitHotelManagementSystem();


        return new CommandResult(String.format(MESSAGE_GENERATE_BILL_FOR_RESERVATION_SUCCESS,
            bill.getCustomer().getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof GenerateBillForReservationCommand // instanceof handles nulls
            && reservationContainsPayerPredicate.equals(((GenerateBillForReservationCommand) other)
            .reservationContainsPayerPredicate)
            && reservationWithTypePredicate.equals(((GenerateBillForReservationCommand) other)
            .reservationWithTypePredicate)
            && reservationWithDatePredicate.equals(((GenerateBillForReservationCommand) other)
            .reservationWithDatePredicate));
    }
}
