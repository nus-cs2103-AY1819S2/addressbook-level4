package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.hms.logic.commands.FindReservationCommand;

public class FindReservationCommandParserTest {

    private FindReservationCommandParser parser = new FindReservationCommandParser();

    @Test
    public void parse_wrongArg_returnsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindReservationCommand.MESSAGE_USAGE));
    }

}
