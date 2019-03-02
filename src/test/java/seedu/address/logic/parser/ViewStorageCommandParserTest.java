package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewStorageCommand;

public class ViewStorageCommandParserTest {

    private ViewStorageCommandParser parser = new ViewStorageCommandParser();

    @Test
    public void validArgument_returnsViewStorageCommand() {
        assertParseSuccess(parser, "root\\TCM\\Healroot",
                new ViewStorageCommand(new String[] {"root", "TCM", "Healroot"}));
    }

    @Test
    public void blankArgument_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStorageCommand.MESSAGE_USAGE));
    }
}
