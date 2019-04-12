/* @@author itszp */
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.OpenCommand;

public class OpenCommandParserTest {

    @Test
    public void parse_emptyString_failure() {
        OpenCommandParser parser = new OpenCommandParser();
        String emptyString = "";
        assertParseFailure(parser, emptyString, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                OpenCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validString_success() {
        OpenCommandParser parser = new OpenCommandParser();
        String sampleString = "sample.png";
        assertParseSuccess(parser, sampleString, new OpenCommand("sample.png"));
    }
}
