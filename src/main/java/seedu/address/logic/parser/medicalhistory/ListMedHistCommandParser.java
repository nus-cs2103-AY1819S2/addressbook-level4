package seedu.address.logic.parser.medicalhistory;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_MEDHIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;

import java.util.Optional;

import seedu.address.logic.commands.medicalhistory.ListMedHistCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
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

        ListMedHistCommand.ListMedHistDescriptor listMedHistDescriptor = new ListMedHistCommand.ListMedHistDescriptor();


        if (argMultimap.getValue(PREFIX_PATIENT_ID).isPresent()) {
            PersonId patientId = ParserUtil
                    .parsePersonId(argMultimap.getValue(PREFIX_PATIENT_ID).get());
            listMedHistDescriptor.setPatientId(Optional.of(patientId));
        }

        if (argMultimap.getValue(PREFIX_DOCTOR_ID).isPresent()) {
            PersonId doctorId = ParserUtil
                    .parsePersonId(argMultimap.getValue(PREFIX_DOCTOR_ID).get());
            listMedHistDescriptor.setDoctorId(Optional.of(doctorId));
        }

        if (argMultimap.getValue(PREFIX_DATE_OF_MEDHIST).isPresent()) {
            ValidDate date = ParserUtil
                    .parseValidDate(argMultimap.getValue(PREFIX_DATE_OF_MEDHIST).get());
            listMedHistDescriptor.setDate(Optional.of(date));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMedHistCommand.MESSAGE_USAGE));
        }

        return new ListMedHistCommand(listMedHistDescriptor);
    }

    /**
     * Returns true if none of the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
