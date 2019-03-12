package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.ExportCommand;

import java.util.Arrays;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_allFields_present() {
        // one folder
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FOLDER_DESC_SAMPLE_1
                + FILENAME_DESC_SAMPLE, new ExportCommand(Arrays.asList(VALID_FOLDER_NAME_1), VALID_FILENAME));

        // multiple folders
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FOLDER_DESC_SAMPLE_1 + FOLDER_DESC_SAMPLE_2
                + FILENAME_DESC_SAMPLE, new ExportCommand(Arrays.asList(VALID_FOLDER_NAME_1,
                VALID_FOLDER_NAME_2), VALID_FILENAME));
    }
}
