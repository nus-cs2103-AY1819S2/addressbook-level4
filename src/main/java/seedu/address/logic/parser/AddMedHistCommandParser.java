package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WRITEUP;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddMedHistCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;


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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_WRITEUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_WRITEUP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedHistCommand.MESSAGE_USAGE));
        }

        Patient patient = null;
        Doctor doctor = null;
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        WriteUp writeUp = ParserUtil.parseWriteUp(argMultimap.getValue(PREFIX_WRITEUP).get());

        MedicalHistory medicalHistory = new MedicalHistory(patient, doctor, name, writeUp);

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

