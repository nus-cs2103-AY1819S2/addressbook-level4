package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUYER;

import org.junit.Test;

import seedu.address.logic.commands.ArchiveSelectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class ArchiveSelectCommandParserTest {

    private ArchiveSelectCommandParser parser = new ArchiveSelectCommandParser();

    @Test
    public void parse_validArgs_returnsArchiveSelectCommand() {
        assertParseSuccess(parser, "1", new ArchiveSelectCommand(INDEX_FIRST_BUYER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveSelectCommand.MESSAGE_USAGE));
    }
}
