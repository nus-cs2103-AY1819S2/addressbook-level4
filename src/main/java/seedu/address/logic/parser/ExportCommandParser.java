package seedu.address.logic.parser;

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
            return new ExportCommand(parsedInOut);
        } catch (ParseException pe) {
            throw new ParseException(String.format("%s\n%s", pe.getMessage(), ExportCommand.MESSAGE_USAGE), pe);
        }
    }
}
