package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddBookingCommand;
import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.commands.ClearCustomerCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCustomerCommand;
import seedu.address.logic.commands.EditBookingCommand;
import seedu.address.logic.commands.EditCustomerCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.GenerateBillCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCustomerCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCustomerCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.BookingModel;
import seedu.address.model.CustomerModel;

/**
 * Parses user input.
 */
public class HotelManagementSystemParser {

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
    public Command parseCommand(String userInput, CustomerModel customerModel, BookingModel bookingModel)
        throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCustomerCommand.COMMAND_WORD:
        case AddCustomerCommand.COMMAND_ALIAS:
            return new AddCustomerCommandParser().parse(arguments);

        case AddBookingCommand.COMMAND_WORD:
        case AddBookingCommand.COMMAND_ALIAS:
            return new AddBookingCommandParser().parse(arguments, customerModel);

        case EditBookingCommand.COMMAND_WORD:
        case EditBookingCommand.COMMAND_ALIAS:
            return new EditBookingCommandParser().parse(arguments, customerModel);

        case EditCustomerCommand.COMMAND_WORD:
        case EditCustomerCommand.COMMAND_ALIAS:
            return new EditCustomerCommandParser().parse(arguments);

        case SelectCustomerCommand.COMMAND_WORD:
        case SelectCustomerCommand.COMMAND_ALIAS:
            return new SelectCustomerCommandParser().parse(arguments);

        case DeleteCustomerCommand.COMMAND_WORD:
        case DeleteCustomerCommand.COMMAND_ALIAS:
            return new DeleteCustomerCommandParser().parse(arguments);

        case ClearCustomerCommand.COMMAND_WORD:
        case ClearCustomerCommand.COMMAND_ALIAS:
            return new ClearCustomerCommand();

        case FindNameCommand.COMMAND_WORD:
        case FindNameCommand.COMMAND_ALIAS:
            return new FindNameCommandParser().parse(arguments);

        case GenerateBillCommand.COMMAND_WORD:
        case GenerateBillCommand.COMMAND_ALIAS:
            return new GenerateBillCommandParser().parse(arguments);


        case ListCustomerCommand.COMMAND_WORD:
        case ListCustomerCommand.COMMAND_ALIAS:
            return new ListCustomerCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
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
