package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddCpnyCommand;
import seedu.address.logic.commands.AnalyzeCommand;
import seedu.address.logic.commands.AverageCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CountCommand;
import seedu.address.logic.commands.DelFavoriteCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCpnyCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCpnyCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCpnyCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListCpnyCommand;
import seedu.address.logic.commands.ListFavoritesCommand;
import seedu.address.logic.commands.MedianCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class AddressBookParser {

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditCpnyCommand.COMMAND_WORD:
            return new EditCpnyCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteCpnyCommand.COMMAND_WORD:
            return new DeleteCpnyCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindCpnyCommand.COMMAND_WORD:
            return new FindCpnyCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListCpnyCommand.COMMAND_WORD:
            return new ListCpnyCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case FavoriteCommand.COMMAND_WORD:
            return new FavoriteCommandParser().parse(arguments);

        case DelFavoriteCommand.COMMAND_WORD:
            return new DelFavoriteCommandParser().parse(arguments);

        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case ListFavoritesCommand.COMMAND_WORD:
            return new ListFavoritesCommand();

        case AddCpnyCommand.COMMAND_WORD:
            return new AddCpnyCommandParser().parse(arguments);

        case CountCommand.COMMAND_WORD:
            return new CountCommand();

        case AverageCommand.COMMAND_WORD:
            return new AverageCommand();

        case AnalyzeCommand.COMMAND_WORD:
            return new AnalyzeCommand();

        case MedianCommand.COMMAND_WORD:
            return new MedianCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
