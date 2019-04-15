package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.hms.logic.commands.FindBookingCommand;
import seedu.hms.model.booking.BookingContainsPayerPredicate;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.BookingWithinTimePredicate;
import seedu.hms.model.util.TimeRange;

public class FindBookingCommandParserTest {

    private FindBookingCommandParser parser = new FindBookingCommandParser();

    @Test
    public void parse_wrongArg_returnsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindBookingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_singleArg_returnsFindCommand() {
        FindBookingCommand expectedFindBookingCommand =
            new FindBookingCommand(new BookingContainsPayerPredicate(""),
                new BookingWithTypePredicate(""), new BookingWithinTimePredicate(new TimeRange(14, 15)));
        assertParseSuccess(parser, " :/14 - 15 ", expectedFindBookingCommand);
    }

    @Test
    public void parse_multipleArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        BookingContainsPayerPredicate bookingContainsPayerPredicate =
            new BookingContainsPayerPredicate("1231212A");
        BookingWithinTimePredicate bookingWithinTimePredicate = new BookingWithinTimePredicate(new TimeRange(14, 16));
        BookingWithTypePredicate bookingWithTypePredicate = new BookingWithTypePredicate("");
        FindBookingCommand expectedFindBookingCommand =
            new FindBookingCommand(bookingContainsPayerPredicate,
                bookingWithTypePredicate, bookingWithinTimePredicate);
        assertParseSuccess(parser, " id/1231212A " + ":/14-16", expectedFindBookingCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n id/1231212A \n \t :/14-16  \t", expectedFindBookingCommand);
    }

}
