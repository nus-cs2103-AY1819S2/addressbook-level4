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
     * @throws ParseException
     */
    public RotateCommand parse(String args) throws ParseException {
        try {
            args = args.trim();
            String[] parsed = args.split(" ");
            String deg = parsed[0];
            String fileName = parsed[1];
            int degree = 0;
            degree = Integer.parseInt(deg);
            return new RotateCommand(degree, fileName);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_ROTATE_DEGREE_ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.MESSAGE_FILE_NAME_UNSPECIFIED);
        }
        // next version, we need to ensure that we can accept both with and without file name
    }
}
