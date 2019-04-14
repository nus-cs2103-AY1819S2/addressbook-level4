package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.GenerateBillForCustomerCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BillManager;
import seedu.hms.model.BillModel;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * Parses input arguments and creates a new GenerateBillForCustomerCommand object
 */
public class GenerateBillForCustomerCommandParser implements Parser<GenerateBillForCustomerCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the GenerateBillForReservationCommand
     * and returns an GenerateBillForReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForCustomerCommand parse(String args, CustomerModel customerModel, BillModel billModel)
        throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenerateBillForCustomerCommand.MESSAGE_USAGE),
                pe);
        }

        List<Customer> lastShownList = customerModel.getFilteredCustomerList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new ParseException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        //Finds the selected customer
        Customer customer = lastShownList.get(index.getZeroBased());

        // Customer selected
        IdentificationNo payerIdentificationNo = customer.getIdNum();
        String payerId = payerIdentificationNo.toString();

        //Booking bill
        BookingContainsPayerPredicate bookingContainsPayerPredicate = new BookingContainsPayerPredicate(payerId);
        Predicate<Booking> bookingPredicate;
        bookingPredicate = bookingContainsPayerPredicate;
        billModel.updateFilteredBookingList(bookingPredicate);
        ObservableList<Booking> bookingObservableList = billModel.getFilteredBookingList();
        HashMap<ServiceType, Pair<Double, Integer>> bookingBill =
            billModel.generateHashMapForBooking(bookingObservableList);

        //Reservation bill
        ReservationContainsPayerPredicate reservationContainsPayerPredicate =
            new ReservationContainsPayerPredicate(payerId);
        Predicate<Reservation> reservationPredicate;
        reservationPredicate = reservationContainsPayerPredicate;
        billModel.updateFilteredReservationList(reservationPredicate);
        ObservableList<Reservation> reservationObservableList = billModel.getFilteredReservationList();
        HashMap<RoomType, Pair<Double, Long>> reservationBill =
            billModel.generateHashMapForReservation(reservationObservableList);

        Bill bill = new Bill(customer, bookingBill, reservationBill);

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
