/* @@author thamsimun */
package seedu.address.logic.parser;

import java.util.OptionalDouble;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.BrightnessCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class parses the contrast command.
 */
public class BrightnessCommandParser implements Parser<BrightnessCommand> {

    /**
     * Parses the Contrast Command.
     * @param args
     * @return a ContrastCommand object
     * @throws ParseException
     */
    public BrightnessCommand parse(String args) throws ParseException {
        OptionalDouble brightnessValue;

        if (args.isEmpty()) {
            brightnessValue = OptionalDouble.empty();
        } else {
            args = args.trim();
            String[] parsed = args.split(" ");
            if (parsed.length == 1) {
                try {
                    brightnessValue = OptionalDouble.of(Double.parseDouble(parsed[0]));
                } catch (NumberFormatException e) {
                    throw new ParseException(Messages.MESSAGE_BRIGHTNESS_DOUBLE_ERROR);
                }
                Character lastletter = parsed[0].charAt(parsed[0].length() - 1);
                if (lastletter.equals('f') || lastletter.equals('F') || lastletter.equals('d')
                    || lastletter.equals('D')) {
                    throw new ParseException(Messages.MESSAGE_BRIGHTNESS_DOUBLE_ERROR);
                }
                if (brightnessValue.getAsDouble() < 0) {
                    throw new ParseException(Messages.MESSAGE_NEGATIVE_ERROR);
                }
            } else {
                brightnessValue = OptionalDouble.empty();
            }
        }
        return new BrightnessCommand(brightnessValue);
    }
}
