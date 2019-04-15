package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.Booking;

/**
 * Deletes a customer identified using it's displayed index from the hms book.
 */
public class DeleteBookingCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "db";
    public static final String COMMAND_WORD = "delete-booking";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the booking identified by the index number used in the displayed customer list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BOOKING_SUCCESS = "Deleted Booking: %1$s";

    private final Index targetIndex;

    public DeleteBookingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Booking> lastShownList = model.getFilteredBookingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
        }

        Booking bookingToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBooking(targetIndex.getZeroBased());
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_DELETE_BOOKING_SUCCESS, bookingToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteBookingCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteBookingCommand) other).targetIndex)); // state check
    }
}
