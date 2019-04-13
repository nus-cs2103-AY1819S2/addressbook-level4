package seedu.address.logic.parser.appointment;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.appointment.FutureAppointment;
import seedu.address.model.appointment.exceptions.AppointmentNotInFutureException;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATIENT_ID, PREFIX_DOCTOR_ID,
                        PREFIX_DATE_OF_APPT, PREFIX_START_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_PATIENT_ID, PREFIX_DOCTOR_ID,
                PREFIX_DATE_OF_APPT, PREFIX_START_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        AppointmentPatientId patientId = ParserUtil
                .parseAppointmentPatientId(argMultimap.getValue(PREFIX_PATIENT_ID).get());
        AppointmentDoctorId doctorId = ParserUtil
                .parseAppointmentDoctorId(argMultimap.getValue(PREFIX_DOCTOR_ID).get());
        AppointmentDate date = ParserUtil
                .parseAppointmentDate(argMultimap.getValue(PREFIX_DATE_OF_APPT).get());
        AppointmentTime time = ParserUtil
                .parseAppointmentTime(argMultimap.getValue(PREFIX_START_TIME).get());

        FutureAppointment appointment;
        try {
            appointment = new FutureAppointment(patientId, doctorId, date, time);
        } catch (AppointmentNotInFutureException e) {
            throw new ParseException(FutureAppointment.MESSAGE_CONSTRAINT_FUTURE);
        }

        return new AddAppointmentCommand(appointment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
