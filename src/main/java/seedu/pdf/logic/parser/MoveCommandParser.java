package seedu.pdf.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DIRECTORY;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.MoveCommand;
import seedu.pdf.logic.parser.exceptions.ParseException;
import seedu.pdf.model.pdf.Directory;

/**
 * Parses input arguments and creates a new RenameCommand object
 */
public class MoveCommandParser implements Parser<MoveCommand> {

    private static final String MESSAGE_NO_DIR_SELECTED = "No Directory Selected";

    /**
     * Parses the given {@code String} of arguments in the context of the RenameCommand
     * and returns an RenameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        Directory directory;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIRECTORY);
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE), pe);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_DIRECTORY)) {

            if (argMultimap.getValue(PREFIX_DIRECTORY).isPresent()) {
                directory = ParserUtil.parseDirectory(argMultimap.getValue(PREFIX_DIRECTORY).get());
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
            }

        } else {

            Optional<File> selectedDirContainer = new MoveGuiParser().selectDirectory();

            if (!selectedDirContainer.isPresent()) {
                throw new ParseException(MoveCommandParser.MESSAGE_NO_DIR_SELECTED);
            }

            directory = ParserUtil.parseDirectory(selectedDirContainer.get().getAbsolutePath());

        }

        /*try {
            String[] parseArgs = args.trim().split("\\s+");
            if (parseArgs.length != 2) {
                throw new ParseException(MoveCommand.MESSAGE_USAGE);
            }
            index = ParserUtil.parseIndex(parseArgs[0]);
            directory = ParserUtil.parseDirectory(parseArgs[1]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE), pe);
        }*/

        return new MoveCommand(index, directory);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
