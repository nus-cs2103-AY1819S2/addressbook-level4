/* @@author wayneswq */
package seedu.address.logic.parser.patient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.patient.SearchPatientApptStatusCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.person.patient.PatientApptStatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchPatientApptStatusCommand object
 */
public class SearchPatientApptStatusCommandParser implements Parser<SearchPatientApptStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchPatientApptStatusCommand
     * and returns an SearchPatientApptStatusCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchPatientApptStatusCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchPatientApptStatusCommand.MESSAGE_USAGE));
        }

        String[] status = trimmedArgs.split("\\s+");

        // more than one status or not one of the four status
        if (status.length > 1 || !isValidApptStatus(status[0])) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            SearchPatientApptStatusCommand.MESSAGE_INVALID_STATUS));
        }

        return new SearchPatientApptStatusCommand(
                new PatientApptStatusContainsKeywordsPredicate(Arrays.asList(status)));
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
