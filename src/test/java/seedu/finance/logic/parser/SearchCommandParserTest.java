package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.finance.logic.commands.SearchCommand;
import seedu.finance.model.record.CategoryContainsKeywordsPredicate;
import seedu.finance.model.record.DateContainsKeywordsPredicate;
import seedu.finance.model.record.NameContainsKeywordsPredicate;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedFindCommand =
                new SearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("Apple", "Banana")));
        assertParseSuccess(parser, "-name Apple Banana", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "-name \n Apple \n \t Banana  \t", expectedFindCommand);
    }

    @Test
    public void parse_validCategoryArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedFindCommand =
                new SearchCommand(new CategoryContainsKeywordsPredicate(Arrays.asList("Food", "Drinks")));
        assertParseSuccess(parser, "-cat Food Drinks", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "-cat \n Food \n \t Drinks  \t", expectedFindCommand);
    }

    @Test
    public void parse_validDateArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedFindCommand =
                new SearchCommand(new DateContainsKeywordsPredicate(Arrays.asList("10/3/2018", "2/12/2017")));
        assertParseSuccess(parser, "-date 10/3/2018 2/12/2017", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "-date \n 10/3/2018 \n \t 2/12/2017  \t", expectedFindCommand);
    }

}
