/* @@author kayheen */
package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RotateCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * This class parses the rotate command.
 */
public class RotateCommandParser implements Parser<RotateCommand> {
    /**
     * Parses the Rotate Command.
     * @param args
     * @return a RotateCommand object
     * @throws ParseException is thrown if the value is not an integer or unspecified.
     */
    public RotateCommand parse(String args) throws ParseException {
        try {
            args = args.trim();
            String[] parsed = args.split(" ");
            String deg = parsed[0];
            int degree = Integer.parseInt(deg);
            return new RotateCommand(degree);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_ROTATE_DEGREE_ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(RotateCommand.MESSAGE_USAGE);
        }
    }
}
