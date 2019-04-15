package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEmployeeCommand;
import seedu.address.logic.commands.ListProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    private static final Pattern LIST_COMMAND_FORMAT = Pattern.compile("(?<keyword>\\S+)");

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        final Matcher matcher = LIST_COMMAND_FORMAT.matcher(trimmedArgs);

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        final String keyword = matcher.group("keyword").toLowerCase();

        if (keyword.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        if (keyword.equals(ListEmployeeCommand.LIST_EMPLOYEE_KEYWORD)) {
            return new ListEmployeeCommand();
        } else if (keyword.equals(ListProjectCommand.LIST_PROJECT_KEYWORD)) {
            return new ListProjectCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

}
