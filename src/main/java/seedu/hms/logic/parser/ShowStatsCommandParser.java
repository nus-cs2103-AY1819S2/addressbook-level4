package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.ShowStatsCommand;
import seedu.hms.logic.parser.exceptions.ParseException;

/**
 * Parser for the stats command.
 */
public class ShowStatsCommandParser implements Parser<ShowStatsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ShowStatsCommand
     * and returns an ShowStatsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowStatsCommand parse(String args) throws ParseException {
        try {
            if (!args.isEmpty()) {
                ArrayList<Index> indexList = new ArrayList<>();
                ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args.trim().split("\\s+")));
                for (String a : argsList) {
                    indexList.add(ParserUtil.parseIndex(a));
                }
                return new ShowStatsCommand(indexList);
            }
            return new ShowStatsCommand();
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowStatsCommand.MESSAGE_USAGE), pe);
        }
    }
}
