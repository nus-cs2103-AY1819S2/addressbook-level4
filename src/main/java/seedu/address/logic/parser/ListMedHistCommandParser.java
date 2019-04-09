package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_MEDHIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.ListMedHistCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.person.PersonId;

/**
 * Parses input arguments and creates a new ListMedHistCommand object
 */
public class ListMedHistCommandParser implements Parser<ListMedHistCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListMedHistCommand
     * and returns an ListMedHistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListMedHistCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_PATIENT_ID, PREFIX_DOCTOR_ID, PREFIX_DATE_OF_MEDHIST);

        PersonId patientId = null;
        PersonId doctorId = null;
        ValidDate date = null;

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMedHistCommand.MESSAGE_USAGE));
        }

        patientId = presentPatientIdOrNull(PREFIX_PATIENT_ID, argMultimap);
        doctorId = presentDoctorIdOrNull(PREFIX_DOCTOR_ID, argMultimap);
        date = presentDateOrNull(argMultimap);

        return new ListMedHistCommand(patientId, doctorId, date);
    }

    /**
     * Returns PersonId if patient id present and the format is correct
     * {@code ArgumentMultimap}.
     */
    private static PersonId presentPatientIdOrNull(Prefix prefix, ArgumentMultimap argumentMultimap)
            throws ParseException {
        PersonId patientId = null;
        if (isPrefixPresent(argumentMultimap, prefix)) {
            patientId = ParserUtil.parsePersonId(argumentMultimap.getValue(PREFIX_PATIENT_ID).get());
        }
        return patientId;
    }

    /**
     * Returns id if doctor id present and the format is correct
     * {@code ArgumentMultimap}.
     */
    private static PersonId presentDoctorIdOrNull(Prefix prefix, ArgumentMultimap argumentMultimap)
            throws ParseException {
        PersonId doctorId = null;
        if (isPrefixPresent(argumentMultimap, prefix)) {
            doctorId = ParserUtil.parsePersonId(argumentMultimap.getValue(PREFIX_DOCTOR_ID).get());
        }
        return doctorId;
    }

    /**
     * Returns date if date presents and the format is correct
     * {@code ArgumentMultimap}.
     */
    private static ValidDate presentDateOrNull(ArgumentMultimap argumentMultimap) throws ParseException {
        ValidDate date = null;
        if (isPrefixPresent(argumentMultimap, PREFIX_DATE_OF_MEDHIST)) {
            date = ParserUtil.parseValidDate(argumentMultimap.getValue(PREFIX_DATE_OF_MEDHIST).get());
        }
        return date;
    }

    /**
     * Returns true if none of the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
