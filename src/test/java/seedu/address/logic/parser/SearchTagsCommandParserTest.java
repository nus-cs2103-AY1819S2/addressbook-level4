package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.SearchTagsCommand;
import seedu.address.model.place.TagContainsKeywordsPredicate;

public class SearchTagsCommandParserTest {

    private SearchTagsCommandParser parser = new SearchTagsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchTagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchTagsCommand expectedSearchTagsCommand =
                new SearchTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList("school", "temple")));
        assertParseSuccess(parser, "school temple", expectedSearchTagsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n school \n \t temple  \t", expectedSearchTagsCommand);
    }

}
