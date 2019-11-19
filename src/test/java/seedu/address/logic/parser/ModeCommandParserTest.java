package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ModeCommand;
import seedu.address.model.AppMode;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the  ModeCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the  ModeCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ModeCommandParserTest {

    private ModeCommandParser parser = new ModeCommandParser();

    @Test
    public void parse_memeber_returnsModeCommandMember() {
        assertParseSuccess(parser, "MEMBER", new ModeCommand(AppMode.Modes.MEMBER));
    }

    @Test
    public void parse_activity_returnsModeCommandActivity() {
        assertParseSuccess(parser, "ACTIVITY", new ModeCommand(AppMode.Modes.ACTIVITY));
    }

    @Test
    public void parse_null_returnsModeCommand() {
        assertParseSuccess(parser, "", new ModeCommand(null));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                 ModeCommand.MESSAGE_USAGE));
    }
}
