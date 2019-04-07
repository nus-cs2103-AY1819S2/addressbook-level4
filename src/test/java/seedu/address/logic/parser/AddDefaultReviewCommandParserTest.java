package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddDefaultReviewCommand;

public class AddDefaultReviewCommandParserTest {
    private AddDefaultReviewCommandParser parser = new AddDefaultReviewCommandParser();
    @Test
    public void parse_allArgumentsPresent_success() {
        Index expectedIndex = INDEX_FIRST_RESTAURANT;

        //correct command to add default review 1
        assertParseSuccess(parser, "1 1", new AddDefaultReviewCommand(expectedIndex,
                "1"));

        //correct command to add default review 2
        assertParseSuccess(parser, "1 2", new AddDefaultReviewCommand(expectedIndex,
                "2"));

        //correct command to add default review 3
        assertParseSuccess(parser, "1 3", new AddDefaultReviewCommand(expectedIndex,
                "3"));

        //correct command to add default review 4
        assertParseSuccess(parser, "1 4", new AddDefaultReviewCommand(expectedIndex,
                "4"));

        //correct command to add default review 5
        assertParseSuccess(parser, "1 5", new AddDefaultReviewCommand(expectedIndex,
                "5"));
    }

    @Test
    public void parse_argumentMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDefaultReviewCommand.MESSAGE_USAGE);

        //If only 1 argument is given
        assertParseFailure(parser, "1", expectedMessage);

        //If no arguments are given
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidArguments_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDefaultReviewCommand.MESSAGE_USAGE);

        //If the first argument is not an integer
        assertParseFailure(parser, "a 1", expectedMessage);

        //If the second argument is not an integer
        assertParseFailure(parser, "1 #", expectedMessage);
    }
}
