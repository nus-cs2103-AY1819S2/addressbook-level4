package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SomethingColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.apparel.ColorValue;

/**
 * Parses input arguments and creates a new SomethingColorCommand object
 */
// @@author PhilipPhil
public class SomethingColorParser implements Parser<SomethingColorCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SomethingColorCommand
     * and returns an SomethingColorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SomethingColorCommand parse(String userInput) throws ParseException {
        try {
            ColorValue colorValue = ParserUtil.parseColor(userInput).getPrimary();
            return new SomethingColorCommand(colorValue);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SomethingColorCommand.MESSAGE_USAGE), pe);
        }
    }
}
