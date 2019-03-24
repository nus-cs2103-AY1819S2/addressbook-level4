package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.travel.logic.commands.SearchCountryCommand;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.CountryCodeContainsKeywordsPredicate;

public class SearchCountryCommandParserTest {

    private SearchCountryCommandParser parser = new SearchCountryCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchCountryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "SGXX JP", String.format(CountryCode.MESSAGE_CONSTRAINTS,
                SearchCountryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCountryCommand() {
        // no leading and trailing whitespaces
        SearchCountryCommand expectedSearchCountryCommand =
                new SearchCountryCommand(new CountryCodeContainsKeywordsPredicate(Arrays.asList("SGP", "JPN")));
        assertParseSuccess(parser, "SGP JPN", expectedSearchCountryCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n SGP \n \t JPN  \t", expectedSearchCountryCommand);
    }

}
