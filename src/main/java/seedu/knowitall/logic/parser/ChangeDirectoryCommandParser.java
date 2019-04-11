package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.logic.commands.ChangeDirectoryCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangeDirectoryCommand object
 */
public class ChangeDirectoryCommandParser implements Parser<ChangeDirectoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeDirectoryCommand
     * and returns an ChangeDirectoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeDirectoryCommand parse(String args) throws ParseException {
        try {
            if (ParserUtil.parseHomeSymbol(args)) {
                return new ChangeDirectoryCommand();
            }
            Index index = ParserUtil.parseIndex(args);
            return new ChangeDirectoryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeDirectoryCommand.MESSAGE_USAGE), pe);
        }
    }
}
