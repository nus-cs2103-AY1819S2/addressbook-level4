package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import quickdocs.commons.core.index.Index;
import quickdocs.logic.commands.DeleteRemCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteRemCommand} object.
 */
public class DeleteRemCommandParser implements Parser<DeleteRemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteRemCommand}
     * and returns a {@code DeleteRemCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteRemCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteRemCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRemCommand.MESSAGE_USAGE), pe);
        }
    }
}
