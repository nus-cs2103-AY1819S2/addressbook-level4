package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.reservation.Reservation;

/**
 * Deletes a customer identified using it's displayed index from the hms book.
 */
public class DeleteReservationCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "dr";
    public static final String COMMAND_WORD = "delete-reservation";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the reservation identified by the index number used in the displayed customer list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RESERVATION_SUCCESS = "Deleted Reservation: %1$s";

    private final Index targetIndex;

    public DeleteReservationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteReservation(targetIndex.getZeroBased());
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_DELETE_RESERVATION_SUCCESS, reservationToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteReservationCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteReservationCommand) other).targetIndex)); // state check
    }
}
