package seedu.address.logic.parser.doctor;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.doctor.DoctorMatchCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.doctor.DoctorMatch;
import seedu.address.model.person.doctor.DoctorSpecialisationMatchesPredicate;
import seedu.address.model.person.specialisation.Specialisation;

/**
 * Parses the given {@code String} of arguments in the context of the DoctorMatchCommand
 * and returns a DoctorMatchCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class DoctorMatchCommandParser implements Parser<DoctorMatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoctorMatchCommand
     * and returns a DoctorMatchCommand object for execution.
     * @param args
     * @return
     * @throws ParseException
     */
    public DoctorMatchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SPECIALISATION, PREFIX_DATE_OF_APPT, PREFIX_START_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_SPECIALISATION, PREFIX_DATE_OF_APPT, PREFIX_START_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoctorMatchCommand.MESSAGE_USAGE));
        }

        Specialisation specialisation = ParserUtil.parseSpecialisation(argMultimap
                .getValue(PREFIX_SPECIALISATION).get());
        AppointmentDate date = ParserUtil.parseAppointmentDate(argMultimap.getValue(PREFIX_DATE_OF_APPT).get());
        AppointmentTime time = ParserUtil.parseAppointmentTime(argMultimap.getValue(PREFIX_START_TIME).get());

        DoctorMatch doctorMatch = new DoctorMatch(specialisation, date, time);

        return new DoctorMatchCommand(new DoctorSpecialisationMatchesPredicate(doctorMatch));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
