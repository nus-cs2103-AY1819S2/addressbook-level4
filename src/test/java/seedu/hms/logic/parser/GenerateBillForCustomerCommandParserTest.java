package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.hms.logic.commands.GenerateBillForCustomerCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the GenerateBillForCustomerCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCustomerCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class GenerateBillForCustomerCommandParserTest {

    private GenerateBillForCustomerCommandParser parser = new GenerateBillForCustomerCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            GenerateBillForCustomerCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1", MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }
}
