package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.reservation.Reservation;

/**
 * Adds a reservation to the hms book.
 */
public class AddReservationCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "ar";
    public static final String COMMAND_WORD = "add-reservation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reservation to the hotel management system. "
        + "Parameters: "
        + PREFIX_ROOM + "ROOM NAME "
        + PREFIX_DATES + "DATES(DD/MM/YY - DD/MM/YY) "
        + PREFIX_PAYER + "PAYER INDEX "
        + "[" + PREFIX_CUSTOMERS + "CUSTOMER INDEX]... "
        + "[" + PREFIX_COMMENT + "COMMENT]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_ROOM + "SINGLE "
        + PREFIX_DATES + "05/05/19 - 07/05/19 "
        + PREFIX_PAYER + "2 "
        + PREFIX_CUSTOMERS + "1,3 "
        + PREFIX_COMMENT + "Need one more pillow.\n";

    public static final String MESSAGE_SUCCESS = "New reservation added: %1$s";
    public static final String MESSAGE_ROOM_FULL = "The room has been booked fully during your requested hours";
    public static final String MESSAGE_ROOM_UNAVAILABLE = "The room is not available during your requested hours";

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
        model.addReservation(toAdd);
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
