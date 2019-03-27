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
     * @param args
     * @return a BlackWhiteCommand object
     * @throws ParseException
     */
    public BlackWhiteCommand parse(String args) throws ParseException {
        OptionalInt threshold;

        if (args.isEmpty()) {
            threshold = OptionalInt.empty();
        } else {
            args = args.trim();
            String[] parsed = args.split(" ");
            if (parsed.length == 1) {
                try {
                    threshold = OptionalInt.of(Integer.parseInt(parsed[0]));
                } catch (NumberFormatException e) {
                    throw new ParseException(Messages.MESSAGE_BLACKWHITE_INT_ERROR);
                }
                Character lastletter = parsed[0].charAt(parsed[0].length() - 1);
                if (lastletter.equals('f') || lastletter.equals('F') || lastletter.equals('d')
                    || lastletter.equals('D')) {
                    throw new ParseException(Messages.MESSAGE_BLACKWHITE_INT_ERROR);
                }
                if (threshold.getAsInt() < 0) {
                    throw new ParseException(Messages.MESSAGE_NEGATIVE_ERROR);
                }
            } else {
                threshold = OptionalInt.empty();
            }
        }
        return new BlackWhiteCommand(threshold);
    }

}


