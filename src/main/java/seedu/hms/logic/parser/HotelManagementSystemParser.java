package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.hms.logic.commands.AddBookingCommand;
import seedu.hms.logic.commands.AddCustomerCommand;
import seedu.hms.logic.commands.AddReservationCommand;
import seedu.hms.logic.commands.AddRoomTypeCommand;
import seedu.hms.logic.commands.AddServiceTypeCommand;
import seedu.hms.logic.commands.ClearBookingCommand;
import seedu.hms.logic.commands.ClearHotelManagementSystemCommand;
import seedu.hms.logic.commands.ClearReservationCommand;
import seedu.hms.logic.commands.ClearRoomTypeCommand;
import seedu.hms.logic.commands.ClearServiceTypeCommand;
import seedu.hms.logic.commands.Command;
import seedu.hms.logic.commands.DeleteBookingCommand;
import seedu.hms.logic.commands.DeleteCustomerCommand;
import seedu.hms.logic.commands.DeleteReservationCommand;
import seedu.hms.logic.commands.DeleteRoomTypeCommand;
import seedu.hms.logic.commands.DeleteServiceTypeCommand;
import seedu.hms.logic.commands.EditBookingCommand;
import seedu.hms.logic.commands.EditCustomerCommand;
import seedu.hms.logic.commands.EditReservationCommand;
import seedu.hms.logic.commands.EditRoomTypeCommand;
import seedu.hms.logic.commands.EditServiceTypeCommand;
import seedu.hms.logic.commands.ExitCommand;
import seedu.hms.logic.commands.FindBookingCommand;
import seedu.hms.logic.commands.FindNameCommand;
import seedu.hms.logic.commands.FindReservationCommand;
import seedu.hms.logic.commands.GenerateBillForBookingCommand;
import seedu.hms.logic.commands.GenerateBillForCustomerCommand;
import seedu.hms.logic.commands.GenerateBillForReservationCommand;
import seedu.hms.logic.commands.HelpCommand;
import seedu.hms.logic.commands.HistoryCommand;
import seedu.hms.logic.commands.ListBookingCommand;
import seedu.hms.logic.commands.ListCustomerCommand;
import seedu.hms.logic.commands.ListReservationCommand;
import seedu.hms.logic.commands.RedoCommand;
import seedu.hms.logic.commands.SelectCustomerCommand;
import seedu.hms.logic.commands.ShowStatsCommand;
import seedu.hms.logic.commands.SwitchTabCommand;
import seedu.hms.logic.commands.UndoCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BillModel;
import seedu.hms.model.BookingModel;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.ReservationModel;

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
    public Command parseCommand(String userInput, CustomerModel customerModel, BookingModel bookingModel,
                                ReservationModel reservationModel, BillModel billModel)
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
            return new AddBookingCommandParser().parse(arguments, customerModel, bookingModel);

        case AddServiceTypeCommand.COMMAND_WORD:
        case AddServiceTypeCommand.COMMAND_ALIAS:
            return new AddServiceTypeCommandParser().parse(arguments);

        case AddRoomTypeCommand.COMMAND_WORD:
        case AddRoomTypeCommand.COMMAND_ALIAS:
            return new AddRoomTypeCommandParser().parse(arguments);

        case AddReservationCommand.COMMAND_WORD:
        case AddReservationCommand.COMMAND_ALIAS:
            return new AddReservationCommandParser().parse(arguments, customerModel, reservationModel);

        case EditBookingCommand.COMMAND_WORD:
        case EditBookingCommand.COMMAND_ALIAS:
            return new EditBookingCommandParser().parse(arguments, customerModel, bookingModel);

        case EditServiceTypeCommand.COMMAND_WORD:
        case EditServiceTypeCommand.COMMAND_ALIAS:
            return new EditServiceTypeCommandParser().parse(arguments);

        case EditRoomTypeCommand.COMMAND_WORD:
        case EditRoomTypeCommand.COMMAND_ALIAS:
            return new EditRoomTypeCommandParser().parse(arguments);

        case EditCustomerCommand.COMMAND_WORD:
        case EditCustomerCommand.COMMAND_ALIAS:
            return new EditCustomerCommandParser().parse(arguments);

        case EditReservationCommand.COMMAND_WORD:
        case EditReservationCommand.COMMAND_ALIAS:
            return new EditReservationCommandParser().parse(arguments, customerModel, reservationModel);

        case SelectCustomerCommand.COMMAND_WORD:
        case SelectCustomerCommand.COMMAND_ALIAS:
            return new SelectCustomerCommandParser().parse(arguments);

        case DeleteCustomerCommand.COMMAND_WORD:
        case DeleteCustomerCommand.COMMAND_ALIAS:
            return new DeleteCustomerCommandParser().parse(arguments);

        case DeleteBookingCommand.COMMAND_WORD:
        case DeleteBookingCommand.COMMAND_ALIAS:
            return new DeleteBookingCommandParser().parse(arguments);

        case DeleteServiceTypeCommand.COMMAND_WORD:
        case DeleteServiceTypeCommand.COMMAND_ALIAS:
            return new DeleteServiceTypeCommandParser().parse(arguments);

        case DeleteRoomTypeCommand.COMMAND_WORD:
        case DeleteRoomTypeCommand.COMMAND_ALIAS:
            return new DeleteRoomTypeCommandParser().parse(arguments);

        case ClearHotelManagementSystemCommand.COMMAND_WORD:
        case ClearHotelManagementSystemCommand.COMMAND_ALIAS:
            return new ClearHotelManagementSystemCommand();

        case DeleteReservationCommand.COMMAND_WORD:
        case DeleteReservationCommand.COMMAND_ALIAS:
            return new DeleteReservationCommandParser().parse(arguments);

        case ClearBookingCommand.COMMAND_WORD:
        case ClearBookingCommand.COMMAND_ALIAS:
            return new ClearBookingCommand();

        case ClearReservationCommand.COMMAND_WORD:
        case ClearReservationCommand.COMMAND_ALIAS:
            return new ClearReservationCommand();

        case ClearServiceTypeCommand.COMMAND_WORD:
        case ClearServiceTypeCommand.COMMAND_ALIAS:
            return new ClearServiceTypeCommand();

        case ClearRoomTypeCommand.COMMAND_WORD:
        case ClearRoomTypeCommand.COMMAND_ALIAS:
            return new ClearRoomTypeCommand();

        case FindNameCommand.COMMAND_WORD:
        case FindNameCommand.COMMAND_ALIAS:
            return new FindNameCommandParser().parse(arguments);

        case FindBookingCommand.COMMAND_WORD:
        case FindBookingCommand.COMMAND_ALIAS:
            return new FindBookingCommandParser().parse(arguments, bookingModel);

        case FindReservationCommand.COMMAND_WORD:
        case FindReservationCommand.COMMAND_ALIAS:
            return new FindReservationCommandParser().parse(arguments, reservationModel);

        case GenerateBillForBookingCommand.COMMAND_WORD:
        case GenerateBillForBookingCommand.COMMAND_ALIAS:
            return new GenerateBillForBookingCommandParser().parse(arguments, customerModel, billModel, bookingModel);

        case GenerateBillForCustomerCommand.COMMAND_WORD:
        case GenerateBillForCustomerCommand.COMMAND_ALIAS:
            return new GenerateBillForCustomerCommandParser().parse(arguments, customerModel, billModel);

        case SwitchTabCommand.COMMAND_WORD:
        case SwitchTabCommand.COMMAND_ALIAS:
            return new SwitchTabCommandParser().parse(arguments);

        case GenerateBillForReservationCommand.COMMAND_WORD:
        case GenerateBillForReservationCommand.COMMAND_ALIAS:
            return new GenerateBillForReservationCommandParser().parse(arguments, customerModel, billModel,
                reservationModel);

        case ShowStatsCommand.COMMAND_WORD:
        case ShowStatsCommand.COMMAND_ALIAS:
            return new ShowStatsCommandParser().parse(arguments);

        case ListCustomerCommand.COMMAND_WORD:
        case ListCustomerCommand.COMMAND_ALIAS:
            return new ListCustomerCommand();

        case ListBookingCommand.COMMAND_WORD:
        case ListBookingCommand.COMMAND_ALIAS:
            return new ListBookingCommand();

        case ListReservationCommand.COMMAND_WORD:
        case ListReservationCommand.COMMAND_ALIAS:
            return new ListReservationCommand();

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
