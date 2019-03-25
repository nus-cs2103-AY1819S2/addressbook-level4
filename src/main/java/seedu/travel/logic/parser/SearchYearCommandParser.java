package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.travel.logic.commands.SearchYearCommand;
import seedu.travel.logic.parser.exceptions.ParseException;
import seedu.travel.model.place.DateVisited;
import seedu.travel.model.place.YearContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchRatingCommand object
 */
public class SearchYearCommandParser implements Parser<SearchYearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchRatingCommand
     * and returns an SearchRatingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchYearCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchYearCommand.MESSAGE_USAGE));
        }

        String[] yearKeywords = trimmedArgs.split("\\s+");

        for (String year : yearKeywords) {
            if (!DateVisited.isValidYear(year)) {
                throw new ParseException(String.format(DateVisited.MESSAGE_CONSTRAINTS_SEARCH,
                        SearchYearCommand.MESSAGE_USAGE));
            }
        }

        return new SearchYearCommand(new YearContainsKeywordsPredicate(Arrays.asList(yearKeywords)));
    }
}
