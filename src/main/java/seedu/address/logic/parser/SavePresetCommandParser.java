/* @@author thamsimun */
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.SavePresetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SavePresetCommand object
 */
public class SavePresetCommandParser implements Parser<SavePresetCommand> {

    /**
     * Parses the SavePreset Command.
     * @param args argument
     * @return a SavePresetCommand object
     * @throws ParseException is thrown if the value is not an integer or unspecified.
     */
    @Override
    public SavePresetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        if (args.equals("")) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SavePresetCommand.MESSAGE_USAGE));
        }
        return new SavePresetCommand(args);
    }


}
/* @@author*/
