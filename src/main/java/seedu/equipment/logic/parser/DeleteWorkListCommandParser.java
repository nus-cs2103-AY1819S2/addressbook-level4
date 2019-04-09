package seedu.equipment.logic.parser;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.DeleteWorkListCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteWorkListCommand object
 */
public class DeleteWorkListCommandParser implements Parser<DeleteWorkListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteWorkListCommand
     * and returns an DeleteWorkListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteWorkListCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteWorkListCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteWorkListCommand.MESSAGE_USAGE), pe);
        }
    }

}
