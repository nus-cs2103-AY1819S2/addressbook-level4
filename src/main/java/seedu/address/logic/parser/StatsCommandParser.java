package seedu.address.logic.parser;

import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns an StatsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            return new StatsCommand();
        }

        return new StatsCommand(ParserUtil.filterByKeyword(args, StatsCommand.MESSAGE_USAGE));
    }



}
