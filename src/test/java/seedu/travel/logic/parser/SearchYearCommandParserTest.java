package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.travel.logic.commands.SearchYearCommand;
import seedu.travel.model.place.DateVisited;
import seedu.travel.model.place.YearContainsKeywordsPredicate;

public class SearchYearCommandParserTest {

    private SearchYearCommandParser parser = new SearchYearCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchYearCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "1819", String.format(DateVisited.MESSAGE_CONSTRAINTS_SEARCH,
                SearchYearCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "2017-2016", String.format(DateVisited.MESSAGE_CONSTRAINTS_SEARCH,
                SearchYearCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1819-2000", String.format(DateVisited.MESSAGE_CONSTRAINTS_SEARCH,
                SearchYearCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2000-9999", String.format(DateVisited.MESSAGE_CONSTRAINTS_SEARCH,
                SearchYearCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchYearCommand() {
        // no leading and trailing whitespaces
        SearchYearCommand expectedSearchYearCommand =
                new SearchYearCommand(new YearContainsKeywordsPredicate(Arrays.asList("2018", "2019")));
        assertParseSuccess(parser, "2018 2019", expectedSearchYearCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 2018 \n \t 2019  \t", expectedSearchYearCommand);
    }

    @Test
    public void parse_validArgsRange_returnsSearchYearCommand() {
        // no leading and trailing whitespaces
        SearchYearCommand expectedSearchYearCommand =
                new SearchYearCommand(new YearContainsKeywordsPredicate(Arrays.asList("2016", "2017", "2018")));
        assertParseSuccess(parser, "2016-2018", expectedSearchYearCommand);

        // leading and trailing whitespaces
        assertParseSuccess(parser, " \n 2016-2018  \t", expectedSearchYearCommand);
    }
}
