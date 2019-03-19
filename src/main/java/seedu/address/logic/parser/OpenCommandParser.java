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
            openValidation(parsedInOut);
            return new OpenCommand(parsedInOut.getFile());
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
    }

    /**
     * openValidation() checks if the file exists, is a file and can be read.
     * @param parsedInOut
     * @throws ParseException
     */
    private void openValidation(ParsedInOut parsedInOut) throws ParseException {
        if (parsedInOut.getType().equals("json")) {
            throw new ParseException("Only .json file type can be opened!");
        } else {
            if (!parsedInOut.getFile().exists()) {
                throw new ParseException("File not found!");
            } else if (!parsedInOut.getFile().isFile()) {
                throw new ParseException("File is invalid!");
            } else if (!parsedInOut.getFile().canRead()) {
                throw new ParseException("File cannot be read!");
            }
        }
    }

}
