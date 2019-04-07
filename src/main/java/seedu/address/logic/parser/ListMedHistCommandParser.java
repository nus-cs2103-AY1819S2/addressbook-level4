package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_MEDHIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.ListMedHistCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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

        Integer patientId = null;
        Integer doctorId = null;
        LocalDate date = null;

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMedHistCommand.MESSAGE_USAGE));
        }

        patientId = presentIdOrNull(PREFIX_PATIENT_ID, argMultimap);
        doctorId = presentIdOrNull(PREFIX_DOCTOR_ID, argMultimap);
        date = presentDateOrNull(argMultimap);

        return new ListMedHistCommand(patientId, doctorId, date);
    }

    /**
     * Returns id if id present and the format is correct
     * {@code ArgumentMultimap}.
     */
    private static Integer presentIdOrNull(Prefix prefix, ArgumentMultimap argumentMultimap) throws ParseException {
        Integer id = null;
        if (isPrefixPresent(argumentMultimap, prefix)) {
            try {
                id = Integer.parseInt(argumentMultimap.getValue(prefix).get());
            } catch (NumberFormatException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMedHistCommand.MESSAGE_USAGE));
            }
        }
        return id;
    }

    /**
     * Returns date if date presents and the format is correct
     * {@code ArgumentMultimap}.
     */
    private static LocalDate presentDateOrNull(ArgumentMultimap argumentMultimap) throws ParseException {
        LocalDate date = null;
        if (isPrefixPresent(argumentMultimap, PREFIX_DATE_OF_MEDHIST)) {
            try {
                date = LocalDate.parse(argumentMultimap.getValue(PREFIX_DATE_OF_MEDHIST).get());
            } catch (DateTimeParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMedHistCommand.MESSAGE_USAGE));
            }
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
