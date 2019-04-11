/* @@author thamsimun */
package seedu.address.logic.parser;

import java.util.OptionalInt;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.BlackWhiteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class parses the black/white command.
 */
public class BlackWhiteCommandParser implements Parser<BlackWhiteCommand> {

    /**
     * Parses the BlackWhite Command.
     * @param args argument
     * @return a BlackWhiteCommand object
     * @throws ParseException is thrown if the value is not an integer or unspecified.
     */
    public BlackWhiteCommand parse(String args) throws ParseException {
        OptionalInt threshold;

        if (args.isEmpty()) {
            threshold = OptionalInt.empty();
        } else {
            args = args.trim();
            String[] parsed = args.split(" ");
            if (parsed.length > 1) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    BlackWhiteCommand.MESSAGE_USAGE));
            }
            if (parsed.length == 1) {
                try {
                    threshold = OptionalInt.of(Integer.parseInt(parsed[0]));
                } catch (NumberFormatException e) {
                    throw new ParseException(String.format(Messages.MESSAGE_BLACKWHITE_INT_ERROR,
                        BlackWhiteCommand.MESSAGE_USAGE));
                }
                Character lastletter = parsed[0].charAt(parsed[0].length() - 1);
                if (lastletter.equals('f') || lastletter.equals('F') || lastletter.equals('d')
                    || lastletter.equals('D')) {
                    throw new ParseException(String.format(Messages.MESSAGE_BLACKWHITE_INT_ERROR,
                        BlackWhiteCommand.MESSAGE_USAGE));
                }
                if (threshold.isPresent() && (threshold.getAsInt() < 0)) {
                    throw new ParseException(String.format(Messages.MESSAGE_NEGATIVE_ERROR,
                        BlackWhiteCommand.MESSAGE_USAGE));
                }
            } else {
                threshold = OptionalInt.empty();
            }
        }
        return new BlackWhiteCommand(threshold);
    }

}
/* @@author*/


