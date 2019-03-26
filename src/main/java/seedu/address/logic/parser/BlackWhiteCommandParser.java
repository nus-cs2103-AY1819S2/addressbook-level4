package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
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
        requireNonNull(args);
        String fileName;
        OptionalInt threshold;
        args = args.trim();
        String[] parsed = args.split(" ");

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlackWhiteCommand.MESSAGE_USAGE));
        }

        if (parsed.length < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlackWhiteCommand.MESSAGE_USAGE));
        }

        if (parsed.length == 2) {
            try {
                threshold = OptionalInt.of(Integer.parseInt(parsed[0]));
            } catch (NumberFormatException e) {
                throw new ParseException(Messages.MESSAGE_BLACKWHITE_INT_ERROR);
            }
            Character lastletter = parsed[0].charAt(parsed[0].length() - 1);
            if (lastletter.equals('f') || lastletter.equals('F') || lastletter.equals('d') || lastletter.equals('D')) {
                throw new ParseException(Messages.MESSAGE_BLACKWHITE_INT_ERROR);
            }
            if (threshold.getAsInt() < 0) {
                throw new ParseException(Messages.MESSAGE_NEGATIVE_ERROR);
            }
            fileName = parsed[1];
        } else {
            threshold = OptionalInt.empty();
            fileName = parsed[0];
        }

        File directory = new File(ASSETS_FILEPATH + fileName);

        if (!directory.exists()) {
            throw new ParseException(Messages.MESSAGE_FILE_DOES_NOT_EXIST);
        }

        return new BlackWhiteCommand(threshold, fileName);
    }

}


