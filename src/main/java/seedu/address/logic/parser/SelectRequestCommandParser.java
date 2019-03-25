package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.request.SelectRequestCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectRequestCommand object
 */
public class SelectRequestCommandParser implements Parser<SelectRequestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectRequestCommand
     * object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public SelectRequestCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new SelectRequestCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectRequestCommand.MESSAGE_USAGE), pe);
        }
    }

}
