package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchDoctorSpecialisationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DoctorSpecialisationContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchDoctorSpecialisationCommand object
 */
public class SearchDoctorSpecialisationCommandParser implements Parser<SearchDoctorSpecialisationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchDoctorCommand
     * and returns an SearchDoctorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchDoctorSpecialisationCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchDoctorSpecialisationCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchDoctorSpecialisationCommand(
                new DoctorSpecialisationContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
