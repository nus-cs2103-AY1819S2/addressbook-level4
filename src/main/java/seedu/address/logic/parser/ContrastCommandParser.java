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
     * @param args argument
     * @return a ContrastCommand object
     * @throws ParseException is thrown if the value is not an integer or unspecified.
     */
    public ContrastCommand parse(String args) throws ParseException {
        OptionalDouble contrastValue;

        if (args.isEmpty()) {
            contrastValue = OptionalDouble.empty();
        } else {
            args = args.trim();
            String[] parsed = args.split(" ");
            if (parsed.length > 1) {
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
                if (contrastValue.isPresent() && (contrastValue.getAsDouble() < 0)) {
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
