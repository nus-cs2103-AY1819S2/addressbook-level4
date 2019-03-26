package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteFromOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.menu.Code;

/**
 * Parses input arguments and creates a new TableModeCommand object
 */
public class DeleteFromOrderCommandParser implements Parser<DeleteFromOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TableModeCommand
     * and returns an TableModeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFromOrderCommand parse(String args) throws ParseException {
        try {
            Code itemCode = ParserUtil.parseCode(args);
            return new DeleteFromOrderCommand(itemCode);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteFromOrderCommand.MESSAGE_USAGE));
        }
    }
}
