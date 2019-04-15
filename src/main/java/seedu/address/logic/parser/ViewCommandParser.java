package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewEmployeeCommand;
import seedu.address.logic.commands.ViewProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewEmployeeCommand object or a ViewProjectCommand
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Used for separation of view type word and args.
     */
    private static final Pattern VIEW_COMMAND_FORMAT = Pattern.compile("(?<keyword>\\S+)(?<arguments>.*)");


    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {

        final Matcher matcher = VIEW_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        final String keyword = matcher.group("keyword").toLowerCase();
        final String arguments = matcher.group("arguments");

        if (keyword.equals(ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD) || keyword.equals("e")) {
            try {
                Index index = ParserUtil.parseIndex(arguments.trim());
                return new ViewEmployeeCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEmployeeCommand.MESSAGE_USAGE), pe);
            }
        } else if (keyword.equals(ViewProjectCommand.VIEW_PROJECT_KEYWORD) || keyword.equals("p")) {
            try {
                Index index = ParserUtil.parseIndex(arguments.trim());
                return new ViewProjectCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewProjectCommand.MESSAGE_USAGE), pe);
            }
        } else {
            throw new ParseException (
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE)
            );
        }
    }
}
