package seedu.address.logic.parser;

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
            importValidation(parsedInOut);
            return new ImportCommand(parsedInOut);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
    }

    /**
     * importValidation() checks if the file exists, is a file and can be read.
     * @param parsedInOut
     * @throws ParseException
     */
    private void importValidation(ParsedInOut parsedInOut) throws ParseException {
        if (!parsedInOut.getType().equals("json")) {
            throw new ParseException("Only .json file type can by imported!");
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
