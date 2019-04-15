package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingModel;
import seedu.hms.model.HotelManagementSystem;

/**
 * Clears the Hotel Management System of Bookings.
 */
public class ClearBookingCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "cb";
    public static final String COMMAND_WORD = "clear-bookings";
    public static final String MESSAGE_SUCCESS = "Bookings have been cleared!";


    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) {
        requireNonNull(model);
        model.setClearBooking(new HotelManagementSystem());
        model.commitHotelManagementSystem();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
