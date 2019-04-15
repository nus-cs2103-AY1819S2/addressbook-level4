package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewEmployeeCommand;
import seedu.address.logic.commands.ViewProjectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, " employee 1", new ViewEmployeeCommand(INDEX_FIRST_EMPLOYEE));
        assertParseSuccess(parser, " project 1", new ViewProjectCommand(INDEX_FIRST_PROJECT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "employee", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewEmployeeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "project", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewProjectCommand.MESSAGE_USAGE));
    }
}
