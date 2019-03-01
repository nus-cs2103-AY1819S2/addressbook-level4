package seedu.address.logic.parser;

import java.io.File;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            File file = ParserUtil.parseFile(args);
            return new ExportCommand(file);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
    }
    // TODO: Combine ImportCommandParser and ExportCommandParser

}
