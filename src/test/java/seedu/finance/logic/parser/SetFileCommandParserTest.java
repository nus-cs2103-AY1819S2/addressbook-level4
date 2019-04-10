package seedu.finance.logic.parser;

import org.junit.Test;
import seedu.finance.logic.commands.SetFileCommand;

import java.nio.file.Paths;

import static seedu.finance.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;

import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class SetFileCommandParserTest {
    private SetFileCommandParser parser = new SetFileCommandParser();

    @Test
    public void parse_validField_success() {
        // Single character filename
        assertParseSuccess(parser, " " + PREFIX_FILE + "a",
                new SetFileCommand(Paths.get("data\\a.json")));

        // Filename with period
        assertParseSuccess(parser, " " + PREFIX_FILE + "file.",
                new SetFileCommand(Paths.get("data\\file..json")));
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(SetFileCommand.MESSAGE_CONSTRAINTS);

        // no filename
        assertParseFailure(parser, " " + PREFIX_FILE + "", expectedMessage);

        // Filename with special symbols
        assertParseFailure(parser, " " + PREFIX_FILE + "!file", expectedMessage);
        assertParseFailure(parser, " " + PREFIX_FILE + "\\file", expectedMessage);
        assertParseFailure(parser, " " + PREFIX_FILE + "f&ile", expectedMessage);

        // Filename is same as current file
    }
}
