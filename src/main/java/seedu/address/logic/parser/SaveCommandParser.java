package seedu.address.logic.parser;

import java.io.File;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            File file = ParserUtil.parseOpenSave(args);
            saveValidation(file);
            return new SaveCommand(file);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
    }

    /**
     * saveValidation() checks if the file is writable if it exists.
     * @param file
     * @throws ParseException
     */
    private void saveValidation(File file) throws ParseException {
        if (file.exists() && !file.canWrite()) {
            throw new ParseException("File is read only!");
        }
    }
}
