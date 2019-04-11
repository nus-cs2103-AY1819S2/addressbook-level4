/* @@author kayheen */
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CropCommand;

/**
 * This tests the various inputs that users can possibly input into a crop command.
 */
public class CropCommandParserTest {

    private CropCommandParser parser = new CropCommandParser();

    @Test
    public void parse_validArgs_returnsCropCommand() {
        assertParseSuccess(parser, " 0 0 200 200", new CropCommand(0, 0, 200, 200));
        assertParseSuccess(parser, " 20 20 100 100", new CropCommand(20, 20, 100, 100));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " abc cdf ghi jkl", String.format(Messages.MESSAGE_CROP_INT_ERROR,
                CropCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 90.5 20 20 20", String.format(Messages.MESSAGE_CROP_INT_ERROR,
                CropCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -2 0 200 200", String.format(Messages.MESSAGE_CROP_INT_ERROR,
                CropCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 90 100 200 50 79", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                CropCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_CROP_INT_ERROR,
                CropCommand.MESSAGE_USAGE));
    }
}
