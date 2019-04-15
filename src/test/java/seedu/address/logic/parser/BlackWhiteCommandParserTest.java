/* @@author thamsimun */
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.OptionalInt;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.BlackWhiteCommand;

public class BlackWhiteCommandParserTest {

    private BlackWhiteCommandParser parser = new BlackWhiteCommandParser();

    @Test
    public void parse_validArgs_returnsBlackWhiteCommand() {
        assertParseSuccess(parser, " 123", new BlackWhiteCommand(OptionalInt.of(123)));
        assertParseSuccess(parser, " 9", new BlackWhiteCommand(OptionalInt.of(9)));
        assertParseSuccess(parser, "", new BlackWhiteCommand(OptionalInt.empty()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " abc", String.format(Messages.MESSAGE_BLACKWHITE_INT_ERROR,
            BlackWhiteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -13", String.format(Messages.MESSAGE_NEGATIVE_ERROR,
            BlackWhiteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 123 13", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            BlackWhiteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 134 132 4", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            BlackWhiteCommand.MESSAGE_USAGE));
    }
}
/* @@author*/
