package seedu.finance.logic.parser;

//@author Jackimaru96

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.finance.logic.commands.ThemeCommand;
import seedu.finance.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns a ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ThemeCommand.MESSAGE_USAGE));

        }
        return new ThemeCommand(args);
    }
}
