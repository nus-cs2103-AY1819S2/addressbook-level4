package seedu.address.logic.parser.medicalhistory;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_MEDHIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WRITEUP;

import java.util.stream.Stream;

import seedu.address.logic.commands.medicalhistory.AddMedHistCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.PersonId;


/**
 * Parses input arguments and creates a new AddMedHistCommand object
 */
public class AddMedHistCommandParser implements Parser<AddMedHistCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMedHistCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_PATIENT_ID, PREFIX_DOCTOR_ID, PREFIX_DATE_OF_MEDHIST, PREFIX_WRITEUP);

        if (!arePrefixesPresent
                (argMultimap, PREFIX_PATIENT_ID, PREFIX_DOCTOR_ID, PREFIX_DATE_OF_MEDHIST, PREFIX_WRITEUP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedHistCommand.MESSAGE_USAGE));
        }
        PersonId patientId = ParserUtil.parsePersonId(argMultimap.getValue(PREFIX_PATIENT_ID).get());
        PersonId doctorId = ParserUtil.parsePersonId(argMultimap.getValue(PREFIX_DOCTOR_ID).get());
        ValidDate validDate = ParserUtil.parseValidDate(argMultimap.getValue(PREFIX_DATE_OF_MEDHIST).get());
        WriteUp writeUp = ParserUtil.parseWriteUp(argMultimap.getValue(PREFIX_WRITEUP).get());

        MedicalHistory medicalHistory = new MedicalHistory(patientId, doctorId, validDate, writeUp);

        return new AddMedHistCommand(medicalHistory);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

