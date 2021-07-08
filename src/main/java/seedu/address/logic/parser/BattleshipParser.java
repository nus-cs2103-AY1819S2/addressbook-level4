package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AttackCommand;
import seedu.address.logic.commands.BeginCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InitialiseMapCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTagsCommand;
import seedu.address.logic.commands.PutShipCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.StatsCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class BattleshipParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ListTagsCommand.COMMAND_WORD:
            return new ListTagsCommand();

        case StatsCommand.COMMAND_WORD:
            return new StatsCommand();

        case SaveCommand.COMMAND_WORD:
            return new SaveCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case InitialiseMapCommand.COMMAND_WORD:
        case InitialiseMapCommand.COMMAND_ALIAS:
        case InitialiseMapCommand.COMMAND_ALIAS1:
            return new InitialiseMapCommandParser().parse(arguments);

        case PutShipCommand.COMMAND_WORD:
        case PutShipCommand.COMMAND_ALIAS:
            return new PutShipCommandParser().parse(arguments);

        case AttackCommand.COMMAND_WORD:
        case AttackCommand.COMMAND_ALIAS1:
        case AttackCommand.COMMAND_ALIAS2:
        case AttackCommand.COMMAND_ALIAS3:
            return new AttackCommandParser().parse(arguments);

        case BeginCommand.COMMAND_WORD:
        case BeginCommand.COMMAND_ALIAS1:
            return new BeginCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
