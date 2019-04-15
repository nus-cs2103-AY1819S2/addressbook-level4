package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecordMcCommand;


public class RecordMcCommadParserTest {

    private RecordMcCommandParser parser = new RecordMcCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordMcCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordMcCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordMcCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexOnly_success() {
        assertParseSuccess(parser, "1", new RecordMcCommand(Index.fromOneBased(1), "2"));
    }

    @Test
    public void parse_invalidDaysToRest_throwsParseException() {
        assertParseFailure(parser, "1 noInteger",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordMcCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 1.1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordMcCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordMcCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 -1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordMcCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_twoFields_success() {
        assertParseSuccess(parser, "1 1", new RecordMcCommand(Index.fromOneBased(1), "1"));
    }

}
