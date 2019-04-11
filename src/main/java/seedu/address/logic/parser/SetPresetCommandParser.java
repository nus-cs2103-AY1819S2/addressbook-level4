/* @@author thamsimun */
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.SetPresetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SavePresetCommand object
 */
public class SetPresetCommandParser implements Parser<SetPresetCommand> {

    /**
     * Parses the Preset Command.
     * @param args argument
     * @return a PresetCommand object
     * @throws ParseException is thrown if the value is not an integer or unspecified.
     */
    @Override
    public SetPresetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        if (args.equals("")) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SetPresetCommand.MESSAGE_USAGE));
        }
        return new SetPresetCommand(args);
    }
}
/* @@author */
