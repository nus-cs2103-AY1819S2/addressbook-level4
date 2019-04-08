/* @@author wayneswq */
package seedu.address.logic.parser;

import static com.google.common.base.Ascii.toUpperCase;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchPatientWithApptStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.person.PatientApptStatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchPatientWithApptStatusCommand object
 */
public class SearchPatientWithApptStatusCommandParser implements Parser<SearchPatientWithApptStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPatientCommand
     * and returns an FindPatientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchPatientWithApptStatusCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchPatientWithApptStatusCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (nameKeywords.length > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            SearchPatientWithApptStatusCommand.MESSAGE_ONLY_ONE_STATUS));
        }

        if(!isValidApptStatus(nameKeywords[0])) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            SearchPatientWithApptStatusCommand.MESSAGE_ONLY_ONE_STATUS));
        }

        return new SearchPatientWithApptStatusCommand(
                new PatientApptStatusContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Checks if the input keyword is one of the AppointmentStatus enum
     */
    public boolean isValidApptStatus(String keyword) {
        for (AppointmentStatus status : AppointmentStatus.values()) {
            if (status.toString().equalsIgnoreCase(keyword)) {
                return true;
            }
        }
        return false;
    }

}
