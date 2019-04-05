package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;

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
                args, PREFIX_PATIENT_ID);

        if (!arePrefixPresent(argMultimap, PREFIX_PATIENT_ID) && argMultimap.getPreamble().isEmpty()) {
            return new ListMedHistCommand();
        }

        if (arePrefixPresent(argMultimap, PREFIX_PATIENT_ID) && argMultimap.getPreamble().isEmpty()) {
            String patientId = argMultimap.getValue(PREFIX_PATIENT_ID).get();
            return new ListMedHistCommand(patientId);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMedHistCommand.MESSAGE_USAGE));

    }

    /**
     * Returns true if none of the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
