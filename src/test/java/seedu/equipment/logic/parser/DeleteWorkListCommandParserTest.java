package seedu.equipment.logic.parser;

import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_FIRST_WORKLIST;

import org.junit.Test;

import seedu.equipment.logic.commands.DeleteWorkListCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteWorkListCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteWorkListCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteWorkListCommandParserTest {

    private DeleteWorkListCommandParser parser = new DeleteWorkListCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteWorkListCommand(INDEX_FIRST_WORKLIST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteWorkListCommand.MESSAGE_USAGE));
    }
}
