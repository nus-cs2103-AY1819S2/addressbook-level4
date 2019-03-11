package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.SearchRatingCommand;
import seedu.address.model.place.RatingContainsKeywordsPredicate;

public class SearchRatingCommandParserTest {

    private SearchRatingCommandParser parser = new SearchRatingCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchRatingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchRatingCommand() {
        // no leading and trailing whitespaces
        SearchRatingCommand expectedSearchRatingCommand =
                new SearchRatingCommand(new RatingContainsKeywordsPredicate(Arrays.asList("4", "5")));
        assertParseSuccess(parser, "4 5", expectedSearchRatingCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 4 \n \t 5  \t", expectedSearchRatingCommand);
    }

}
