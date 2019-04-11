/* @@author itszp */
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new OpenCommand object
 */
public class OpenCommandParser implements Parser<OpenCommand> {
    /**
     * Parses the given {@code String} of arguments in the context
     * of the OpenCommand and returns an OpenCommand object for execution.
     *
     * @throws ParseException if the string is empty.
     */
    public OpenCommand parse(String args) throws ParseException {
        args = args.trim();

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
        }

        return new OpenCommand(args);
    }
}
