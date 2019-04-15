package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        //All fields present.
        assertParseSuccess(parser, " f/123 d/abc", new ExportCommand("abc", "123"));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

        // missing filename prefix
        assertParseFailure(parser, " 123 d/abc",
            expectedMessage);

        // missing directory prefix
        assertParseFailure(parser, " f/abc 123",
            expectedMessage);

        // missing both prefix
        assertParseFailure(parser, "abc 123",
            expectedMessage);
    }
}
