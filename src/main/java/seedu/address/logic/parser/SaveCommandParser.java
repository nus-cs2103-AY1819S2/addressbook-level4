package seedu.address.logic.parser;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_FILE;

import java.io.File;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new OpenCommand object
 */
public class SaveCommandParser implements Parser<SaveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context
     * of the SaveCommand and returns an SaveCommand object for execution.
     * @throws ParseException if image name already exists in assets folder.
     */
    public SaveCommand parse(String args) throws ParseException {
        args = args.trim();
        File file = new File(ASSETS_FILEPATH + args);

        if (args.isEmpty()) {
            return new SaveCommand(args);
        } else if (file.exists()) {
            throw new ParseException(String.format(MESSAGE_DUPLICATE_FILE + " Try using another name."));
        }
        return new SaveCommand(args);
    }
}
