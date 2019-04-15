package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.parseIndex;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecordMcCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for RecordMcCommand
 */
public class RecordMcCommandParser implements Parser<RecordMcCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RecordMcCommand
     * and returns an RecordMcCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordMcCommand parse(String args) throws ParseException {
        try {
            String[] results = ParserUtil.parseRecordMc(args);
            Index index = parseIndex(results[0]);
            String daysToRest = results[1];
            return new RecordMcCommand(index, daysToRest);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordMcCommand.MESSAGE_USAGE), pe);
        }
    }
}
