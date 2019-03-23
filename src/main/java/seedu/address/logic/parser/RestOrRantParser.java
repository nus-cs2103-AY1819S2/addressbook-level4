package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.Mode;
import seedu.address.logic.commands.AddTableCommand;
import seedu.address.logic.commands.AddToMenuCommand;
import seedu.address.logic.commands.AddToOrderCommand;
import seedu.address.logic.commands.BillCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearOrderCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.MenuModeCommand;
import seedu.address.logic.commands.RestaurantModeCommand;
import seedu.address.logic.commands.TableModeCommand;
import seedu.address.logic.commands.UpdateTableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RestOrRantParser {

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
    public Command parseCommand(Mode mode, String userInput) throws ParseException, CommandException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // General commands that work in all modes

        switch (commandWord) {
            
        case HelpCommand.COMMAND_WORD: // Fallthrough 
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case HistoryCommand.COMMAND_WORD: // Fallthrough
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD: // Fallthrough
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case RestaurantModeCommand.COMMAND_WORD: // Fallthrough
        case RestaurantModeCommand.COMMAND_ALIAS:
            return new RestaurantModeCommand();

        case MenuModeCommand.COMMAND_WORD: // Fallthrough
        case MenuModeCommand.COMMAND_ALIAS:
            return new MenuModeCommand();

        case TableModeCommand.COMMAND_WORD: // Fallthrough
        case TableModeCommand.COMMAND_ALIAS:
            return new TableModeCommandParser().parse(arguments);

        // Commands that work in Restaurant Mode
        case AddTableCommand.COMMAND_WORD:
            if (mode != Mode.RESTAURANT_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new AddTableCommandParser().parse(arguments);

        case UpdateTableCommand.COMMAND_WORD:
            if (mode != Mode.RESTAURANT_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new UpdateTableCommandParser().parse(arguments);

        // Commands that work in Menu Mode
        case AddToMenuCommand.COMMAND_WORD:
            if (mode != Mode.MENU_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new AddToMenuCommandParser().parse(arguments);

        // Commands that work in Table Mode
        case AddToOrderCommand.COMMAND_WORD:
            if (mode != Mode.TABLE_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new AddToOrderCommandParser().parse(arguments);

        case ClearOrderCommand.COMMAND_WORD: // Fallthrough
        case ClearOrderCommand.COMMAND_ALIAS:
            if (mode != Mode.TABLE_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new ClearOrderCommand();

        case BillCommand.COMMAND_WORD: // Fallthrough
        case BillCommand.COMMAND_ALIAS:
            if (mode != Mode.TABLE_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new BillCommand();

        // General alias commands that do different functions in different modes
        case "add":
            if (mode == Mode.RESTAURANT_MODE) {
                return new AddTableCommandParser().parse(arguments);
            } else if (mode == Mode.MENU_MODE) {
                return new AddToMenuCommandParser().parse(arguments);
            } else if (mode == Mode.TABLE_MODE) {
                return new AddToOrderCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);

        }

    }

}
