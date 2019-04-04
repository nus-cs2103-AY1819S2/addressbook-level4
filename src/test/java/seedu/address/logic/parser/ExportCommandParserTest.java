package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT_DETAILED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import seedu.address.commons.util.FileName;
import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_optionalFileNameFieldPresent_success() {
        FileName expectedFileName = new FileName("example");

        // file name present as argument
        assertParseSuccess(parser, "example", new ExportCommand(expectedFileName));

    }

    @Test
    public void parse_optionalFileNameFieldsMissing_success() {
        SimpleDateFormat currentDateAndTimeFormat = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
        String fileName = currentDateAndTimeFormat.format(new Date());
        FileName expectedFileName = new FileName(fileName);

        // no input file name
        assertParseSuccess(parser, "", new ExportCommand(expectedFileName));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid file name (hyphen only)
        assertParseFailure(parser, "-",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_DETAILED, FileName.MESSAGE_CONSTRAINTS,
                        ExportCommand.MESSAGE_USAGE));
        // invalid file name (underscore only)
        assertParseFailure(parser, "_",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_DETAILED, FileName.MESSAGE_CONSTRAINTS,
                        ExportCommand.MESSAGE_USAGE));
        // invalid file name (only not supported non-alphanumeric characters)
        assertParseFailure(parser, "*",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_DETAILED, FileName.MESSAGE_CONSTRAINTS,
                        ExportCommand.MESSAGE_USAGE));
        // invalid file name (contains not supported non-alphanumeric characters)
        assertParseFailure(parser, "example*",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_DETAILED, FileName.MESSAGE_CONSTRAINTS,
                        ExportCommand.MESSAGE_USAGE));
        // invalid file name (alphabets with a space only)
        assertParseFailure(parser, "example record",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_DETAILED, FileName.MESSAGE_CONSTRAINTS,
                        ExportCommand.MESSAGE_USAGE));
        // invalid file name (alphabets with a file format)
        assertParseFailure(parser, "exampleRecord.csv",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_DETAILED, FileName.MESSAGE_CONSTRAINTS,
                        ExportCommand.MESSAGE_USAGE));
        // invalid file name (file format)
        assertParseFailure(parser, ".csv",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_DETAILED, FileName.MESSAGE_CONSTRAINTS,
                        ExportCommand.MESSAGE_USAGE));
    }
}
