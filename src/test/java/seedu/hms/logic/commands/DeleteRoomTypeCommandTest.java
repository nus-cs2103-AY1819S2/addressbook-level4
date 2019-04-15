package seedu.hms.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandFailure;
import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandSuccess;
import static seedu.hms.logic.commands.CommandTestUtil.showReservationForPayer;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;
import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_ROOM_TYPE;
import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_ROOM_TYPE;

import org.junit.Test;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.reservation.roomType.RoomType;

public class DeleteRoomTypeCommandTest {

    private ReservationModel model =
        new ReservationManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        RoomType roomTypeToDelete =
            model.getRoomTypeList().get(INDEX_FIRST_ROOM_TYPE.getZeroBased());
        DeleteRoomTypeCommand deleteRoomTypeCommand = new DeleteRoomTypeCommand(INDEX_FIRST_ROOM_TYPE);

        String expectedMessage = String.format(DeleteRoomTypeCommand.MESSAGE_DELETE_ROOM_TYPE_SUCCESS,
            roomTypeToDelete);

        ReservationManager expectedModel =
            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteRoomType(roomTypeToDelete);
        expectedModel.commitHotelManagementSystem();

        assertReservationCommandSuccess(deleteRoomTypeCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getRoomTypeList().size() + 1);
        DeleteRoomTypeCommand deleteRoomTypeCommand = new DeleteRoomTypeCommand(outOfBoundIndex);

        assertReservationCommandFailure(deleteRoomTypeCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_ROOM_TYPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showReservationForPayer(model, ALICE);

        RoomType roomType =
            model.getRoomTypeList().get(INDEX_FIRST_ROOM_TYPE.getZeroBased());
        DeleteRoomTypeCommand deleteRoomTypeCommand = new DeleteRoomTypeCommand(INDEX_FIRST_ROOM_TYPE);

        String expectedMessage = String.format(DeleteRoomTypeCommand.MESSAGE_DELETE_ROOM_TYPE_SUCCESS,
            roomType);

        ReservationModel expectedModel =
            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteRoomType(roomType);
        expectedModel.commitHotelManagementSystem();
        showNoReservation(expectedModel);

        assertReservationCommandSuccess(deleteRoomTypeCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        RoomType roomType =
            model.getRoomTypeList().get(INDEX_FIRST_ROOM_TYPE.getZeroBased());
        DeleteRoomTypeCommand deleteRoomTypeCommand = new DeleteRoomTypeCommand(INDEX_FIRST_ROOM_TYPE);

        ReservationModel expectedModel =
            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteRoomType(roomType);
        expectedModel.commitHotelManagementSystem();

        // delete -> first Reservation deleted
        deleteRoomTypeCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered Reservation list to show all
        // Reservations
        expectedModel.undoHotelManagementSystem();
        assertReservationCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
            expectedModel);

        // redo -> same first Reservation deleted again
        expectedModel.redoHotelManagementSystem();
        assertReservationCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
            expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getRoomTypeList().size() + 1);
        DeleteRoomTypeCommand deleteRoomTypeCommand = new DeleteRoomTypeCommand(outOfBoundIndex);

        // execution failed -> hms book state not added into model
        assertReservationCommandFailure(deleteRoomTypeCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_ROOM_TYPE_DISPLAYED_INDEX);

        // single hms book state in model -> undoCommand and redoCommand fail
        assertReservationCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertReservationCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteRoomTypeCommand deleteFirstCommand = new DeleteRoomTypeCommand(INDEX_FIRST_ROOM_TYPE);
        DeleteRoomTypeCommand deleteSecondCommand = new DeleteRoomTypeCommand(INDEX_SECOND_ROOM_TYPE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRoomTypeCommand deleteFirstCommandCopy = new DeleteRoomTypeCommand(INDEX_FIRST_ROOM_TYPE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // different Reservation -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoReservation(ReservationModel model) {
        model.updateFilteredReservationList(p -> false);

        assertTrue(model.getFilteredReservationList().isEmpty());
    }
}
