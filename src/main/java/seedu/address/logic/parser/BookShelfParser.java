package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddBookCommand;
import seedu.address.logic.commands.AddReviewCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteBookCommand;
import seedu.address.logic.commands.DeleteReviewCommand;
import seedu.address.logic.commands.EditBookCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListAllReviewsCommand;
import seedu.address.logic.commands.ListBookCommand;
import seedu.address.logic.commands.ListReviewCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectReviewCommand;
import seedu.address.logic.commands.SortBookCommand;
import seedu.address.logic.commands.SummaryCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class BookShelfParser {

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

        case AddBookCommand.COMMAND_WORD:
            return new AddBookCommandParser().parse(arguments);

        case AddReviewCommand.COMMAND_WORD:
            return new AddReviewCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case DeleteBookCommand.COMMAND_WORD:
            return new DeleteBookCommandParser().parse(arguments);

        case DeleteReviewCommand.COMMAND_WORD:
            return new DeleteReviewCommandParser().parse(arguments);

        case EditBookCommand.COMMAND_WORD:
            return new EditBookCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ListBookCommand.COMMAND_WORD:
            return new ListBookCommandParser().parse(arguments);

        case ListAllReviewsCommand.COMMAND_WORD:
            return new ListAllReviewsCommand();

        case ListReviewCommand.COMMAND_WORD:
            return new ListReviewCommandParser().parse(arguments);

        case SelectReviewCommand.COMMAND_WORD:
            return new SelectReviewCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case SummaryCommand.COMMAND_WORD:
            return new SummaryCommand();

        case SortBookCommand.COMMAND_WORD:
            return new SortBookCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
