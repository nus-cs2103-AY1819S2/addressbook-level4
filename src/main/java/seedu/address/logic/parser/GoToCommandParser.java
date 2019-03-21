package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input and creates a new StatsCommand object
 */
public class GoToCommandParser implements Parser<GoToCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GoToCommand
     * and returns a GoToCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public GoToCommand parse(String userInput) throws ParseException {
        //TODO: Implement parse usage for KEYWORD
        requireNonNull(userInput);
        try {
            Index idx = ParserUtil.parseIndex(userInput);
            return new GoToCommand(idx);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE), pe);
        }
    }
}
