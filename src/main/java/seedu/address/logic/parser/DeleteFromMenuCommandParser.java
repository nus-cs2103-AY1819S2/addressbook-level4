package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteFromMenuCommand;
import seedu.address.logic.commands.DeleteFromOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.menu.Code;

/**
 * Parses input arguments and creates a new DeleteFromMenuCommand object
 */
public class DeleteFromMenuCommandParser implements Parser<DeleteFromMenuCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFromMenuCommand
     * and returns an DeleteFromMenuCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFromMenuCommand parse(String args) throws ParseException {
        try {
            Code itemCode = ParserUtil.parseCode(args);
            return new DeleteFromMenuCommand(itemCode);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteFromOrderCommand.MESSAGE_USAGE));
        }
    }

}
