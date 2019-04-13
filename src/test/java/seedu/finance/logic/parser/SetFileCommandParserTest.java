package seedu.finance.logic.parser;

import static seedu.finance.logic.commands.SetFileCommand.MAX_FILE_LENGTH;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Paths;

import org.junit.Test;

import seedu.finance.logic.commands.SetFileCommand;

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

        // File name is longer than maximum length
        StringBuilder longFileName = new StringBuilder();
        for (int i = 0; i < MAX_FILE_LENGTH + 1; i++) {
            longFileName.append("a");
        }
        assertParseFailure(parser, " " + PREFIX_FILE + longFileName.toString(), expectedMessage);
    }
}
