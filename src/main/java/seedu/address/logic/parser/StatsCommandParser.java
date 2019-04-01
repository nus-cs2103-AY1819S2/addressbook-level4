package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns a StatsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StatsCommand parse(String userInput) throws ParseException {
        //TODO: Implement parse usage for KEYWORD
        requireNonNull(userInput);
        try {
            Index idx = ParserUtil.parseIndex(userInput);
            return new StatsCommand(idx);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE), pe);
        }
    }
}
