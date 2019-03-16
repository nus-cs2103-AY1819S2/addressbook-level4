package seedu.address.logic.parser;

import java.io.File;

import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            File file = ParserUtil.parseOpenSave(args);
            openValidation(file);
            return new OpenCommand(file);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
    }

    private void openValidation(File file) throws ParseException {
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new ParseException("File is invalid");
        }
    }

}
