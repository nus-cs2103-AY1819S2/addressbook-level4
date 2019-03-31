package seedu.address.logic.parser;

import java.util.Arrays;

import seedu.address.logic.commands.SearchMedHistCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicalhistory.MedHistContainsKeywordsPredicate;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new SearchMedHistCommand object
 */
public class SearchMedHistCommandParser implements Parser<SearchMedHistCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SearchMedHistCommand
     * and returns an SearchMedHistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchMedHistCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchMedHistCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchMedHistCommand(new MedHistContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
