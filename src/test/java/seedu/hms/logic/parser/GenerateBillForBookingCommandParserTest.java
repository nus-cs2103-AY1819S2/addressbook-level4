package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.hms.logic.commands.GenerateBillForBookingCommand;

public class GenerateBillForBookingCommandParserTest {

    private GenerateBillForBookingCommandParser parser = new GenerateBillForBookingCommandParser();

    @Test
    public void parse_wrongArg_returnsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            GenerateBillForBookingCommand.MESSAGE_USAGE));
    }
}
