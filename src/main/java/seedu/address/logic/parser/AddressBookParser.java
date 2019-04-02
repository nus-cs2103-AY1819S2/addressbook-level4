package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_ONLY_GO_TO_MODE_COMMANDS;
import static seedu.address.commons.core.Messages.MESSAGE_ONLY_TASK_OR_DATE_COMMANDS;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CalendarWindow;
import seedu.address.ui.MainWindow;

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
        final boolean checkBothConditions = true;
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        //Commands that should not run in both GoTo mode and Calendar Window
        case AddCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new HistoryCommand();

        case UndoCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new RedoCommand();

        case StatsCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new StatsCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new SortCommandParser().parse(arguments);

        case CopyCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new CopyCommandParser().parse(arguments);

        case OpenCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new OpenCommandParser().parse(arguments);

        case SaveCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new SaveCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new ImportCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            checkSpecialCondition(checkBothConditions);
            return new ExportCommandParser().parse(arguments);

        //Commands that should ONLY run in GoTo mode but not in Calendar Window
        case GoToCommand.COMMAND_WORD:
            checkSpecialCondition(!checkBothConditions);
            return new GoToCommandParser().parse(arguments);

        case BackCommand.COMMAND_WORD:
            checkSpecialCondition(!checkBothConditions);
            return new BackCommand();

        case RecordAddCommand.COMMAND_WORD:
            checkSpecialCondition(!checkBothConditions);
            return new RecordAddCommandParser().parse(arguments);

        case RecordEditCommand.COMMAND_WORD:
            checkSpecialCondition(!checkBothConditions);
            return new RecordEditCommandParser().parse(arguments);

        case RecordDeleteCommand.COMMAND_WORD:
            checkSpecialCondition(!checkBothConditions);
            return new RecordDeleteCommandParser().parse(arguments);

        case TeethEditCommand.COMMAND_WORD:
            checkSpecialCondition(!checkBothConditions);
            return new TeethEditCommandParser().parse(arguments);

        //Commands that should run in ALL modes and popups
        case TaskAddCommand.COMMAND_WORD:
            return new TaskAddCommandParser().parse(arguments);

        case TaskCalendarCommand.COMMAND_WORD:
            return new TaskCalendarCommandParser().parse(arguments);

        case TaskEditCommand.COMMAND_WORD:
            return new TaskEditCommandParser().parse(arguments);

        case TaskSortCommand.COMMAND_WORD:
            return new TaskSortCommandParser().parse(arguments);

        case TaskDeleteCommand.COMMAND_WORD:
            return new TaskDeleteCommandParser().parse(arguments);

        case TaskCopyCommand.COMMAND_WORD:
            return new TaskCopyCommandParser().parse(arguments);

        case TaskListCommand.COMMAND_WORD:
            return new TaskListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitAnywayCommand.COMMAND_WORD:
            return new ExitAnywayCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Checks if the Main Window is currently in Goto Mode or if a command is being ran from Calendar Window.
     * Feedback given to user if commands are being ran in the wrong modes or areas of the GUI.
     */
    public void checkSpecialCondition(boolean checkBothConditions) throws ParseException {
        if (CalendarWindow.isRunningCommand()) {
            throw new ParseException(MESSAGE_ONLY_TASK_OR_DATE_COMMANDS);
        }
        if (MainWindow.isGoToMode() && checkBothConditions) {
            throw new ParseException(MESSAGE_ONLY_GO_TO_MODE_COMMANDS);
        }
    }

}
