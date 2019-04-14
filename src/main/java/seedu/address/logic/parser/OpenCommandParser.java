package seedu.address.logic.parser;

import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.ParsedInOut;

/**
 * Parses input arguments and creates a new OpenCommand object.
 */
public class OpenCommandParser implements Parser<OpenCommand> {

    /**
     * Parses the given argument {@code String} in the context of the OpenCommand
     * and returns an OpenCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenCommand parse(String args) throws ParseException {
        try {
            ParsedInOut parsedInOut = ParserUtil.parseOpenSave(args);
            return new OpenCommand(parsedInOut);
        } catch (ParseException pe) {
            throw new ParseException(String.format("%s\n%s", pe.getMessage(), OpenCommand.MESSAGE_USAGE), pe);
        }
    }
}
