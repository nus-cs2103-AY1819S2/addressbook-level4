package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.ReservationModel;

/**
 * Clears the Hotel Management System of Room Types.
 */
public class ClearRoomTypeCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "crt";
    public static final String COMMAND_WORD = "clear-room-type";
    public static final String MESSAGE_SUCCESS = "Room types and reservations have been cleared!";


    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) {
        requireNonNull(model);
        model.setClearRoomTypes(new HotelManagementSystem());
        model.setClearReservation(new HotelManagementSystem());
        model.commitHotelManagementSystem();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
