package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickdocs.logic.commands.AddDirectoryCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * A parser to parse User input into AddDirectory Command
 */
public class AddDirectoryCommandParser implements Parser<AddDirectoryCommand> {

    private static final Pattern AddDirectoryCommand_Argument_Format =
            Pattern.compile("(?<rawPath>\\S+)(?:\\s+)(?<name>[^\\s^\\\\]+)");

    /**
     * Parse user input into an AddDirectory Command
     * @param args user input
     * @return A corresponding AddDirectory Command
     * @throws ParseException
     */
    public AddDirectoryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDirectoryCommand.MESSAGE_USAGE));
        }
        Matcher matcher = AddDirectoryCommand_Argument_Format.matcher(trimmedArgs);
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDirectoryCommand.MESSAGE_USAGE));
        }
        String rawPath = matcher.group("rawPath");
        String name = matcher.group("name");
        String[] path = rawPath.split("\\\\");
        return new AddDirectoryCommand(path, name);
    }
}
