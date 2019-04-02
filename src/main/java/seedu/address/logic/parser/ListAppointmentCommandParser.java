package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.logic.commands.ListAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListAppointmentCommand object
 */
public class ListAppointmentCommandParser implements Parser<ListAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListAppointmentCommand
     * and returns an ListAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAppointmentCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATIENT_ID, PREFIX_DOCTOR_ID,
                        PREFIX_DATE_OF_APPT, PREFIX_START_TIME);

        if (argMultimap.getValue(PREFIX_PATIENT_ID).isPresent()) {
            argMultimap.getValue(PREFIX_PATIENT_ID).get();
        }
        if (argMultimap.getValue(PREFIX_DOCTOR_ID).isPresent()) {
            argMultimap.getValue(PREFIX_DOCTOR_ID).get();
        }
        if (argMultimap.getValue(PREFIX_DATE_OF_APPT).isPresent()) {
            argMultimap.getValue(PREFIX_DATE_OF_APPT).get();
        }
        if (argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
            argMultimap.getValue(PREFIX_START_TIME).get();
        }

        return new ListAppointmentCommand();
    }
}
