package seedu.address.logic.parser;

import java.io.File;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.ParsedInOut;

/**
 * Parses input arguments and creates a new ImportCommand object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given argument {@code String} in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        try {
            ParsedInOut parsedInOut = ParserUtil.parseImportExport(args);
            importValidation(parsedInOut.getFile());
            return new ImportCommand(parsedInOut);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
    }

    private void importValidation(File file) throws ParseException {
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new ParseException("File is invalid");
        }
    }
}
