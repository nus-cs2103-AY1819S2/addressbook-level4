package seedu.address.logic.parser;

import seedu.address.logic.commands.UnpinCommand;

import org.junit.Test;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class UnpinCommandParserTest {
    private UnpinCommandParser parser = new UnpinCommandParser();

    @Test
    public void parse_validArguments_returnsCustomOrderCommand() {
        assertParseSuccess(
                parser,
                "1",
                new UnpinCommand(1));
    }

    @Test
    public void parse_invalidArguments_throwsParseException() {
        assertParseFailure(
                parser,
                "a b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE));
    }
}
