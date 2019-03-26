package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.stream.Stream;

import seedu.hms.logic.commands.FindBookingCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;
import seedu.hms.model.util.TimeRange;

/**
 * Parses input arguments and creates a new AddCustomerCommand object
 */
public class FindBookingCommandParser implements Parser<FindBookingCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCustomerCommand
     * and returns an AddCustomerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindBookingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IDENTIFICATION_NUMBER,
                        PREFIX_SERVICE, PREFIX_TIMING);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBookingCommand.MESSAGE_USAGE));
        }

        BookingContainsPayerPredicate bookingContainsPayerPredicate;
        if (argMultimap.getValue(PREFIX_IDENTIFICATION_NUMBER).isPresent()) {
            bookingContainsPayerPredicate = new BookingContainsPayerPredicate(
                    ParserUtil.parseIdNum(argMultimap.getValue(PREFIX_IDENTIFICATION_NUMBER).get()).toString());
        } else {
            bookingContainsPayerPredicate = new BookingContainsPayerPredicate("");
        }

        BookingWithTypePredicate bookingWithTypePredicate;
        if (argMultimap.getValue(PREFIX_SERVICE).isPresent()) {
            bookingWithTypePredicate = new BookingWithTypePredicate(
                    ParserUtil.parseService(argMultimap.getValue(PREFIX_SERVICE).get()).getName());
        } else {
            bookingWithTypePredicate = new BookingWithTypePredicate("");
        }

        //search in whole day if timing is not provided
        TimeRange timeRange = ParserUtil.parseTiming(argMultimap.getValue(PREFIX_TIMING).orElse("0 - 23"));
        BookingWithinTimePredicate bookingWithinTimePredicate = new BookingWithinTimePredicate(timeRange);

        return new FindBookingCommand(bookingContainsPayerPredicate,
                bookingWithTypePredicate,
                bookingWithinTimePredicate);
    }


}
