package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.request.DeleteRequestCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRequest object.
 */
public class DeleteRequestParser implements Parser<DeleteRequestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRequestCommand
     * and returns a DeleteRequestCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteRequestCommand parse(String userInput) throws ParseException {
        try {
            Index index;
            if (userInput.split(" ").length < 2) {
                index = ParserUtil.parseIndex(userInput);
                return new DeleteRequestCommand(index);
            }
            index = ParserUtil.parseIndex(userInput);
            return new DeleteRequestCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRequestCommand.MESSAGE_USAGE), pe);
        }

    }
}
