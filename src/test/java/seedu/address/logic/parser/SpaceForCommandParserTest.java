package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SpaceForCommand;

public class SpaceForCommandParserTest {
    private SpaceForCommandParser parser = new SpaceForCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "3", new SpaceForCommand(3));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpaceForCommand.MESSAGE_USAGE);

        // missing inputs
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid number of customers
        assertParseFailure(parser, "a", SpaceForCommand.MESSAGE_CONSTRAINT);
        assertParseFailure(parser, "0", SpaceForCommand.MESSAGE_CONSTRAINT);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "3", SpaceForCommand.MESSAGE_CONSTRAINT);
    }

    @Test
    public void parse_invalidNumberOfInputs_failure() {
        // extra inputs
        assertParseFailure(parser, "1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpaceForCommand.MESSAGE_USAGE));
    }
}
