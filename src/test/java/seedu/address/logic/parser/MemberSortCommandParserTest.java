package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.MemberSortCommand;

public class MemberSortCommandParserTest {
    private MemberSortCommandParser parser = new MemberSortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MemberSortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        MemberSortCommand expectedSortCommand =
                new MemberSortCommand("yearOfStudy");
        assertParseSuccess(parser, "yearOfStudy", expectedSortCommand);

        expectedSortCommand =
                new MemberSortCommand("major");
        assertParseSuccess(parser, "major", expectedSortCommand);

        expectedSortCommand =
                new MemberSortCommand("name");
        assertParseSuccess(parser, "name", expectedSortCommand);

        expectedSortCommand = new MemberSortCommand("gender");
        assertParseSuccess(parser, "gender", expectedSortCommand);
    }
}
