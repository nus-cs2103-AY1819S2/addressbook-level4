package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK_A;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ImportDeckCommand;

public class ImportDeckCommandParserTest {
    private ImportDeckCommandParser parser = new ImportDeckCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        assertParseSuccess(parser, VALID_NAME_DECK_A,
                new ImportDeckCommand(VALID_NAME_DECK_A));

        // whitespace only
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_NAME_DECK_A,
                new ImportDeckCommand(VALID_NAME_DECK_A));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportDeckCommand.MESSAGE_USAGE);

        // No argument
        assertParseFailure(parser, "", expectedMessage);
    }
}
