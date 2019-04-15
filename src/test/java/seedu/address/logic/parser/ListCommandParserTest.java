package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEmployeeCommand;
import seedu.address.logic.commands.ListProjectCommand;


public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListEmployeeCommand() {
        // no leading and trailing whitespaces
        ListEmployeeCommand expectedListCommand = new ListEmployeeCommand();
        assertParseSuccess(parser, "employee", expectedListCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "    employee   ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_returnsListProjectCommand() {
        // no leading and trailing whitespaces
        ListProjectCommand expectedListCommand = new ListProjectCommand();
        assertParseSuccess(parser, "project", expectedListCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "    project   ", expectedListCommand);
    }
}
