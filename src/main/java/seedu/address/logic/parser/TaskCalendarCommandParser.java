package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.TaskCalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.datetime.DateCustom;

/**
 * Parses input arguments and creates a new TaskCalendarCommand object
 */
public class TaskCalendarCommandParser implements Parser<TaskCalendarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskCalendarCommand
     * and returns an TaskCalendarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskCalendarCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.contains(" ")) {
            trimmedArgs = trimmedArgs.substring(0, trimmedArgs.indexOf(" "));
        }
        if (trimmedArgs.isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return new TaskCalendarCommand(new DateCustom(currentDate.format(formatter)));
        }
        if (!DateCustom.isValidDate(trimmedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCalendarCommand.MESSAGE_USAGE));
        }
        DateCustom givenDate = new DateCustom(trimmedArgs);
        return new TaskCalendarCommand(givenDate);
    }
}
