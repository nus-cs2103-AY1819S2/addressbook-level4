package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATH;

import java.io.File;

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
        requireNonNull(args);
        args = args.trim();
        String[] parsed = args.split(" ");

        if (parsed.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ContrastCommand.MESSAGE_USAGE));
        }

        String operator = parsed[0];

        if (!operator.equals("add") && !operator.equals("subtract")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ContrastCommand.MESSAGE_USAGE));
        }

        String fileName = parsed[1];
        File directory = new File("src/main/resources/assets/" + fileName);

        if (!directory.exists()) {
            throw new ParseException(String.format(MESSAGE_INVALID_PATH, ContrastCommand.MESSAGE_USAGE));
        }

        return new ContrastCommand(operator, fileName);
    }
}
