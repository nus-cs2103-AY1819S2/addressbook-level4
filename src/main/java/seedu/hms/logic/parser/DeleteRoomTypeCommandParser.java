package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.DeleteRoomTypeCommand;
import seedu.hms.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRoomTypeCommand object
 */
public class DeleteRoomTypeCommandParser implements Parser<DeleteRoomTypeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRoomTypeCommand
     * and returns an DeleteRoomTypeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRoomTypeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteRoomTypeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRoomTypeCommand.MESSAGE_USAGE), pe);
        }
    }

}
