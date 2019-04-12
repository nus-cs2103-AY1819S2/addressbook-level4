/* @@author thamsimun */
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.OptionalDouble;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ContrastCommand;


public class ContrastCommandParserTest {
    private ContrastCommandParser parser = new ContrastCommandParser();

    @Test
    public void parse_validArgs_returnsContrastCommand() {
        assertParseSuccess(parser, " 1.3", new ContrastCommand(OptionalDouble.of(1.3)));
        assertParseSuccess(parser, "0.5", new ContrastCommand(OptionalDouble.of(0.5)));
        assertParseSuccess(parser, "", new ContrastCommand(OptionalDouble.empty()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " abc", String.format(Messages.MESSAGE_CONTRAST_DOUBLE_ERROR,
            ContrastCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1.3f", String.format(Messages.MESSAGE_CONTRAST_DOUBLE_ERROR,
            ContrastCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1.3d", String.format(Messages.MESSAGE_CONTRAST_DOUBLE_ERROR,
            ContrastCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -1.3", String.format(Messages.MESSAGE_NEGATIVE_ERROR,
            ContrastCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1.3 1.3", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            ContrastCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1.3 1.3 0.4", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            ContrastCommand.MESSAGE_USAGE));
    }
}
/* @@author*/
