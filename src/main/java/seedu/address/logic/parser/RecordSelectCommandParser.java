package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecordSelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RecordSelectCommand object
 */
public class RecordSelectCommandParser implements Parser<RecordSelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordSelectCommand
     * and returns an RecordSelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordSelectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RecordSelectCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordSelectCommand.MESSAGE_USAGE), pe);
        }
    }
}
