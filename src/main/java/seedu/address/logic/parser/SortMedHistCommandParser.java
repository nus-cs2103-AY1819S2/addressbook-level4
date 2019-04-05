package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SearchMedHistCommand;
import seedu.address.logic.commands.SortMedHistCommand;
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

        if (trimmedArgs.isEmpty()) {
            return new SortMedHistCommand();
        }

        if (trimmedArgs.equals(SortMedHistCommand.ASCENDING)||trimmedArgs.equals(SortMedHistCommand.DESCENDING)) {
            return new SortMedHistCommand(trimmedArgs);
        }


        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortMedHistCommand.MESSAGE_USAGE));
    }
}
