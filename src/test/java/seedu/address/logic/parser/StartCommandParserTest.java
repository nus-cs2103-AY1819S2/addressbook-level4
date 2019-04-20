package seedu.address.logic.parser;

import static java.lang.String.format;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.management.StartCommand;


public class StartCommandParserTest {
    private StartCommandParser parser = new StartCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", format(MESSAGE_INVALID_COMMAND_FORMAT,
                StartCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "start 1i c/15 m/LEARN", format(MESSAGE_INVALID_COMMAND_FORMAT,
                StartCommand.MESSAGE_USAGE));
    }
    //TODO:
    /*@Test
    public void parseAnswer() throws Exception {
    }*/
}
