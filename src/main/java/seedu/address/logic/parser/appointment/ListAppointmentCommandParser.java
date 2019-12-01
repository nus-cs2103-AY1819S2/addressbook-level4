package seedu.address.logic.parser.appointment;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHRONOLOGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.Optional;

import seedu.address.logic.commands.appointment.ListAppointmentCommand;
import seedu.address.logic.commands.appointment.ListAppointmentCommand.ListAppointmentDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentChronology;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentTime;

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
                        PREFIX_DATE_OF_APPT, PREFIX_START_TIME, PREFIX_APPT_STATUS, PREFIX_CHRONOLOGY);

        ListAppointmentDescriptor descriptors =
                new ListAppointmentDescriptor();

        if (argMultimap.getValue(PREFIX_PATIENT_ID).isPresent()) {
            AppointmentPatientId pid = ParserUtil
                    .parseAppointmentPatientId(argMultimap.getValue(PREFIX_PATIENT_ID).get());
            descriptors.setPatientId(Optional.of(pid));
        }
        if (argMultimap.getValue(PREFIX_DOCTOR_ID).isPresent()) {
            AppointmentDoctorId did = ParserUtil
                    .parseAppointmentDoctorId(argMultimap.getValue(PREFIX_DOCTOR_ID).get());
            descriptors.setDoctorId(Optional.of(did));
        }
        if (argMultimap.getValue(PREFIX_DATE_OF_APPT).isPresent()) {
            AppointmentDate date = ParserUtil
                    .parseAppointmentDate(argMultimap.getValue(PREFIX_DATE_OF_APPT).get());
            descriptors.setDate(Optional.of(date));
        }
        if (argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
            AppointmentTime time = ParserUtil
                    .parseAppointmentTime(argMultimap.getValue(PREFIX_START_TIME).get());
            descriptors.setTime(Optional.of(time));
        }
        if (argMultimap.getValue(PREFIX_APPT_STATUS).isPresent()) {
            AppointmentStatus status = ParserUtil
                    .parseAppointmentStatus(argMultimap.getValue(PREFIX_APPT_STATUS).get());
            descriptors.setStatus(Optional.of(status));
        }
        if (argMultimap.getValue(PREFIX_CHRONOLOGY).isPresent()) {
            AppointmentChronology chronology = ParserUtil
                    .parseAppointmentChronology(argMultimap.getValue(PREFIX_CHRONOLOGY).get());
            descriptors.setChronology(Optional.of(chronology));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListAppointmentCommand.MESSAGE_USAGE));
        }

        return new ListAppointmentCommand(descriptors);
    }
}
