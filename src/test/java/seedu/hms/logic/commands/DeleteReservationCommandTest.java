package seedu.hms.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandFailure;
import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandSuccess;
import static seedu.hms.logic.commands.CommandTestUtil.showReservationForPayer;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;
import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;
import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_RESERVATION;

import org.junit.Test;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.Model;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.reservation.Reservation;

public class DeleteReservationCommandTest {

    private ReservationModel model =
        new ReservationManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Reservation reservationToDelete =
            model.getFilteredReservationList().get(INDEX_FIRST_RESERVATION.getZeroBased());
        DeleteReservationCommand deleteReservationCommand = new DeleteReservationCommand(INDEX_FIRST_RESERVATION);

        String expectedMessage = String.format(DeleteReservationCommand.MESSAGE_DELETE_RESERVATION_SUCCESS,
            reservationToDelete);

        ReservationManager expectedModel =
            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitHotelManagementSystem();

        assertReservationCommandSuccess(deleteReservationCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        DeleteReservationCommand deleteReservationCommand = new DeleteReservationCommand(outOfBoundIndex);

        assertReservationCommandFailure(deleteReservationCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showReservationForPayer(model, ALICE);

        Reservation reservationToDelete =
            model.getFilteredReservationList().get(INDEX_FIRST_RESERVATION.getZeroBased());
        DeleteReservationCommand deleteReservationCommand = new DeleteReservationCommand(INDEX_FIRST_RESERVATION);

        String expectedMessage = String.format(DeleteReservationCommand.MESSAGE_DELETE_RESERVATION_SUCCESS,
            reservationToDelete);

        ReservationModel expectedModel =
            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitHotelManagementSystem();
        showNoReservation(expectedModel);

        assertReservationCommandSuccess(deleteReservationCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showReservationForPayer(model, ALICE);

        Index outOfBoundIndex = INDEX_SECOND_RESERVATION;
        // ensures that outOfBoundIndex is still in bounds of hms book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHotelManagementSystem().getReservationList().size());

        DeleteReservationCommand deleteReservationCommand = new DeleteReservationCommand(outOfBoundIndex);

        assertReservationCommandFailure(deleteReservationCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Reservation reservationToDelete =
            model.getFilteredReservationList().get(INDEX_FIRST_RESERVATION.getZeroBased());
        DeleteReservationCommand deleteReservationCommand = new DeleteReservationCommand(INDEX_FIRST_RESERVATION);

        ReservationModel expectedModel =
            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitHotelManagementSystem();

        // delete -> first Reservation deleted
        deleteReservationCommand.execute(model, commandHistory);

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
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        DeleteReservationCommand deleteReservationCommand = new DeleteReservationCommand(outOfBoundIndex);

        // execution failed -> hms book state not added into model
        assertReservationCommandFailure(deleteReservationCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);

        // single hms book state in model -> undoCommand and redoCommand fail
        assertReservationCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertReservationCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Reservation} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted Reservation in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the Reservation object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameReservationDeleted() throws Exception {
        DeleteReservationCommand deleteReservationCommand = new DeleteReservationCommand(INDEX_FIRST_RESERVATION);
        ReservationModel expectedModel =
            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());

        showReservationForPayer(model, ALICE);
        Reservation reservationToDelete =
            model.getFilteredReservationList().get(INDEX_FIRST_RESERVATION.getZeroBased());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitHotelManagementSystem();

        // delete -> deletes second Reservation in unfiltered Reservation list / first Reservation in filtered
        // Reservation list
        deleteReservationCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered Reservation list to show all
        // Reservations
        expectedModel.undoHotelManagementSystem();
        expectedModel.updateFilteredReservationList(Model.PREDICATE_SHOW_ALL_RESERVATIONS);
        model.updateFilteredReservationList(Model.PREDICATE_SHOW_ALL_RESERVATIONS);
        assertReservationCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
            expectedModel);

        // redo -> deletes same second Reservation in unfiltered Reservation list
        expectedModel.redoHotelManagementSystem();
        assertReservationCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
            expectedModel);
    }

    @Test
    public void equals() {
        DeleteReservationCommand deleteFirstCommand = new DeleteReservationCommand(INDEX_FIRST_RESERVATION);
        DeleteReservationCommand deleteSecondCommand = new DeleteReservationCommand(INDEX_SECOND_RESERVATION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteReservationCommand deleteFirstCommandCopy = new DeleteReservationCommand(INDEX_FIRST_RESERVATION);
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
