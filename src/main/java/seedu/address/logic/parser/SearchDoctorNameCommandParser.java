package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchDoctorNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DoctorNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchDoctorNameCommand object
 */
public class SearchDoctorNameCommandParser implements Parser<SearchDoctorNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDoctorCommand
     * and returns an FindDoctorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchDoctorNameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchDoctorNameCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchDoctorNameCommand(new DoctorNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
