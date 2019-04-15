package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.FINISHED_STATUS_TRUE;

import org.junit.Test;

import seedu.address.logic.commands.DisplaymodCommand;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.testutil.ModuleInfoBuilder;

public class DisplaymodCommandPaserTest {
    private DisplaymodCommandParser parser = new DisplaymodCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplaymodCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplaymodCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "cs", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplaymodCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidInput_failure() {
        String invalidInput = "CS1010,CS2010";
        assertParseFailure(parser, invalidInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DisplaymodCommand.MESSAGE_USAGE));
        invalidInput = "software+Engineering";
        assertParseFailure(parser, invalidInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DisplaymodCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidcode_failture() {
        String invalidInput = "C1010";
        assertParseFailure(parser, invalidInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DisplaymodCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidword_failure() {
        String invalidInput = "@";
        assertParseFailure(parser, invalidInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DisplaymodCommand.MESSAGE_USAGE));
    }
}
