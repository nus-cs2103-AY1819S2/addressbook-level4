/* @@author thamsimun */
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.OptionalDouble;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.BrightnessCommand;

public class BrightnessCommandParserTest {

    private BrightnessCommandParser parser = new BrightnessCommandParser();

    @Test
    public void parse_validArgs_returnsBrightnessCommand() {
        assertParseSuccess(parser, " 1.3", new BrightnessCommand(OptionalDouble.of(1.3)));
        assertParseSuccess(parser, "0.5", new BrightnessCommand(OptionalDouble.of(0.5)));
        assertParseSuccess(parser, "", new BrightnessCommand(OptionalDouble.empty()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " abc", String.format(Messages.MESSAGE_BRIGHTNESS_DOUBLE_ERROR,
            BrightnessCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1.3f", String.format(Messages.MESSAGE_BRIGHTNESS_DOUBLE_ERROR,
            BrightnessCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1.3d", String.format(Messages.MESSAGE_BRIGHTNESS_DOUBLE_ERROR,
            BrightnessCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -1.3", String.format(Messages.MESSAGE_NEGATIVE_ERROR,
            BrightnessCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1.3 1.3", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            BrightnessCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1.3 1.3 0.4", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            BrightnessCommand.MESSAGE_USAGE));
    }
}
/* @@author*/
