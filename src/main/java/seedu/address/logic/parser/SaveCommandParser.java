package seedu.address.logic.parser;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.ParsedInOut;

/**
 * Parses input arguments and creates a new SaveCommand object.
 */
public class SaveCommandParser implements Parser<SaveCommand> {

    /**
     * Parses the given argument {@code String} in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SaveCommand parse(String args) throws ParseException {
        try {
            ParsedInOut parsedInOut = ParserUtil.parseOpenSave(args);
            return new SaveCommand(parsedInOut);
        } catch (ParseException pe) {
            throw new ParseException(String.format("%s\n%s", pe.getMessage(), SaveCommand.MESSAGE_USAGE), pe);
        }
    }
}
