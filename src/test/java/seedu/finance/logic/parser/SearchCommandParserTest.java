package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_NAME;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.finance.logic.commands.SearchCommand;
import seedu.finance.model.record.CategoryContainsKeywordsPredicate;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.DateContainsKeywordsPredicate;
import seedu.finance.model.record.NameContainsKeywordsPredicate;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noFlags_throwsParseException() {
        assertParseFailure(parser, "hello there", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.NO_FLAG));
    }

    @Test
    public void parse_moreThanOneFlag_throwsParseException() {
        //both are invalid flags
        assertParseFailure(parser, "-invalidFlag -anotherFlag food apple",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.ONE_FLAG_ONLY));

        //valid flag and invalid flag
        assertParseFailure(parser, "-cat -anotherFlag food apple",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.ONE_FLAG_ONLY));

        //both are valid flags
        assertParseFailure(parser, "-cat -name food apple",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.ONE_FLAG_ONLY));
    }

    @Test
    public void parse_validNameArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedFindCommand =
                new SearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("Apple", "Banana")));
        assertParseSuccess(parser, COMMAND_FLAG_NAME + " Apple Banana", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, COMMAND_FLAG_NAME + " \n Apple \n \t Banana  \t", expectedFindCommand);
    }

    @Test
    public void parse_validCategoryArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedFindCommand =
                new SearchCommand(new CategoryContainsKeywordsPredicate(Arrays.asList("Food", "Drinks")));
        assertParseSuccess(parser, COMMAND_FLAG_CATEGORY + " Food Drinks", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, COMMAND_FLAG_CATEGORY + " \n Food \n \t Drinks  \t", expectedFindCommand);
    }

    @Test
    public void parse_validDateArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedFindCommand =
                new SearchCommand(new DateContainsKeywordsPredicate(Arrays.asList("10/3/2018", "2/12/2017")));
        assertParseSuccess(parser, COMMAND_FLAG_DATE + " 10/3/2018 2/12/2017", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, COMMAND_FLAG_DATE + " \n 10/3/2018 \n \t 2/12/2017  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidDateArgs_failure() {
        // invalid date as argument
        assertParseFailure(parser, COMMAND_FLAG_DATE + " abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Date.MESSAGE_CONSTRAINTS));

        // valid date + invalid date as argument
        assertParseFailure(parser, COMMAND_FLAG_DATE + " abc 4/4/2019", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Date.MESSAGE_CONSTRAINTS));


    }


}
