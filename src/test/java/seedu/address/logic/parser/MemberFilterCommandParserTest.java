package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.MemberFilterCommand;



public class MemberFilterCommandParserTest {
    private MemberFilterCommandParser parser = new MemberFilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MemberFilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        String[] input = new String[2];
        input[0] = "yearofstudy";
        input[1] = "2";
        MemberFilterCommand expectedFilterCommand =
                new MemberFilterCommand(input);
        assertParseSuccess(parser, "yearOfStudy 2", expectedFilterCommand);
        assertParseFailure(parser, "year 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MemberFilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "field economics", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MemberFilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "sex male", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MemberFilterCommand.MESSAGE_USAGE));
    }
}
