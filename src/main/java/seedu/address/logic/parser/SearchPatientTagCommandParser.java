package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchPatientTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PatientTagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchPatientCommand object
 */
public class SearchPatientTagCommandParser implements Parser<SearchPatientTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPatientCommand
     * and returns an FindPatientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchPatientTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchPatientTagCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchPatientTagCommand(new PatientTagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
