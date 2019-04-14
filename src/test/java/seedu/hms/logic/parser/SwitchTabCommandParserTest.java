package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.hms.logic.commands.SwitchTabCommand;

public class SwitchTabCommandParserTest {

    private SwitchTabCommandParser parser = new SwitchTabCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SwitchTabCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SwitchTabCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SwitchTabCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 3 2", SwitchTabCommand.MESSAGE_INVALID_PANEL_NUMBER);
        assertParseFailure(parser, " 2 3", SwitchTabCommand.MESSAGE_INVALID_TAB_NUMBER);
        assertParseFailure(parser, " 1 4", SwitchTabCommand.MESSAGE_INVALID_TAB_NUMBER);
        assertParseFailure(parser, " 1 1111111111111111111111111111",
            "Invalid Panel/Tab number");
    }


    @Test
    public void parse_validArgs_newSwitchTabCommand() {
        assertParseSuccess(parser, "1 1", new SwitchTabCommand(1, 0));
    }
}

