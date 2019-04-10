/* @@author thamsimun */
package seedu.address.logic.parser;

import java.util.OptionalDouble;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ContrastCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class parses the contrast command.
 */
public class ContrastCommandParser implements Parser<ContrastCommand> {

    /**
     * Parses the Contrast Command.
     * @param args
     * @return a ContrastCommand object
     * @throws ParseException
     */
    public ContrastCommand parse(String args) throws ParseException {
        OptionalDouble contrastValue;

        if (args.isEmpty()) {
            System.out.println("empty");
            contrastValue = OptionalDouble.empty();
        } else {
            args = args.trim();
            String[] parsed = args.split(" ");
            System.out.println("length is" + parsed.length);
            if (parsed.length > 1) {
                System.out.println("hi");
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ContrastCommand.MESSAGE_USAGE));
            }
            if (parsed.length == 1) {
                try {
                    contrastValue = OptionalDouble.of(Double.parseDouble(parsed[0]));
                } catch (NumberFormatException e) {
                    throw new ParseException(String.format(Messages.MESSAGE_CONTRAST_DOUBLE_ERROR,
                        ContrastCommand.MESSAGE_USAGE));
                }
                Character lastletter = parsed[0].charAt(parsed[0].length() - 1);
                if (lastletter.equals('f') || lastletter.equals('F') || lastletter.equals('d')
                    || lastletter.equals('D')) {
                    throw new ParseException(String.format(Messages.MESSAGE_CONTRAST_DOUBLE_ERROR,
                        ContrastCommand.MESSAGE_USAGE));
                }
                if (contrastValue.getAsDouble() < 0) {
                    throw new ParseException(String.format(Messages.MESSAGE_NEGATIVE_ERROR,
                        ContrastCommand.MESSAGE_USAGE));
                }
            } else {
                contrastValue = OptionalDouble.empty();
            }
        }
        return new ContrastCommand(contrastValue);
    }
}
/* @@author*/
