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
            try {
                openValidation(parsedInOut);
                return new OpenCommand(parsedInOut.getFile());
            } catch (ParseException pe) {
                throw new ParseException(String.format("%s\n%s", pe.getMessage(), OpenCommand.MESSAGE_USAGE), pe);
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format("%s\n%s", pe.getMessage(), OpenCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * openValidation() checks if the file exists, is a file and can be read.
     * @param parsedInOut the ParsedInOut object which contains parsed information from the input.
     * @throws ParseException if the file is not a .json type
     *                        if the file does not exist
     *                        if the file is not a file
     *                        if the file cannot be read
     */
    private void openValidation(ParsedInOut parsedInOut) throws ParseException {
        if (!parsedInOut.getType().equals("json")) {
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
