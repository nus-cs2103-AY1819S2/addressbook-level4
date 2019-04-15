package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.patient.SelectPatientCommand;
import seedu.address.logic.parser.patient.SelectPatientCommandParser;

/**
 * Test scope: similar to {@code DeletePatientCommandParserTest}.
 * @see DeletePatientCommandParserTest
 */
public class SelectPatientCommandParserTest {

    private SelectPatientCommandParser parser = new SelectPatientCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectPatientCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SelectPatientCommand.MESSAGE_USAGE));
    }
}
