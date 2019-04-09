package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickdocs.logic.commands.ViewStorageCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * A parser to return a ViewStorageCommand from user input
 */
public class ViewStorageCommandParser implements Parser<ViewStorageCommand> {

    private static final Pattern ViewStorageCommand_Argument_Format = Pattern.compile("(?<rawpath>\\S+)");

    /**
     * A parser to return a ViewStorageCommand from user input
     */
    public ViewStorageCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStorageCommand.MESSAGE_USAGE));
        }
        Matcher matcher = ViewStorageCommand_Argument_Format.matcher(trimmedArgs);
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStorageCommand.MESSAGE_USAGE)
            );
        }
        String[] path = matcher.group("rawpath").split("\\\\");
        return new ViewStorageCommand(path);
    }
}
