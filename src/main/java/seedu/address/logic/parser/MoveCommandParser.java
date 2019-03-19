package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.pdf.Directory;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class MoveCommandParser implements Parser<MoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        Directory directory;

        try {
            String[] parseArgs = args.trim().split("\\s+");
            if(parseArgs.length != 2){
                throw new ParseException(MoveCommand.MESSAGE_USAGE);
            }
            index = ParserUtil.parseIndex(parseArgs[0]);
            directory = ParserUtil.parseDirectory(parseArgs[1]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE), pe);
        }

        return new MoveCommand(index, directory);
    }
}
