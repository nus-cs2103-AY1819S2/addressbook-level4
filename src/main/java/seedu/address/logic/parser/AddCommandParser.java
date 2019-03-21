package seedu.address.logic.parser;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
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

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(new Stage());

        /*ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILE, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_FILE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }*/

        try {
            Name name = new Name(file.getName());
            Directory directory = new Directory(file.getParent());
            Size size = new Size(Long.toString(file.length()));
            Set<Tag> tagList = new HashSet<>();

            Pdf pdf = new Pdf(name, directory, size, tagList);
            return new AddCommand(pdf);

        } catch (Exception e) {
            throw new ParseException(AddCommand.MESSAGE_INVALID_SELECTION);
        }

    }
    /*
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    /*
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    */
}
