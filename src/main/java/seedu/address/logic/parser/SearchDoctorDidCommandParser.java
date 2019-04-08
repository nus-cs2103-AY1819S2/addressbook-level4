/* @@author wayneswq */
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SearchDoctorDidCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.RecordContainsDoctorIdPredicate;

/**
 * Parses input arguments and creates a new SearchDoctorDidCommand object
 */
public class SearchDoctorDidCommandParser implements Parser<SearchDoctorDidCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchDoctorDidCommand
     * and returns an SearchDoctorDidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchDoctorDidCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchDoctorDidCommand.MESSAGE_USAGE));
        }

        return new SearchDoctorDidCommand(new RecordContainsDoctorIdPredicate(Integer.parseInt(trimmedArgs)));
    }

}
