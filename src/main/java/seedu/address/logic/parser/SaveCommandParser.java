/* @@author itszp */
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SAVE_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SAVE_TYPE;

import java.util.Arrays;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new OpenCommand object
 */
public class SaveCommandParser implements Parser<SaveCommand> {
    // Represents a string array of valid file types allowed in assets folder.
    private String[] validFileTypes = new String[]{".jpg", ".png", ".gif", ".bmp", ".jpeg"};

    /**
     * Parses the given {@code String} of arguments in the context
     * of the SaveCommand and returns an SaveCommand object for execution.
     * @throws ParseException if args is not a valid file name.
     */
    public SaveCommand parse(String args) throws ParseException {
        boolean isValidName = false;
        args = args.trim();

        if (args.isEmpty()) {
            return new SaveCommand(args);
        }

        for (String x : validFileTypes) {
            if (args.endsWith(x)) {
                String substring = args.substring(0, args.length() - x.length());
                if (substring.isEmpty()) {
                    throw new ParseException(MESSAGE_INVALID_SAVE_NAME);
                }
                isValidName = true;
                break;
            }
        }

        if (!isValidName) {
            throw new ParseException(String.format(MESSAGE_INVALID_SAVE_TYPE, Arrays.toString(validFileTypes)));
        }

        return new SaveCommand(args);
    }
}
