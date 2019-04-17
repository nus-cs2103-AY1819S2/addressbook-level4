package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SwapCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SwapCommand object
 */

/**
 * Parses input arguments and creates a new SwapCommand object
 */
public class SwapCommandParser implements Parser<SwapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SwapCommand
     * and returns an SwapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwapCommand parse(String args) throws ParseException {
        try {
            Index[] indexes = ParserUtil.parseSwapValue(args);
            return new SwapCommand(indexes[0], indexes[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwapCommand.MESSAGE_USAGE), pe);
        }
    }
}
