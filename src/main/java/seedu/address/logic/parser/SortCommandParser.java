package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.options.SortOption;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new SortCommand object
 */
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        try {
            String[] token = args.trim().split(" ");
            boolean desc = false;
            if (token.length > 1) {
                if (token[1].equalsIgnoreCase("desc")) {
                    desc = true;
                } else {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
                }
            }
            SortOption sortOption = ParserUtil.parseSortValue(token[0]);
            if (desc == true) {
                return new SortCommand(sortOption, desc);
            } else {
                return new SortCommand(sortOption);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }
    }
}
