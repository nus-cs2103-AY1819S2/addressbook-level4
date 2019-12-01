package seedu.address.logic.parser.prescription;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.prescription.SearchPrescriptionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.prescription.PrescriptionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchMedHistCommand object
 */
public class SearchPrescriptionCommandParser implements Parser<SearchPrescriptionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SearchPrescriptionCommand
     * and returns an SearchPrescriptionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchPrescriptionCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchPrescriptionCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+"); // split the trimmed string on one or more white spaces

        return new SearchPrescriptionCommand(new PrescriptionContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
