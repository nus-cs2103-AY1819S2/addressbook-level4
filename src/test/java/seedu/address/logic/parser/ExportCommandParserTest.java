package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.ExportCommand;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_allFields_present() {
        // whitespace preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FOLDER_DESC_SAMPLE_1
                + FILENAME_DESC_SAMPLE, new ExportCommand(Arrays.asList(VALID_FOLDER_NAME_1), VALID_FILENAME));

        // multiple folders
        assertParseSuccess(parser, FOLDER_DESC_SAMPLE_1 + FOLDER_DESC_SAMPLE_2
                + FILENAME_DESC_SAMPLE, new ExportCommand(Arrays.asList(VALID_FOLDER_NAME_1,
                VALID_FOLDER_NAME_2), VALID_FILENAME));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

        // missing folder prefix
        assertParseFailure(parser, VALID_FOLDER_NAME_1
                + FILENAME_DESC_SAMPLE, String.format(expectedMessage));

        // missing filename prefix
        assertParseFailure(parser, FOLDER_DESC_SAMPLE_1
                + VALID_FILENAME, String.format(expectedMessage));

        // missing all prefix
        assertParseFailure(parser, VALID_FOLDER_NAME_1
                + VALID_FILENAME, String.format(expectedMessage));
    }
}
