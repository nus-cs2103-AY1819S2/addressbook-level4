package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_ROOM_TYPE;

import org.junit.Test;

import seedu.hms.logic.commands.DeleteRoomTypeCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteRoomTypeCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteRoomTypeCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteRoomTypeCommandParserTest {

    private DeleteRoomTypeCommandParser parser = new DeleteRoomTypeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteRoomTypeCommand(INDEX_FIRST_ROOM_TYPE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteRoomTypeCommand.MESSAGE_USAGE));
    }
}
