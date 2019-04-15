package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.options.SortOption;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        String userInput = "name";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput)));
        userInput = "color";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput)));
        userInput = "type";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = "nameX";
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
