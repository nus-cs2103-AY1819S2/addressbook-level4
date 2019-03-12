package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.InitialiseMapCommand;

public class InitialiseMapCommandParserTest {

    private InitialiseMapCommandParser parser = new InitialiseMapCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "6", new InitialiseMapCommand(6));
    }

    @Test
    public void parse_missingArgs_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InitialiseMapCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_argsNotPositiveInt_failure() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InitialiseMapCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InitialiseMapCommand.MESSAGE_USAGE));
    }
}
