package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.List;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.GenerateBillForBookingCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.util.TimeRange;

/**
 * Parses input arguments and creates a new GenerateBillForBookingCommand object
 */
public class GenerateBillForBookingCommandParser implements Parser<GenerateBillForBookingCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the AddCustomerCommand
     * and returns an AddCustomerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForBookingCommand parse(String args, CustomerModel customerModel) throws ParseException {
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenerateBillForBookingCommand.MESSAGE_USAGE));
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
                ParserUtil.parseService(argMultimap.getValue(PREFIX_SERVICE).get()).getName());
        } else {
            bookingWithTypePredicate = new BookingWithTypePredicate("");
        }

        //Search in whole day if timing is not provided
        TimeRange timeRange = ParserUtil.parseTiming(argMultimap.getValue(PREFIX_TIMING).orElse("0 - 23"));
        BookingWithinTimePredicate bookingWithinTimePredicate = new BookingWithinTimePredicate(timeRange);

        return new GenerateBillForBookingCommand(bookingContainsPayerPredicate,
            bookingWithTypePredicate,
            bookingWithinTimePredicate);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddReservationCommand
     * and returns an AddReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForBookingCommand parse(String args) throws ParseException {
        return parse(args, new CustomerManager());
    }


}
