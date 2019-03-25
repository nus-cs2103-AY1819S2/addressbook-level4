package seedu.address.logic.parser;

import java.io.File;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.ParsedInOut;

/**
 * Parses input arguments and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given argument {@code String} in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        try {
            ParsedInOut parsedInOut = ParserUtil.parseImportExport(args);
            try {
                exportValidation(parsedInOut.getFile());
                return new ExportCommand(parsedInOut);
            } catch (ParseException pe) {
                throw new ParseException(String.format("%s\n%s", pe.getMessage(), ExportCommand.MESSAGE_USAGE), pe);
            }
        } catch (ParseException pe) {
            System.out.println(ExportCommand.MESSAGE_USAGE);
            throw new ParseException(String.format("%s\n%s", pe.getMessage(), ExportCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * exportValidation() checks if the file is writable if it exists.
     * @param file the file to be saved to.
     * @throws ParseException if the user is trying to write to a read only file
     */
    private void exportValidation(File file) throws ParseException {
        if (file.exists() && !file.canWrite()) {
            throw new ParseException("File is read only!");
        }
    }
}
