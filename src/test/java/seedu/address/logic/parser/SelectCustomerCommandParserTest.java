package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;

import org.junit.Test;

import seedu.address.logic.commands.SelectCustomerCommand;

/**
 * Test scope: similar to {@code DeleteCustomerCommandParserTest}.
 *
 * @see DeleteCustomerCommandParserTest
 */
public class SelectCustomerCommandParserTest {

    private SelectCustomerCommandParser parser = new SelectCustomerCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCustomerCommand(INDEX_FIRST_CUSTOMER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectCustomerCommand.MESSAGE_USAGE));
    }
}
