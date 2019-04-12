/* @@author thamsimun */
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.SetPresetCommand;

public class SetPresetCommandParserTest {
    private SetPresetCommandParser parser = new SetPresetCommandParser();

    @Test
    public void parse_validArgsWithTrueIsNewCommand_returnsSetPresetCommand() {
        assertParseSuccess(parser, " hellohello123", new SetPresetCommand("hellohello123"));
        assertParseSuccess(parser, " 你好你好你好123", new SetPresetCommand("你好你好你好123"));
        assertParseSuccess(parser, " hello this string is long", new SetPresetCommand("hello this "
            + "string is long"));
        assertParseSuccess(parser, " 234987129$#%@", new SetPresetCommand("234987129$#%@"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            SetPresetCommand.MESSAGE_USAGE));
    }
}
/* @@author*/
