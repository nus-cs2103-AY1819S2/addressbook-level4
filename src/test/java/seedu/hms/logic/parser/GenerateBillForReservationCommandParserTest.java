package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.hms.logic.commands.GenerateBillForReservationCommand;

public class GenerateBillForReservationCommandParserTest {

    private GenerateBillForReservationCommandParser parser = new GenerateBillForReservationCommandParser();

    @Test
    public void parse_wrongArg_returnsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            GenerateBillForReservationCommand.MESSAGE_USAGE));
    }
}
