package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.hms.logic.commands.GenerateBillForCustomerCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BillManager;
import seedu.hms.model.BillModel;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;

/**
 * Parses input arguments and creates a new AddCustomerCommand object
 */
public class GenerateBillForCustomerCommandParser implements Parser<GenerateBillForCustomerCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


    /**
     * Parses the given {@code String} of arguments in the context of the GenerateBillForReservationCommand
     * and returns an GenerateBillForReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForCustomerCommand parse(String args, CustomerModel customerModel, BillModel billModel)
        throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenerateBillForCustomerCommand.MESSAGE_USAGE));
        }

        // Customer selected
        Customer payer = ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_INDEX).get(),
            customerModel.getFilteredCustomerList());
        IdentificationNo payerIdentificationNo = payer.getIdNum();
        String payerId = payerIdentificationNo.toString();

        //Booking bill
        BookingContainsPayerPredicate bookingContainsPayerPredicate = new BookingContainsPayerPredicate(payerId);
        Predicate<Booking> bookingPredicate;
        bookingPredicate = (bookingTested) -> bookingContainsPayerPredicate.test(bookingTested);
        billModel.updateFilteredBookingList(bookingPredicate);
        ObservableList<Booking> bookingObservableList = billModel.getFilteredBookingList();
        HashMap<String, Pair<Double, Integer>> bookingBill = billModel.generateHashMapForBooking(bookingObservableList);

        //Reservation bill
        ReservationContainsPayerPredicate reservationContainsPayerPredicate =
            new ReservationContainsPayerPredicate(payerId);
        Predicate<Reservation> reservationPredicate;
        reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested);
        billModel.updateFilteredReservationList(reservationPredicate);
        ObservableList<Reservation> reservationObservableList = billModel.getFilteredReservationList();
        HashMap<String, Pair<Double, Integer>> reservationBill =
            billModel.generateHashMapForReservation(reservationObservableList);

        // total amount for booking
        double amountBooking = billModel.generateBillForBooking(bookingObservableList);

        //total amount for reservation
        double amountReservation = billModel.generateBillForReservation(reservationObservableList);

        Bill bill = new Bill(payer, amountReservation, amountBooking, bookingBill, reservationBill);

        return new GenerateBillForCustomerCommand(bookingContainsPayerPredicate, reservationContainsPayerPredicate,
            bill);

    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddReservationCommand
     * and returns an AddReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForCustomerCommand parse(String args) throws ParseException {
        return parse(args, new CustomerManager(), new BillManager());
    }


}
