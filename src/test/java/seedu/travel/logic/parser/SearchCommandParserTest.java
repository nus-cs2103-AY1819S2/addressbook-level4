package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.travel.logic.commands.SearchCommand;
import seedu.travel.model.place.NameContainsKeywordsPredicate;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("AMK", "Bedok")));
        assertParseSuccess(parser, "AMK Bedok", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n AMK \n \t Bedok  \t", expectedSearchCommand);
    }

}
