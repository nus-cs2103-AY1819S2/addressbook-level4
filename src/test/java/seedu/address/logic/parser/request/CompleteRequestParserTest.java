package seedu.address.logic.parser.request;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.request.CompleteRequestCommand;

class CompleteRequestParserTest {
    private CompleteRequestParser parser = new CompleteRequestParser();

    @org.junit.jupiter.api.Test
    public void parse_validArgs_returnsDoneCommand() {
        assertParseSuccess(parser, "1", new CompleteRequestCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteRequestCommand.MESSAGE_USAGE));
    }

}
