/* @@author thamsimun */
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.SavePresetCommand;

public class SavePresetCommandParserTest {
    private SavePresetCommandParser parser = new SavePresetCommandParser();

    @Test
    public void parse_validArgsWithTrueIsNewCommand_returnsSavePresetCommand() {
        assertParseSuccess(parser, " hellohello123", new SavePresetCommand("hellohello123"));
        assertParseSuccess(parser, " 你好你好你好123", new SavePresetCommand("你好你好你好123"));
        assertParseSuccess(parser, " hello this string is long", new SavePresetCommand("hello this "
            + "string is long"));
        assertParseSuccess(parser, " 234987129$#%@", new SavePresetCommand("234987129$#%@"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            SavePresetCommand.MESSAGE_USAGE));
    }

}
/* @@author*/
