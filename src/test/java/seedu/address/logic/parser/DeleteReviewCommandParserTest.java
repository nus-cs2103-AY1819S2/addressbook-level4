package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteReviewCommand;


public class DeleteReviewCommandParserTest {
    private DeleteReviewCommandParser parser = new DeleteReviewCommandParser();

    @Test
    public void parse_allArgumentsPresent_success() {
        Index expectedIndex = INDEX_FIRST_RESTAURANT;

        //correct command to add a review
        assertParseSuccess(parser, "1", new DeleteReviewCommand(expectedIndex));
    }

    @Test
    public void parse_noArguments_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReviewCommand.MESSAGE_USAGE);

        //correct command to add a review
        assertParseFailure(parser, " ", expectedMessage);
    }

}
