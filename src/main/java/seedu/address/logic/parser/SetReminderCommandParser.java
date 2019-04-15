package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.SetReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetReminderCommand object
 * @author Hui Chun
 */
public class SetReminderCommandParser implements Parser<SetReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * SetReminderCommand and returns an SetReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIME, PREFIX_MESSAGE);

        if (argMultimap.getValue(PREFIX_TIME).isPresent()
                && argMultimap.getValue(PREFIX_MESSAGE).isPresent()) {
            // if there are supplied values for time and message
            String time = argMultimap.getValue(PREFIX_TIME).get();
            String message = argMultimap.getValue(PREFIX_MESSAGE).get();
            if (time.length() == 8 && time.matches("\\d+:\\d+:\\d+")
                    && message.length() != 0) {
                return new SetReminderCommand(time, message);
            } else {
                throw new ParseException(SetReminderCommand.INVALID_TIME_FORMAT);
            }
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetReminderCommand.MESSAGE_USAGE));
        }
    }
}
