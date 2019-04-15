package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListReviewCommand;


public class ListReviewCommandParserTest {

    private ListReviewCommandParser parser = new ListReviewCommandParser();

    @Test
    public void parse_validArgs_returnsListReviewCommand() {
        assertParseSuccess(parser, "1", new ListReviewCommand(INDEX_FIRST_BOOK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListReviewCommand.MESSAGE_USAGE));
    }
}
