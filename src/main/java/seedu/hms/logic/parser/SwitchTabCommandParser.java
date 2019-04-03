package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.hms.logic.commands.SwitchTabCommand;
import seedu.hms.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new switchTabCommand object
 */
public class SwitchTabCommandParser implements Parser<SwitchTabCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindNameCommand
     * and returns an FindNameCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwitchTabCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        if (keywords.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
        }

        try {
            Integer panelNumber = Integer.parseInt(keywords[0].trim());
            Integer tabNumber = Integer.parseInt(keywords[1].trim());
            if (panelNumber < 1 || panelNumber > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
            }
            if (panelNumber == 1 && (panelNumber < 1 || panelNumber > 3)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
            }
            if (panelNumber == 1 && (panelNumber < 1 || panelNumber > 2)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
            }
            return new SwitchTabCommand(panelNumber, tabNumber - 1); //transfer to 0 based
        } catch (Exception e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
        }

    }
}
