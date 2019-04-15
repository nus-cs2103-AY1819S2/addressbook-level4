/* @@author kayheen */
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.WaterMarkCommand;

public class WaterMarkCommandParserTest {
    private WaterMarkCommandParser parser = new WaterMarkCommandParser();

    @Test
    public void parse_validArgsWithTrueIsNewCommand_returnsWaterMarkCommand() {
        assertParseSuccess(parser, " hellohello123", new WaterMarkCommand("hellohello123",
                true));
        assertParseSuccess(parser, " 你好你好你好123", new WaterMarkCommand("你好你好你好123",
                true));
        assertParseSuccess(parser, " hello this string is long", new WaterMarkCommand("hello this string"
                + " is long", true));
        assertParseSuccess(parser, " 234987129$#%@", new WaterMarkCommand("234987129$#%@",
                true));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                WaterMarkCommand.MESSAGE_USAGE));
    }
}
