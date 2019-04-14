package seedu.address.logic.parser.medicalhistory;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.medicalhistory.SortMedHistCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SearchMedHistCommand object
 */
public class SortMedHistCommandParser implements Parser<SortMedHistCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortMedHistCommand
     * and returns an SortMedHistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortMedHistCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        SortMedHistCommand.SortMedHistDescriptor sortMedHistDescriptor = new SortMedHistCommand.SortMedHistDescriptor();

        if (!trimmedArgs.isEmpty()) {
            if (trimmedArgs.equals(SortMedHistCommand.ASCENDING) || trimmedArgs.equals(SortMedHistCommand.DESCENDING)) {
                sortMedHistDescriptor.setOrder(Optional.of(trimmedArgs));
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortMedHistCommand.MESSAGE_USAGE));
            }
        }

        return new SortMedHistCommand(sortMedHistDescriptor);
    }
}
