package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddReviewCommand;
import seedu.address.model.review.Review;
import seedu.address.testutil.ReviewBuilder;


public class AddReviewCommandParserTest {

    private AddReviewCommandParser parser = new AddReviewCommandParser();

    @Test
    public void parse_allArgumentsPresent_success() {
        Index expectedIndex = INDEX_FIRST_RESTAURANT;
        Review reviewToAdd = new ReviewBuilder().build();

        //correct command to add a review
        assertParseSuccess(parser, "1 re/Standard restaurant rr/3", new AddReviewCommand(expectedIndex,
                reviewToAdd));
    }

    @Test
    public void parse_argumentMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReviewCommand.MESSAGE_USAGE);

        //If only only re given
        assertParseFailure(parser, "1 re/Standard restaurant", expectedMessage);

        //If only rr given
        assertParseFailure(parser, "1 rr/3", expectedMessage);

        //If no index given
        assertParseFailure(parser, "re/Standard restaurant rr/3", expectedMessage);

        //If no arguments are given
        assertParseFailure(parser, "", expectedMessage);

        //If only index given
        assertParseFailure(parser, "1", expectedMessage);

        //If only re given
        assertParseFailure(parser, "re/Standard restaurant", expectedMessage);

        //If only rr given
        assertParseFailure(parser, "rr/3", expectedMessage);
    }

    @Test
    public void parse_invalidArguments_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReviewCommand.MESSAGE_USAGE);

        //If the first argument is not an integer
        assertParseFailure(parser, "a re/Standard restaurant rr/3", expectedMessage);
    }
}
