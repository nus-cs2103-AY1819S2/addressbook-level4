package seedu.hms.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.assertBookingCommandFailure;
import static seedu.hms.logic.commands.CommandTestUtil.assertBookingCommandSuccess;
import static seedu.hms.logic.commands.CommandTestUtil.showBookingForPayer;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;
import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_BOOKING;
import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_BOOKING;

import org.junit.Test;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.Model;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.booking.Booking;

public class DeleteBookingCommandTest {

    private BookingModel model =
            new BookingManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
                    new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Booking bookingToDelete = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST_BOOKING);

        String expectedMessage = String.format(DeleteBookingCommand.MESSAGE_DELETE_BOOKING_SUCCESS, bookingToDelete);

        BookingManager expectedModel =
                new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteBooking(bookingToDelete);
        expectedModel.commitHotelManagementSystem();

        assertBookingCommandSuccess(deleteBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookingList().size() + 1);
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(outOfBoundIndex);

        assertBookingCommandFailure(deleteBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookingForPayer(model, ALICE);

        Booking bookingToDelete = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST_BOOKING);

        String expectedMessage = String.format(DeleteBookingCommand.MESSAGE_DELETE_BOOKING_SUCCESS, bookingToDelete);

        BookingModel expectedModel =
            new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                    new UserPrefs());
        expectedModel.deleteBooking(bookingToDelete);
        expectedModel.commitHotelManagementSystem();
        showNoBooking(expectedModel);

        assertBookingCommandSuccess(deleteBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookingForPayer(model, ALICE);

        Index outOfBoundIndex = INDEX_SECOND_BOOKING;
        // ensures that outOfBoundIndex is still in bounds of hms book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHotelManagementSystem().getBookingList().size());

        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(outOfBoundIndex);

        assertBookingCommandFailure(deleteBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Booking bookingToDelete = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST_BOOKING);

        BookingModel expectedModel =
                new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                        new UserPrefs());
        expectedModel.deleteBooking(bookingToDelete);
        expectedModel.commitHotelManagementSystem();

        // delete -> first Booking deleted
        deleteBookingCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered Booking list to show all Bookings
        expectedModel.undoHotelManagementSystem();
        assertBookingCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
                expectedModel);

        // redo -> same first Booking deleted again
        expectedModel.redoHotelManagementSystem();
        assertBookingCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookingList().size() + 1);
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(outOfBoundIndex);

        // execution failed -> hms book state not added into model
        assertBookingCommandFailure(deleteBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);

        // single hms book state in model -> undoCommand and redoCommand fail
        assertBookingCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertBookingCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Booking} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted Booking in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the Booking object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameBookingDeleted() throws Exception {
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST_BOOKING);
        BookingModel expectedModel =
                new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                        new UserPrefs());

        showBookingForPayer(model, ALICE);
        Booking bookingToDelete = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        expectedModel.deleteBooking(bookingToDelete);
        expectedModel.commitHotelManagementSystem();

        // delete -> deletes second Booking in unfiltered Booking list / first Booking in filtered Booking list
        deleteBookingCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered Booking list to show all Bookings
        expectedModel.undoHotelManagementSystem();
        expectedModel.updateFilteredBookingList(Model.PREDICATE_SHOW_ALL_BOOKINGS);
        model.updateFilteredBookingList(Model.PREDICATE_SHOW_ALL_BOOKINGS);
        assertBookingCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
                expectedModel);

        // redo -> deletes same second Booking in unfiltered Booking list
        expectedModel.redoHotelManagementSystem();
        assertBookingCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void equals() {
        DeleteBookingCommand deleteFirstCommand = new DeleteBookingCommand(INDEX_FIRST_BOOKING);
        DeleteBookingCommand deleteSecondCommand = new DeleteBookingCommand(INDEX_SECOND_BOOKING);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBookingCommand deleteFirstCommandCopy = new DeleteBookingCommand(INDEX_FIRST_BOOKING);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different Booking -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBooking(BookingModel model) {
        model.updateFilteredBookingList(p -> false);

        assertTrue(model.getFilteredBookingList().isEmpty());
    }
}
