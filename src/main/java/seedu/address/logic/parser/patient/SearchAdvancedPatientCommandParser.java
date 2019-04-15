/* @@author wayneswq */
package seedu.address.logic.parser.patient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.patient.SearchAdvancedPatientCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.patient.AdvancedPatientSearchPredicate;

/**
 * Parses input arguments and creates a new SearchAdvancedPatientCommand object
 */
public class SearchAdvancedPatientCommandParser implements Parser<SearchAdvancedPatientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchAdvancedPatientCommand
     * and returns an SearchAdvancedPatientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchAdvancedPatientCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchAdvancedPatientCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchAdvancedPatientCommand(
                new AdvancedPatientSearchPredicate(Arrays.asList(nameKeywords)));
    }

}
