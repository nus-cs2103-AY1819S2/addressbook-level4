package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.ReservationModel;

/**
 * Clears the Hotel Management System of Bookings.
 */
public class ClearReservationCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "clear-r";
    public static final String COMMAND_WORD = "clear-reservations";
    public static final String MESSAGE_SUCCESS = "Reservations have been cleared!";


    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) {
        requireNonNull(model);
        model.setClearReservation(new HotelManagementSystem());
        model.commitHotelManagementSystem();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}