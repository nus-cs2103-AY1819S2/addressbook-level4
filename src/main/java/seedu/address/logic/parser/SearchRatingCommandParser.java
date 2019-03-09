package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchRatingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.place.RatingContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchRatingCommand object
 */
public class SearchRatingCommandParser implements Parser<SearchRatingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchRatingCommand
     * and returns an SearchRatingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchRatingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchRatingCommand.MESSAGE_USAGE));
        }

        String[] ratingKeywords = trimmedArgs.split("\\s+");

        return new SearchRatingCommand(new RatingContainsKeywordsPredicate(Arrays.asList(ratingKeywords)));
    }

}
