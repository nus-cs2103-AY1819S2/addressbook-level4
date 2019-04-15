package seedu.finance.logic.parser;
//@@author Jackimaru96

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.finance.logic.commands.ThemeCommand;

public class ThemeCommandParserTest {
    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_invalidFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, " ", expectedMessage);


    }

    @Test
    public void parse_validFields_success() {
        String theme = "Blue";
        // Correct parameter Blue
        assertParseSuccess(parser, theme, new ThemeCommand(theme));


    }
}
