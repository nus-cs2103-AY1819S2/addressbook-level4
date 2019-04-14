package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.GenerateBillForBookingCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BillManager;
import seedu.hms.model.BillModel;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.util.TimeRange;

/**
 * Parses input arguments and creates a new GenerateBillForBookingCommand object
 */
public class GenerateBillForBookingCommandParser implements Parser<GenerateBillForBookingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCustomerCommand
     * and returns an GenerateBillForBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForBookingCommand parse(String args, CustomerModel customerModel, BillModel billModel,
                                               BookingModel bookingModel)
        throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_SERVICE, PREFIX_TIMING);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenerateBillForBookingCommand.MESSAGE_USAGE),
                pe);
        }

        List<Customer> lastShownList = customerModel.getFilteredCustomerList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new ParseException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        //Finds the selected customer
        Customer customer = lastShownList.get(index.getZeroBased());
        IdentificationNo payerIdentificationNo = customer.getIdNum();
        String payerId = payerIdentificationNo.toString();

        BookingContainsPayerPredicate bookingContainsPayerPredicate = new BookingContainsPayerPredicate(payerId);

        //Find the selected service type
        BookingWithTypePredicate bookingWithTypePredicate;
        if (argMultimap.getValue(PREFIX_SERVICE).isPresent()) {
            bookingWithTypePredicate = new BookingWithTypePredicate(
                ParserUtil.parseService(argMultimap.getValue(PREFIX_SERVICE).get(), bookingModel).getName());
        } else {
            bookingWithTypePredicate = new BookingWithTypePredicate("");
        }

        //Search in whole day if timing is not provided
        TimeRange timeRange = ParserUtil.parseTiming(argMultimap.getValue(PREFIX_TIMING).orElse("0 - 23"));
        BookingWithinTimePredicate bookingWithinTimePredicate = new BookingWithinTimePredicate(timeRange);

        //Booking bill
        final Predicate<Booking> bookingPredicate;
        bookingPredicate = (bookingTested) -> bookingContainsPayerPredicate.test(bookingTested)
            && bookingWithTypePredicate.test(bookingTested)
            && bookingWithinTimePredicate.test(bookingTested);
        billModel.updateFilteredBookingList(bookingPredicate);
        ObservableList<Booking> bookingObservableList = billModel.getFilteredBookingList();
        HashMap<ServiceType, Pair<Double, Integer>> bookingBill =
            billModel.generateHashMapForBooking(bookingObservableList);

        // total amount for booking
        double amountBooking = billModel.generateBillForBooking(bookingObservableList);

        Bill bill = new Bill(customer, bookingBill, new HashMap<>());


        return new GenerateBillForBookingCommand(bookingContainsPayerPredicate,
            bookingWithTypePredicate,
            bookingWithinTimePredicate, bill);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddReservationCommand
     * and returns an AddReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForBookingCommand parse(String args) throws ParseException {
        return parse(args, new CustomerManager(), new BillManager(), new BookingManager());
    }


}
