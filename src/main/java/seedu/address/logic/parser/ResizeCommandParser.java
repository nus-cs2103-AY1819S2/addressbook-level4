/* @@author kayheen */
package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ResizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class parses the resize command.
 */
public class ResizeCommandParser implements Parser<ResizeCommand> {

    /**
     * Parses the Resize Command.
     * @param args is the arguments taken in with the command.
     * @return a ResizeCommand object.
     * @throws ParseException
     */
    public ResizeCommand parse(String args) throws ParseException {
        try {
            args = args.trim();
            String[] parsed = args.split(" ");
            if (parsed.length > 2) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        ResizeCommand.MESSAGE_USAGE));
            }
            String w = parsed[0];
            String h = parsed[1];
            int width = Integer.parseInt(w);
            int height = Integer.parseInt(h);

            if (width <= 0 || height <= 0) {
                throw new ParseException(String.format(Messages.MESSAGE_RESIZE_VALUE_ERROR,
                        ResizeCommand.MESSAGE_USAGE));
            }
            return new ResizeCommand(width, height);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(Messages.MESSAGE_RESIZE_VALUE_ERROR, ResizeCommand.MESSAGE_USAGE));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ResizeCommand.MESSAGE_USAGE));
        }
    }
}

