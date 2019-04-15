package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.Model;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.exceptions.RoomFullException;
import seedu.hms.model.reservation.roomType.exceptions.RoomUnavailableException;

/**
 * Adds a reservation to the hms book.
 */
public class AddReservationCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "ar";
    public static final String COMMAND_WORD = "add-reservation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reservation to the hotel management system.\n"
        + "Parameters: "
        + PREFIX_ROOM + "ROOM NAME "
        + PREFIX_DATES + "DATES(DD/MM/YYYY - DD/MM/YYYY) "
        + PREFIX_PAYER + "PAYER INDEX "
        + "[" + PREFIX_CUSTOMERS + "CUSTOMER INDEX]... "
        + "[" + PREFIX_COMMENT + "COMMENT]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_ROOM + "SINGLE ROOM "
        + PREFIX_DATES + "05/05/2019 - 07/05/2019 "
        + PREFIX_PAYER + "2 "
        + PREFIX_CUSTOMERS + "1 "
        + PREFIX_CUSTOMERS + "3 "
        + PREFIX_COMMENT + "Need one more pillow.\n";

    public static final String MESSAGE_SUCCESS = "New reservation added: %1$s";
    public static final String MESSAGE_ROOM_FULL = "The room has been booked fully during your requested date range";
    public static final String MESSAGE_ROOM_UNAVAILABLE = "The room is not available during your requested date range";

    private final Reservation toAdd;

    /**
     * Creates an AddReservationCommand to add the specified {@code Reservation}
     */
    public AddReservationCommand(Reservation reservation) {
        requireNonNull(reservation);
        toAdd = reservation;
    }

    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.addReservation(toAdd);
            model.updateFilteredReservationList(Model.PREDICATE_SHOW_ALL_RESERVATIONS);
            model.setSelectedReservation(toAdd);
        } catch (RoomUnavailableException e) {
            return new CommandResult(MESSAGE_ROOM_UNAVAILABLE);
        } catch (RoomFullException e) {
            return new CommandResult(MESSAGE_ROOM_FULL);
        }
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddReservationCommand // instanceof handles nulls
            && toAdd.equals(((AddReservationCommand) other).toAdd));
    }
}
