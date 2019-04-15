package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingModel;
import seedu.hms.model.HotelManagementSystem;

/**
 * Clears the Hotel Management System of Service Types.
 */
public class ClearServiceTypeCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "cst";
    public static final String COMMAND_WORD = "clear-service-type";
    public static final String MESSAGE_SUCCESS = "Service types and bookings have been cleared!";


    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) {
        requireNonNull(model);
        model.setClearServiceTypes(new HotelManagementSystem());
        model.setClearBooking(new HotelManagementSystem());
        model.commitHotelManagementSystem();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
