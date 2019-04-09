/* @@author thamsimun */
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddPresetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SavePresetCommand object
 */
public class AddPresetCommandParser implements Parser<AddPresetCommand> {

    /**
     * Parses the Preset Command.
     * @param args
     * @return a PresetCommand object
     * @throws ParseException is thrown if the value is not an integer or unspecified.
     */
    @Override
    public AddPresetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        String[] parsed = args.split(" ");
        System.out.println("length is " + parsed.length);
        if (parsed.length != 1) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddPresetCommand.MESSAGE_USAGE));
        }
        return new AddPresetCommand(parsed[0]);
    }
}
/* @@author */
