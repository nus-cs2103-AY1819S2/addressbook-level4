package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchiveClearCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ArchiveListCommand;
import seedu.address.logic.commands.ArchiveSearchCommand;
import seedu.address.logic.commands.ArchiveSelectCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.commands.PinSelectCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnpinCommand;
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
     * Used to deal with arguments commands
     *
     * @param arguments arguments of user input string
     * @throws ParseException if the user input parameters that contains keywords
     */
    public void dealArguments(String arguments) throws ParseException {

        String[] commandList = {"help", "add", "list", "select", "edit", "delete", "clear", "pin", "unpin", "pinselect",
                                "archive", "archivelist", "archiveselect", "unarchive", "archiveclear", "archivesearch",
                                "history", "redo", "undo", "exit", "search"};


        ArrayList<String> command = new ArrayList<>(Arrays.asList(commandList));
        Iterator<String> iterator = command.iterator();

        StringTokenizer st = new StringTokenizer(arguments);

        while (st.hasMoreTokens()) {
            String t = st.nextToken();
            while (iterator.hasNext()) {
                String s = iterator.next();
                if (s.equals(t)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
                }
            }
        }
    }

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

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case PinCommand.COMMAND_WORD:
            return new PinCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            dealArguments(arguments);
            return new ClearCommand();

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            dealArguments(arguments);
            return new ListCommand();

        case ArchiveCommand.COMMAND_WORD:
            return new ArchiveCommandParser().parse(arguments);

        case ArchiveListCommand.COMMAND_WORD:
            dealArguments(arguments);
            return new ArchiveListCommand();

        case ArchiveSelectCommand.COMMAND_WORD:
            return new ArchiveSelectCommandParser().parse(arguments);

        case ArchiveClearCommand.COMMAND_WORD:
            dealArguments(arguments);
            return new ArchiveClearCommand();

        case ArchiveSearchCommand.COMMAND_WORD:
            return new ArchiveSearchCommandParser().parse(arguments);

        case UnarchiveCommand.COMMAND_WORD:
            return new UnarchiveCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            dealArguments(arguments);
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            dealArguments(arguments);
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            dealArguments(arguments);
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            dealArguments(arguments);
            return new UndoCommand();

        case UnpinCommand.COMMAND_WORD:
            return new UnpinCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            dealArguments(arguments);
            return new RedoCommand();

        case PinSelectCommand.COMMAND_WORD:
            return new PinSelectCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
