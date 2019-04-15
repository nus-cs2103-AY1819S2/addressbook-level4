package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteFromOrderCommand;
import seedu.address.model.menu.Code;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteFromOrderCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteFromOrderCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteFromOrderCommandParserTest {

    private DeleteFromOrderCommandParser parser = new DeleteFromOrderCommandParser();

    @Test
    public void parse_validItemCode_returnsDeleteFromOrderCommand() {
        assertParseSuccess(parser, "W09", new DeleteFromOrderCommand(new Code("W09")));
    }

    @Test
    public void parse_invalidItemCode_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteFromOrderCommand.MESSAGE_USAGE));
    }
}
