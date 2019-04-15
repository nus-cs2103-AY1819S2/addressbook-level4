package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.management.AddCardCommand;
import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.commands.management.ChangeThemeCommand;
import seedu.address.logic.commands.management.DeleteCardCommand;
import seedu.address.logic.commands.management.DeleteLessonCommand;
import seedu.address.logic.commands.management.EditLessonCommand;
import seedu.address.logic.commands.management.ExitCommand;
import seedu.address.logic.commands.management.HelpCommand;
import seedu.address.logic.commands.management.HistoryCommand;
import seedu.address.logic.commands.management.ListCardsCommand;
import seedu.address.logic.commands.management.ListLessonsCommand;
import seedu.address.logic.commands.management.QuitLessonCommand;
import seedu.address.logic.commands.management.ReloadLessonsCommand;
import seedu.address.logic.commands.management.SetLessonTestValuesCommand;
import seedu.address.logic.commands.management.StartCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ManagementModeParser implements Parser<Command> {
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
    public Command parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        //        case FindCommand.COMMAND_WORD:
        //            return new FindCommandParser().parse(arguments);
        // TODO use parser here
        case StartCommand.COMMAND_WORD:
            return new StartCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonParser().parse(arguments);

        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonParser().parse(arguments);

        case ListLessonsCommand.COMMAND_WORD:
            return new ListLessonsCommand();

        case EditLessonCommand.COMMAND_WORD:
            return new OpenLessonParser().parse(arguments);

        case QuitLessonCommand.COMMAND_WORD:
            return new QuitLessonCommand();

        case SetLessonTestValuesCommand.COMMAND_WORD:
            return new SetTestParser().parse(arguments);

        case AddCardCommand.COMMAND_WORD:
            return new AddCardParser().parse(arguments);

        case DeleteCardCommand.COMMAND_WORD:
            return new DeleteCardParser().parse(arguments);

        case ListCardsCommand.COMMAND_WORD:
            return new ListCardsCommand();

        case ReloadLessonsCommand.COMMAND_WORD:
            return new ReloadLessonsCommand();

        case ChangeThemeCommand.COMMAND_WORD:
            return new ChangeThemeCommand();

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
