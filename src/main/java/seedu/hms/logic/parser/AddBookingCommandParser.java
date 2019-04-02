package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_OTHER_USERS_INDICES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.hms.logic.commands.AddBookingCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.util.TimeRange;

/**
 * Parses input arguments and creates a new AddCustomerCommand object
 */
public class AddBookingCommandParser implements Parser<AddBookingCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddBookingCommand
     * and returns an AddBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBookingCommand parse(String args, CustomerModel customerModel)
        throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_SERVICE, PREFIX_TIMING, PREFIX_PAYER, PREFIX_CUSTOMERS,
                PREFIX_COMMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SERVICE, PREFIX_TIMING, PREFIX_PAYER)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookingCommand.MESSAGE_USAGE));
        }

        ServiceType serviceType = ParserUtil.parseService(argMultimap.getValue(PREFIX_SERVICE).get());
        TimeRange timing = ParserUtil.parseTiming(argMultimap.getValue(PREFIX_TIMING).get());
        Customer payer = ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_PAYER).get(),
            customerModel.getFilteredCustomerList());
        Optional<List<Customer>> otherUsers = ParserUtil.parseCustomers(argMultimap.getAllValues(PREFIX_CUSTOMERS),
            customerModel.getFilteredCustomerList());
        Optional<String> comment = argMultimap.getValue(PREFIX_COMMENT);

        if (otherUsers.filter(l -> l.contains(payer)).isPresent()) {
            throw new ParseException(MESSAGE_INVALID_OTHER_USERS_INDICES);
        }

        Booking booking = new Booking(serviceType, timing, payer, otherUsers, comment);

        return new AddBookingCommand(booking);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddBookingCommand
     * and returns an AddBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBookingCommand parse(String args) throws ParseException {
        return parse(args, new CustomerManager());
    }

}
