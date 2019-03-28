package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.TaskAddCommand;
import seedu.address.logic.commands.TaskCalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.datetime.DateCustom;

/**
 * Parses input arguments and creates a new TaskCalendarCommand object
 */
public class TaskCalendarCommandParser implements Parser<TaskCalendarCommand> {

    public TaskCalendarCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.contains(" ")) {
            trimmedArgs = trimmedArgs.substring(0, trimmedArgs.indexOf(" "));
        }
        if (!DateCustom.isValidDate(trimmedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCalendarCommand.MESSAGE_USAGE));
        }
        DateCustom givenDate = new DateCustom(trimmedArgs);

    }
}
