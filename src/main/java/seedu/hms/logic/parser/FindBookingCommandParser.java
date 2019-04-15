package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import seedu.hms.logic.commands.FindBookingCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;
import seedu.hms.model.util.TimeRange;

/**
 * Parses input arguments and creates a new FindBookingCommand object
 */
public class FindBookingCommandParser implements Parser<FindBookingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindBookingCommand
     * and returns an FindBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindBookingCommand parse(String args, BookingModel bookingModel) throws ParseException {
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
                ParserUtil.parseService(argMultimap.getValue(PREFIX_SERVICE).get(), bookingModel).getName());
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

    @Override
    public FindBookingCommand parse(String args) throws ParseException {
        return parse(args, new BookingManager());
    }


}
