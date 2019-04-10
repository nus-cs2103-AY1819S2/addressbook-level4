package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.hms.logic.commands.FindReservationCommand;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithTypePredicate;

public class FindReservationCommandParserTest {

    private FindReservationCommandParser parser = new FindReservationCommandParser();

    @Test
    public void parse_wrongArg_returnsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindReservationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_returnsFindCommand() {
        FindReservationCommand expectedFindReservationCommand =
            new FindReservationCommand(new ReservationContainsPayerPredicate(""),
                new ReservationWithTypePredicate(""));
        assertParseSuccess(parser, "", expectedFindReservationCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ReservationContainsPayerPredicate reservationContainsPayerPredicate = new ReservationContainsPayerPredicate(
            "1231212A");
        ReservationWithTypePredicate reservationWithTypePredicate = new ReservationWithTypePredicate("");
        FindReservationCommand expectedFindReservationCommand =
            new FindReservationCommand(reservationContainsPayerPredicate,
                reservationWithTypePredicate);
        assertParseSuccess(parser, " id/1231212A ", expectedFindReservationCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n id/1231212A \n \t  \t", expectedFindReservationCommand);
    }

}
