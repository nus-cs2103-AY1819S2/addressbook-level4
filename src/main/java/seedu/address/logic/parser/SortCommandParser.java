package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Comparator;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.comparators.PatientComparator;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Patient;



/**
 * Parses input and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        Comparator<Patient> userComparator;
        try {
            userComparator = PatientComparator.getPatientComparator(userInput);
            return new SortCommand(userComparator, userInput.trim());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }
    }
}
