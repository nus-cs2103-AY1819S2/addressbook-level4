package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import seedu.address.logic.commands.TaskCalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.TaskStartDateContainsKeywordsPredicate;

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
        String[] keywords = new String[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (trimmedArgs.isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            keywords[0] = currentDate.format(formatter);
            ContainsKeywordsPredicate predicate = new TaskStartDateContainsKeywordsPredicate(Arrays.asList(keywords));
            return new TaskCalendarCommand(new DateCustom(currentDate.format(formatter)), predicate);
        }
        if (!DateCustom.isValidDate(trimmedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCalendarCommand.MESSAGE_USAGE));
        }
        keywords[0] = trimmedArgs;
        ContainsKeywordsPredicate predicate = new TaskStartDateContainsKeywordsPredicate(Arrays.asList(keywords));
        DateCustom givenDate = new DateCustom(trimmedArgs);
        return new TaskCalendarCommand(givenDate, predicate);
    }
}
