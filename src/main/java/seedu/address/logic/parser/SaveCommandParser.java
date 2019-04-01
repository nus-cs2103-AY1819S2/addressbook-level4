/* @@author itszp */
package seedu.address.logic.parser;

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

        if (args.isEmpty()) {
            return new SaveCommand(args);
        }
        return new SaveCommand(args);
    }
}
