package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.travel.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.travel.logic.commands.AddCommand;
import seedu.travel.logic.commands.ClearCommand;
import seedu.travel.logic.commands.Command;
import seedu.travel.logic.commands.DeleteCommand;
import seedu.travel.logic.commands.DeleteMultipleCommand;
import seedu.travel.logic.commands.EditCommand;
import seedu.travel.logic.commands.ExitCommand;
import seedu.travel.logic.commands.GenerateCommand;
import seedu.travel.logic.commands.HelpCommand;
import seedu.travel.logic.commands.HistoryCommand;
import seedu.travel.logic.commands.ListCommand;
import seedu.travel.logic.commands.RedoCommand;
import seedu.travel.logic.commands.SearchCommand;
import seedu.travel.logic.commands.SearchCountryCommand;
import seedu.travel.logic.commands.SearchRatingCommand;
import seedu.travel.logic.commands.SearchTagsCommand;
import seedu.travel.logic.commands.SearchYearCommand;
import seedu.travel.logic.commands.SelectCommand;
import seedu.travel.logic.commands.UndoCommand;
import seedu.travel.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class TravelBuddyParser {

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
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case DeleteMultipleCommand.COMMAND_WORD:
            return new DeleteMultipleCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case SearchCommand.COMMAND_WORD:
        case SearchCommand.COMMAND_ALIAS:
            return new SearchCommandParser().parse(arguments);

        case SearchRatingCommand.COMMAND_WORD:
            return new SearchRatingCommandParser().parse(arguments);

        case SearchTagsCommand.COMMAND_WORD:
            return new SearchTagsCommandParser().parse(arguments);

        case SearchCountryCommand.COMMAND_WORD:
            return new SearchCountryCommandParser().parse(arguments);

        case SearchYearCommand.COMMAND_WORD:
            return new SearchYearCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case GenerateCommand.COMMAND_WORD:
        case GenerateCommand.COMMAND_ALIAS:
            return new GenerateCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
