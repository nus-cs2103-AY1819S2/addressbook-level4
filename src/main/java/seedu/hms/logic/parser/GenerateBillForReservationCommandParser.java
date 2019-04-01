package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;

import java.util.stream.Stream;

import seedu.hms.logic.commands.GenerateBillForReservationCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithDatePredicate;
import seedu.hms.model.reservation.ReservationWithTypePredicate;
import seedu.hms.model.util.DateRange;

/**
 * Parses input arguments and creates a new AddCustomerCommand object
 */
public class GenerateBillForReservationCommandParser implements Parser<GenerateBillForReservationCommand> {

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
    public GenerateBillForReservationCommand parse(String args, CustomerModel customerModel) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_INDEX,
                PREFIX_ROOM, PREFIX_DATES);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenerateBillForReservationCommand.MESSAGE_USAGE));
        }

        Customer payer = ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_INDEX).get(),
            customerModel.getFilteredCustomerList());
        IdentificationNo payerIdentificationNo = payer.getIdNum();
        String payerId = payerIdentificationNo.toString();

        ReservationContainsPayerPredicate reservationContainsPayerPredicate =
            new ReservationContainsPayerPredicate(payerId);

        ReservationWithTypePredicate reservationWithTypePredicate;
        if (argMultimap.getValue(PREFIX_ROOM).isPresent()) {
            reservationWithTypePredicate = new ReservationWithTypePredicate(
                ParserUtil.parseRoom(argMultimap.getValue(PREFIX_ROOM).get()).getName());
        } else {
            reservationWithTypePredicate = new ReservationWithTypePredicate("");
        }

        //search in whole day if timing is not provided
        DateRange dateRange = ParserUtil.parseDates(argMultimap.getValue(PREFIX_DATES)
            .orElse("01/01/0000 - 31/12/9999"));
        ReservationWithDatePredicate reservationWithinDatePredicate = new ReservationWithDatePredicate(dateRange);

        return new GenerateBillForReservationCommand(reservationContainsPayerPredicate,
            reservationWithTypePredicate,
            reservationWithinDatePredicate);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddReservationCommand
     * and returns an AddReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForReservationCommand parse(String args) throws ParseException {
        return parse(args, new CustomerManager());
    }

}
