package seedu.address.logic.parser.prescription;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.prescription.SortPrescriptionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortPrescriptionCommand object
 */
public class SortPrescriptionCommandParser implements Parser<SortPrescriptionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortPrescriptionCommand
     * and returns an SortPrescriptionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortPrescriptionCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new SortPrescriptionCommand();
        }

        if (trimmedArgs.equals(SortPrescriptionCommand.ASCENDING)
                || trimmedArgs.equals(SortPrescriptionCommand.DESCENDING)) {
            return new SortPrescriptionCommand(trimmedArgs);
        }


        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPrescriptionCommand.MESSAGE_USAGE));
    }
}
