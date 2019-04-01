package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.LabelCommand.DEFAULT_FILENAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileName;
import seedu.address.logic.commands.LabelCommand;

public class LabelCommandParserTest {
    private LabelCommandParser parser = new LabelCommandParser();

    @Test
    public void parse_optionalFileNameFieldDefault_success() {
        FileName expectedFileName = new FileName(DEFAULT_FILENAME);
        String userInput = ("1");
        Index index = new Index(0);
        assertParseSuccess(parser, userInput, new LabelCommand(index, expectedFileName));

    }

    @Test
    public void parse_optionalFileNameFieldPresent_success() {
        FileName expectedFileName = new FileName("newFile");
        String userInput = ("1 f/newFile");
        Index index = new Index(0);

        assertParseSuccess(parser, userInput, new LabelCommand(index, expectedFileName));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LabelCommand.MESSAGE_USAGE);

        // missing all inputs
        assertParseFailure(parser, "", expectedMessage);

        // missing file prefix with index
        assertParseFailure(parser, "1 noFilePrefix", expectedMessage);

        // missing file prefix without index
        assertParseFailure(parser, "noFilePrefix", expectedMessage);

        // missing index
        assertParseFailure(parser, "f/newFile", expectedMessage);

        // missing index without fileName
        assertParseFailure(parser, "f/", expectedMessage);


    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LabelCommand.MESSAGE_USAGE);

        // Invalid User Input
        assertParseFailure(parser, "-", expectedMessage);

        // Invalid File name (no spacing allowed)
        assertParseFailure(parser, "1 f/new File", expectedMessage);
    }

}
