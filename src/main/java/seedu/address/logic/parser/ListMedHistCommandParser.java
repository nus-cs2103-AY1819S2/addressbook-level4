package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_MEDHIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;

import seedu.address.logic.commands.ListMedHistCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDate;

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

        String patientId = null;
        String doctorId = null;
        LocalDate date = null;

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMedHistCommand.MESSAGE_USAGE));
        }

        if (isPrefixPresent(argMultimap, PREFIX_PATIENT_ID)) {
            patientId = argMultimap.getValue(PREFIX_PATIENT_ID).get();
            // If Id is implemented, may use parseId to detect the ParseException
            if (!patientId.matches("[\\p{Alnum}]")) {
                throw new ParseException("patientId should be an id of patient.\n" + ListMedHistCommand.MESSAGE_USAGE);
            }
        }

        if (isPrefixPresent(argMultimap, PREFIX_DOCTOR_ID)) {
            doctorId = argMultimap.getValue(PREFIX_DOCTOR_ID).get();
            // If Id is implemented, may use parseId to detect the ParseException
            if (!doctorId.matches("[\\p{Alnum}]")) {
                throw new ParseException("doctorId should be an id of patient.\n" + ListMedHistCommand.MESSAGE_USAGE);
            }
        }

        if (isPrefixPresent(argMultimap, PREFIX_DATE_OF_MEDHIST)) {
            date = LocalDate.parse(argMultimap.getValue(PREFIX_DATE_OF_MEDHIST).get());
        }

        return new ListMedHistCommand(patientId, doctorId, date);
    }

    /**
     * Returns true if none of the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
