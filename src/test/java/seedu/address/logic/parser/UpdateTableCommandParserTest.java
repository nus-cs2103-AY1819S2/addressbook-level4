package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.UpdateTableCommand;

public class UpdateTableCommandParserTest {
    private UpdateTableCommandParser parser = new UpdateTableCommandParser();
    private String[] tableStatusInString = new String[]{"1", "4"};

    //    @Test TODO
    //    public void parse_allFieldsPresent_success() {
    //        // whitespace only preamble
    //        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1 4", new UpdateTableCommand(tableStatusInString));
    //    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTableCommand.MESSAGE_USAGE);

        // missing inputs
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid table number
        assertParseFailure(parser, "a 0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTableCommand.MESSAGE_USAGE));

        // invalid new table status
        assertParseFailure(parser, "1 @",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTableCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "4", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateTableCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNumberOfInputs_failure() {
        // extra inputs
        assertParseFailure(parser, "1 2 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateTableCommand.MESSAGE_USAGE));

        // insufficient inputs
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateTableCommand.MESSAGE_USAGE));
    }
}
