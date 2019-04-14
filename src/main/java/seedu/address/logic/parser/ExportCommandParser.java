/* @@author randytqw */
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context
     * of the ImportCommand and returns an ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ExportCommand parse(String args) throws ParseException {

        // Boolean value to indicate if FomoFoto should print directory or file return message.
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_FILENAME, PREFIX_DIRECTORY);
        if (!arePrefixesPresent(map, PREFIX_FILENAME, PREFIX_DIRECTORY)
            || !map.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        String filename = ParserUtil.parseFilename(map.getValue(PREFIX_FILENAME).get());
        String path = ParserUtil.parseFilename(map.getValue(PREFIX_DIRECTORY).get());
        return new ExportCommand(path, filename);

    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
