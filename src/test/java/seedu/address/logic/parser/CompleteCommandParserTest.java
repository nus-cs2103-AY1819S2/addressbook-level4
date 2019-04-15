package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.Test;

import seedu.address.logic.commands.CompleteCommand;
import seedu.address.model.util.PocketProjectDate;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CompleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the CompleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CompleteCommandParserTest {

    private CompleteCommandParser parser = new CompleteCommandParser();

    @Test
    public void parse_validArgs_returnsCompleteCommand() {
        assertParseSuccess(parser, "1 11/11/2011",
                new CompleteCommand(INDEX_FIRST_PROJECT, new PocketProjectDate("11/11/2011")));
    }
}
