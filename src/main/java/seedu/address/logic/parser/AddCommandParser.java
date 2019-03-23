package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.pdf.Directory;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.Size;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final String MESSAGE_NO_FILE_SELECTED = "No file selected";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        File file = null;
        Set<Tag> tags = null;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILE, PREFIX_TAG);

        if (arePrefixesPresent(argMultimap, PREFIX_FILE)
                && argMultimap.getPreamble().isEmpty()) {

            if (argMultimap.getValue(PREFIX_FILE).isPresent()) {

                file = ParserUtil.parseFile(argMultimap.getValue(PREFIX_FILE).get());
                tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

        } else if (!arePrefixesPresent(argMultimap, PREFIX_FILE)
                && argMultimap.getPreamble().isEmpty()) {

            Optional<File> fileContainer = new AddGuiParser().selectPdf();

            if (!fileContainer.isPresent()) {
                throw new ParseException(AddCommandParser.MESSAGE_NO_FILE_SELECTED);
            } else {
                file = fileContainer.get();
                tags = new HashSet<>();
            }

        } else if (!arePrefixesPresent(argMultimap, PREFIX_FILE)
                && !argMultimap.getPreamble().isEmpty()) {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        }

        try {
            Name name = new Name(file.getName());
            Directory directory = new Directory(file.getParent());
            Size size = new Size(Long.toString(file.length()));

            Pdf pdf = new Pdf(name, directory, size, tags);
            return new AddCommand(pdf);

        } catch (Exception e) {
            throw new ParseException(AddCommand.MESSAGE_INVALID_SELECTION);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
