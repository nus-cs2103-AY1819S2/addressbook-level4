package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SwapCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see SortCommandParserTest
 */
public class SwapCommandParserTest {

    private SwapCommandParser parser = new SwapCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        String argsWithFirstAndSecondIndex = "1 2";
        assertParseSuccess(parser, argsWithFirstAndSecondIndex,
                new SwapCommand(Index.fromOneBased(1), Index.fromOneBased(2)));
        // should accept when there white spaces is more than one
        argsWithFirstAndSecondIndex = "  1 2";
        assertParseSuccess(parser, argsWithFirstAndSecondIndex,
                new SwapCommand(Index.fromOneBased(1), Index.fromOneBased(2)));

        argsWithFirstAndSecondIndex = "1 2  ";
        assertParseSuccess(parser, argsWithFirstAndSecondIndex,
                new SwapCommand(Index.fromOneBased(1), Index.fromOneBased(2)));

        argsWithFirstAndSecondIndex = "  1 2  ";
        assertParseSuccess(parser, argsWithFirstAndSecondIndex,
                new SwapCommand(Index.fromOneBased(1), Index.fromOneBased(2)));
    }
}
