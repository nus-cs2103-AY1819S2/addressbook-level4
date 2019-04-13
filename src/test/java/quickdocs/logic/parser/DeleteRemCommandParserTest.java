package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static quickdocs.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;

import org.junit.Test;

import quickdocs.logic.commands.DeleteRemCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteRemCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteRemCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore is covered by the ParserUtilTest.
 */
public class DeleteRemCommandParserTest {

    private DeleteRemCommandParser parser = new DeleteRemCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRemCommand() {
        assertParseSuccess(parser, "1", new DeleteRemCommand(INDEX_FIRST_REMINDER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRemCommand.MESSAGE_USAGE));
    }
}
