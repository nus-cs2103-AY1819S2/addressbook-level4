/* @@author kayheen */
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ResizeCommand;

/**
 * This tests the various inputs that users can possibly input in a resize command.
 */
public class ResizeCommandParserTest {

    private ResizeCommandParser parser = new ResizeCommandParser();

    @Test
    public void parse_validArgs_returnsResizeCommand() {
        assertParseSuccess(parser, " 200 200", new ResizeCommand(200, 200));
        assertParseSuccess(parser, " 10 20", new ResizeCommand(10, 20));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " abc cdf", String.format(Messages.MESSAGE_RESIZE_VALUE_ERROR,
                ResizeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 90.5 20", String.format(Messages.MESSAGE_RESIZE_VALUE_ERROR,
                ResizeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -2 -100", String.format(Messages.MESSAGE_RESIZE_VALUE_ERROR,
                ResizeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 90 100 200", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ResizeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ResizeCommand.MESSAGE_USAGE));
    }
}
