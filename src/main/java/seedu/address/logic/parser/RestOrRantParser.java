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
import seedu.address.logic.commands.ClearMenuCommand;
import seedu.address.logic.commands.ClearOrderCommand;
import seedu.address.logic.commands.ClearTablesCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DailyCommand;
import seedu.address.logic.commands.DeleteFromMenuCommand;
import seedu.address.logic.commands.DeleteFromOrderCommand;
import seedu.address.logic.commands.EditPaxCommand;
import seedu.address.logic.commands.EditSeatsCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.MenuModeCommand;
import seedu.address.logic.commands.MonthlyCommand;
import seedu.address.logic.commands.RestaurantModeCommand;
import seedu.address.logic.commands.RevenueCommand;
import seedu.address.logic.commands.ServeCommand;
import seedu.address.logic.commands.SpaceForCommand;
import seedu.address.logic.commands.StatisticsModeCommand;
import seedu.address.logic.commands.TableModeCommand;
import seedu.address.logic.commands.YearlyCommand;
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

        switch (commandWord) {

        // General commands that work in all modes
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HistoryCommand.COMMAND_WORD: // Fallthrough
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
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

        case StatisticsModeCommand.COMMAND_WORD: //Fallthrough
        case StatisticsModeCommand.COMMAND_ALIAS:
            return new StatisticsModeCommand();

        // Commands that work in Restaurant Mode
        case AddTableCommand.COMMAND_WORD:
            if (mode != Mode.RESTAURANT_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new AddTableCommandParser().parse(arguments);

        case EditPaxCommand.COMMAND_WORD:
            if (mode != Mode.RESTAURANT_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new EditPaxCommandParser().parse(arguments);

        case ClearTablesCommand.COMMAND_WORD:
            if (mode != Mode.RESTAURANT_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new ClearTablesCommand();

        case EditSeatsCommand.COMMAND_WORD:
            if (mode != Mode.RESTAURANT_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new EditSeatsCommandParser().parse(arguments);

        case SpaceForCommand.COMMAND_WORD:
            if (mode != Mode.RESTAURANT_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new SpaceForCommandParser().parse(arguments);

        // Commands that work in Menu Mode
        case AddToMenuCommand.COMMAND_WORD:
            if (mode != Mode.MENU_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new AddToMenuCommandParser().parse(arguments);

        case DeleteFromMenuCommand.COMMAND_WORD:
            if (mode != Mode.MENU_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new DeleteFromMenuCommandParser().parse(arguments);

        case ClearMenuCommand.COMMAND_WORD:
            if (mode != Mode.MENU_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new ClearMenuCommand();

        // Commands that work in Table Mode
        case AddToOrderCommand.COMMAND_WORD:
            if (mode != Mode.TABLE_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new AddToOrderCommandParser().parse(arguments);

        case ClearOrderCommand.COMMAND_WORD:
            if (mode != Mode.TABLE_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new ClearOrderCommand();

        case DeleteFromOrderCommand.COMMAND_WORD:
            if (mode != Mode.TABLE_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new DeleteFromOrderCommandParser().parse(arguments);

        case ServeCommand.COMMAND_WORD: // Fallthrough
        case ServeCommand.COMMAND_ALIAS:
            if (mode != Mode.TABLE_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new ServeCommandParser().parse(arguments);

        case BillCommand.COMMAND_WORD: // Fallthrough
        case BillCommand.COMMAND_ALIAS:
            if (mode != Mode.TABLE_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new BillCommand();

        // Commands that work in Statistics Mode
        case RevenueCommand.COMMAND_WORD: // Fallthrough
        case RevenueCommand.COMMAND_ALIAS:
            if (mode != Mode.STATISTICS_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new RevenueCommandParser().parse(arguments);

        case DailyCommand.COMMAND_WORD: // Fallthrough
        case DailyCommand.COMMAND_ALIAS:
            if (mode != Mode.STATISTICS_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new DailyCommand();

        case MonthlyCommand.COMMAND_WORD: // Fallthrough
        case MonthlyCommand.COMMAND_ALIAS:
            if (mode != Mode.STATISTICS_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new MonthlyCommand();

        case YearlyCommand.COMMAND_WORD: // Fallthrough
        case YearlyCommand.COMMAND_ALIAS:
            if (mode != Mode.STATISTICS_MODE) {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }
            return new YearlyCommand();

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

        case "clear":
            if (mode == Mode.RESTAURANT_MODE) {
                return new ClearTablesCommand();
            } else if (mode == Mode.TABLE_MODE) {
                return new ClearOrderCommand();
            } else if (mode == Mode.MENU_MODE) {
                return new ClearMenuCommand();
            } else {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }

        case "del":
            if (mode == Mode.MENU_MODE) {
                return new DeleteFromMenuCommandParser().parse(arguments);
            } else if (mode == Mode.TABLE_MODE) {
                return new DeleteFromOrderCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_MODE);
            }

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);

        }

    }

}
