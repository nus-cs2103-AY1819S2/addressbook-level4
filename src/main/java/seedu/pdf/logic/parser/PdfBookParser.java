package seedu.pdf.logic.parser;

import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pdf.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.pdf.logic.commands.AddCommand;
import seedu.pdf.logic.commands.ClearCommand;
import seedu.pdf.logic.commands.Command;
import seedu.pdf.logic.commands.DeadlineCommand;
import seedu.pdf.logic.commands.DecryptCommand;
import seedu.pdf.logic.commands.DeleteCommand;
import seedu.pdf.logic.commands.EncryptCommand;
import seedu.pdf.logic.commands.ExitCommand;
import seedu.pdf.logic.commands.FilterCommand;
import seedu.pdf.logic.commands.FindCommand;
import seedu.pdf.logic.commands.HelpCommand;
import seedu.pdf.logic.commands.HistoryCommand;
import seedu.pdf.logic.commands.ListCommand;
import seedu.pdf.logic.commands.MergeCommand;
import seedu.pdf.logic.commands.MoveCommand;
import seedu.pdf.logic.commands.OpenCommand;
import seedu.pdf.logic.commands.RenameCommand;
import seedu.pdf.logic.commands.SelectCommand;
import seedu.pdf.logic.commands.SortCommand;
import seedu.pdf.logic.commands.TagCommand;
import seedu.pdf.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class PdfBookParser {

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

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case DeadlineCommand.COMMAND_WORD:
            return new DeadlineCommandParser().parse(arguments);

        case DecryptCommand.COMMAND_WORD:
            return new DecryptCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case RenameCommand.COMMAND_WORD:
            return new RenameCommandParser().parse(arguments);

        case EncryptCommand.COMMAND_WORD:
            return new EncryptCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case MergeCommand.COMMAND_WORD:
            return new MergeCommandParser().parse(arguments);

        case MoveCommand.COMMAND_WORD:
            return new MoveCommandParser().parse(arguments);

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
