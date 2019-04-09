package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickdocs.logic.commands.AlarmCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * A parser to parse user input to AlarmCommand
 */
public class AlarmCommandParser implements Parser<AlarmCommand> {

    private static final Pattern AlarmCommand_Argument_Format =
            Pattern.compile("(?<rawPath>\\S+)(?:\\s+)(?<threshold>\\d+)");

    /**
     * To parse user input to AlarmCommand
     * @param args the user input
     * @return A corresponding AlarmCommand
     * @throws ParseException
     */
    public AlarmCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AlarmCommand.MESSAGE_USAGE));
        }
        final Matcher matcher = AlarmCommand_Argument_Format.matcher(trimmedArgs);
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AlarmCommand.MESSAGE_USAGE));
        }
        String rawPath = matcher.group("rawPath");
        String threshold = matcher.group("threshold");
        String[] path = rawPath.split("\\\\");
        return new AlarmCommand(path, Integer.parseInt(threshold));
    }
}
