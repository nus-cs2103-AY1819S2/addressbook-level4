package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.travel.logic.commands.SearchCountryCommand;
import seedu.travel.logic.parser.exceptions.ParseException;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.CountryCodeContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCountryCommand object
 */
public class SearchCountryCommandParser implements Parser<SearchCountryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchRatingCommand
     * and returns an SearchRatingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCountryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCountryCommand.MESSAGE_USAGE));
        }

        String[] countryKeywords = trimmedArgs.split("\\s+");

        for (String country : countryKeywords) {
            if (!CountryCode.isValidCountryCode(country)) {
                throw new ParseException(
                        String.format(CountryCode.MESSAGE_CONSTRAINTS, SearchCountryCommand.MESSAGE_USAGE));
            }
        }

        return new SearchCountryCommand(new CountryCodeContainsKeywordsPredicate(Arrays.asList(countryKeywords)));
    }
}
