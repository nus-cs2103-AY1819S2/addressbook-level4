package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.travel.logic.commands.SearchYearCommand;
import seedu.travel.logic.parser.exceptions.ParseException;
import seedu.travel.model.place.DateVisited;
import seedu.travel.model.place.YearContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchRatingCommand object
 */
public class SearchYearCommandParser implements Parser<SearchYearCommand> {

    private static final int YEAR_RANGE_LENGTH = 9;
    private static final int YEAR_RANGE_DASH_POSITION = 4;
    private static final int YEAR_LOWER_BOUND_INDEX = 0;
    private static final int YEAR_UPPER_BOUND_INDEX = 1;

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

        if (isYearARange(yearKeywords[YEAR_LOWER_BOUND_INDEX])) {
            return parseYearRange(yearKeywords[YEAR_LOWER_BOUND_INDEX]);
        }

        for (String year : yearKeywords) {
            if (!DateVisited.isValidYear(year)) {
                throw new ParseException(String.format(DateVisited.MESSAGE_CONSTRAINTS_SEARCH,
                        SearchYearCommand.MESSAGE_USAGE));
            }
        }

        return new SearchYearCommand(new YearContainsKeywordsPredicate(Arrays.asList(yearKeywords)));
    }

    private SearchYearCommand parseYearRange(String args) {

        String[] yearKeywords = args.split("-");

        Integer lowerBoundYear = Integer.parseInt(yearKeywords[YEAR_LOWER_BOUND_INDEX]);
        Integer upperBoundYear = Integer.parseInt(yearKeywords[YEAR_UPPER_BOUND_INDEX]);

        ArrayList<String> yearKeywordsArray = new ArrayList<>();

        for (int index = lowerBoundYear; index <= upperBoundYear; index++) {
            yearKeywordsArray.add(String.valueOf(index));
        }

        return new SearchYearCommand(new YearContainsKeywordsPredicate(yearKeywordsArray));
    }

    private boolean isYearARange(String args) throws ParseException {

        if (args.length() != YEAR_RANGE_LENGTH) {
            return false;
        }

        if (args.charAt(YEAR_RANGE_DASH_POSITION) != '-') {
            return false;
        }

        String[] yearKeywords = args.split("-");

        if (!DateVisited.isValidYear(yearKeywords[YEAR_LOWER_BOUND_INDEX])
                || !DateVisited.isValidYear(yearKeywords[YEAR_UPPER_BOUND_INDEX])) {
            return false;
        }

        Integer lowerBoundYear = Integer.parseInt(yearKeywords[YEAR_LOWER_BOUND_INDEX]);
        Integer upperBoundYear = Integer.parseInt(yearKeywords[YEAR_UPPER_BOUND_INDEX]);

        return (lowerBoundYear <= upperBoundYear);
    }
}
