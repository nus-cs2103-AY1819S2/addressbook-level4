package seedu.address.logic.parser.request;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.request.CompleteRequestCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CompleteRequestCommand object
 */
public class CompleteRequestParser implements Parser<CompleteRequestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteRequestCommand
     * and returns an CompleteRequestCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CompleteRequestCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new CompleteRequestCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteRequestCommand.MESSAGE_USAGE), pe);
        }
    }
}
