package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.WaterMarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class parses the WaterMark command.
 */
public class WaterMarkCommandParser implements Parser<WaterMarkCommand> {
    /**
     * Parses the WaterMark Command.
     * @param args the watermark message to add.
     * @return a WaterMark object
     * @throws ParseException is thrown if the value is not an integer or unspecified.
     */
    public WaterMarkCommand parse(String args) throws ParseException {
        args = args.trim();
        if ("".equals(args)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    WaterMarkCommand.MESSAGE_USAGE));
        }
        return new WaterMarkCommand(args, true);
    }
}
