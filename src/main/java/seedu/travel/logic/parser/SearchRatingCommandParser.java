package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.travel.logic.commands.SearchRatingCommand;
import seedu.travel.logic.parser.exceptions.ParseException;
import seedu.travel.model.place.RatingContainsKeywordsPredicate;

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
