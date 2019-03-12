package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.place.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchRatingCommand object
 */
public class SearchTagsCommandParser implements Parser<SearchTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchRatingCommand
     * and returns an SearchRatingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchTagsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchTagsCommand.MESSAGE_USAGE));
        }

        String[] tagsKeywords = trimmedArgs.split("\\s+");

        return new SearchTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagsKeywords)));
    }

}
