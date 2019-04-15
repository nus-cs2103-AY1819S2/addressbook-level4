package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * Deletes a room type identified using it's displayed index from the hms book.
 */
public class DeleteRoomTypeCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "drt";
    public static final String COMMAND_WORD = "delete-room-type";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the room type identified by the index number used in the displayed room type list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ROOM_TYPE_SUCCESS = "Deleted Room Type: %1$s";

    private final Index targetIndex;

    public DeleteRoomTypeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<RoomType> lastShownList = model.getRoomTypeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ROOM_TYPE_DISPLAYED_INDEX);
        }

        RoomType roomType = lastShownList.get(targetIndex.getZeroBased());
        model.deleteRoomType(roomType);
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_DELETE_ROOM_TYPE_SUCCESS, roomType));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteRoomTypeCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteRoomTypeCommand) other).targetIndex)); // state check
    }
}
