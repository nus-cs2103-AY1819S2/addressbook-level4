package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RotateCommand;

/**
 * This tests the various inputs that users can possibly input into the rotate command.
 */
public class RotateCommandParserTest {

    private RotateCommandParser parser = new RotateCommandParser();

    @Test
    public void parse_validArgs_returnsRotateCommand() {
        assertParseSuccess(parser, " 90", new RotateCommand(90));
        assertParseSuccess(parser, " 180", new RotateCommand(180));
        assertParseSuccess(parser, " 270", new RotateCommand(270));
        assertParseSuccess(parser, " 150", new RotateCommand(150));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " abc", String.format(Messages.MESSAGE_ROTATE_DEGREE_ERROR,
                RotateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 90.5", String.format(Messages.MESSAGE_ROTATE_DEGREE_ERROR,
                RotateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -20", String.format(Messages.MESSAGE_ROTATE_DEGREE_ERROR,
                RotateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 90 190", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                RotateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_ROTATE_DEGREE_ERROR,
                RotateCommand.MESSAGE_USAGE));
    }
}
