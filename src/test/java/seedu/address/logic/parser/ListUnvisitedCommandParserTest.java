package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTAL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ListUnvisitedCommand;
import seedu.address.model.restaurant.Postal;


public class ListUnvisitedCommandParserTest {
    private ListUnvisitedParser parser = new ListUnvisitedParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Postal expectedPostal = new Postal(VALID_POSTAL_BOB);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POSTAL_DESC_BOB,
                new ListUnvisitedCommand(expectedPostal));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_POSTAL_DESC, Postal.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListUnvisitedCommand.MESSAGE_USAGE);
        // missing name prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);

    }
}
