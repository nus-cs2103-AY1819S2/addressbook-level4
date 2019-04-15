package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REVIEW;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteReviewCommand;


public class DeleteReviewCommandParserTest {

    private DeleteReviewCommandParser parser = new DeleteReviewCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteReviewCommand() {
        assertParseSuccess(parser, "1", new DeleteReviewCommand(INDEX_FIRST_REVIEW));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReviewCommand.MESSAGE_USAGE));
    }
}
