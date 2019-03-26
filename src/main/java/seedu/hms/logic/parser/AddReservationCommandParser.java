package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.hms.logic.commands.AddReservationCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.RoomType;
import seedu.hms.model.util.DateRange;

/**
 * Parses input arguments and creates a new AddCustomerCommand object
 */
public class AddReservationCommandParser implements Parser<AddReservationCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddReservationCommand
     * and returns an AddReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReservationCommand parse(String args, CustomerModel customerModel)
        throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_ROOM, PREFIX_DATES, PREFIX_PAYER, PREFIX_CUSTOMERS,
                PREFIX_COMMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_ROOM, PREFIX_DATES, PREFIX_PAYER)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddReservationCommand.MESSAGE_USAGE));
        }

        RoomType roomType = ParserUtil.parseRoom(argMultimap.getValue(PREFIX_ROOM).get());
        DateRange dates = ParserUtil.parseDates(argMultimap.getValue(PREFIX_DATES).get());
        Customer payer = ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_PAYER).get(),
            customerModel.getFilteredCustomerList());
        Optional<List<Customer>> otherUsers = ParserUtil.parseCustomers(argMultimap.getAllValues(PREFIX_CUSTOMERS),
            customerModel.getFilteredCustomerList());
        Optional<String> comment = argMultimap.getValue(PREFIX_COMMENT);

        Reservation reservation = new Reservation(roomType, dates, payer, otherUsers, comment);

        return new AddReservationCommand(reservation);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddReservationCommand
     * and returns an AddReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReservationCommand parse(String args) throws ParseException {
        return parse(args, new CustomerManager());
    }

}
