package seedu.address.logic.parser.prescription;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;

import seedu.address.logic.commands.prescription.ListPrescriptionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonId;

/**
 * Parses input arguments and creates a new ListMedHistCommand object
 */
public class ListPrescriptionCommandParser implements Parser<ListPrescriptionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListMedHistCommand
     * and returns an ListMedHistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListPrescriptionCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_PATIENT_ID, PREFIX_DOCTOR_ID);

        PersonId patientId = null;
        PersonId doctorId = null;

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListPrescriptionCommand.MESSAGE_USAGE));
        }

        patientId = presentPatientIdOrNull(PREFIX_PATIENT_ID, argMultimap);
        doctorId = presentDoctorIdOrNull(PREFIX_DOCTOR_ID, argMultimap);

        return new ListPrescriptionCommand(patientId, doctorId);
    }

    /**
     * Returns PersonId if patient id present and the format is correct
     * If PREFIX_PATIENT_ID is not present,null is returned
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
     * If PREFIX_DOCTOR_ID is not present,null is returned
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
     * Returns true if none of the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}

