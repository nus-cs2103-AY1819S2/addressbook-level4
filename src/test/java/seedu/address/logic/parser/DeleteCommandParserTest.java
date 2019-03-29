package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODE;
import static seedu.address.logic.commands.CommandTestUtil.MODE_HEALTHWORKER;
import static seedu.address.logic.parser.CommandMode.MODE_REQUEST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.DeleteCommandParser.INVALID_COMMAND_USAGE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.DeleteHealthWorkerCommand;
import seedu.address.logic.commands.request.DeleteRequestCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeletePersonCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeletePersonCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_invalidMode() {
        assertParseFailure(parser, INVALID_MODE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                INVALID_COMMAND_USAGE));
    }

    @Test
    public void parse_healthWorker_validInput() {
        assertParseSuccess(parser, MODE_HEALTHWORKER + " 1", new DeleteHealthWorkerCommand(INDEX_FIRST));

        assertParseSuccess(parser, "healthworker" + " 1", new DeleteHealthWorkerCommand(INDEX_FIRST));
    }

    @Test
    public void parse_healthWorker_invalidInput() {
        // non numeric
        assertParseFailure(parser, MODE_HEALTHWORKER + " a", MESSAGE_INVALID_INDEX);

        // negative index
        assertParseFailure(parser, MODE_HEALTHWORKER + " -1", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_healthWorker_emptyFields() {
        assertParseFailure(parser, MODE_HEALTHWORKER, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteHealthWorkerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_request_validInput() {
        assertParseSuccess(parser, MODE_REQUEST + " 1", new DeleteRequestCommand(INDEX_FIRST));

        assertParseSuccess(parser, "request" + " 1", new DeleteRequestCommand(INDEX_FIRST));
    }

    @Test
    public void parse_request_invalidInput() {
        // non numeric
        assertParseFailure(parser, MODE_REQUEST + " a", MESSAGE_INVALID_INDEX);

        // negative index
        assertParseFailure(parser, MODE_REQUEST + " -1", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_request_emptyFields() {
        assertParseFailure(parser, MODE_REQUEST, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRequestCommand.MESSAGE_USAGE));
    }
}
