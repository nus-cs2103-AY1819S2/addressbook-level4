package seedu.hms.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.assertBookingCommandFailure;
import static seedu.hms.logic.commands.CommandTestUtil.assertBookingCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;
import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_SERVICE_TYPE;
import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_SERVICE_TYPE;

import org.junit.Test;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.booking.serviceType.ServiceType;

public class DeleteServiceTypeCommandTest {

    private BookingModel model =
        new BookingManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ServiceType serviceType = model.getServiceTypeList().get(INDEX_FIRST_SERVICE_TYPE.getZeroBased());
        DeleteServiceTypeCommand deleteServiceTypeCommand = new DeleteServiceTypeCommand(INDEX_FIRST_SERVICE_TYPE);

        String expectedMessage = String.format(DeleteServiceTypeCommand.MESSAGE_DELETE_SERVICE_TYPE_SUCCESS,
            serviceType);

        BookingManager expectedModel =
            new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteServiceType(serviceType);
        expectedModel.commitHotelManagementSystem();

        assertBookingCommandSuccess(deleteServiceTypeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookingList().size() + 1);
        DeleteServiceTypeCommand deleteServiceTypeCommand = new DeleteServiceTypeCommand(outOfBoundIndex);

        assertBookingCommandFailure(deleteServiceTypeCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_SERVICE_TYPE_DISPLAYED_INDEX);
    }


    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        ServiceType serviceType = model.getServiceTypeList().get(INDEX_FIRST_SERVICE_TYPE.getZeroBased());
        DeleteServiceTypeCommand deleteServiceTypeCommand = new DeleteServiceTypeCommand(INDEX_FIRST_SERVICE_TYPE);

        BookingModel expectedModel =
            new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteServiceType(serviceType);
        expectedModel.commitHotelManagementSystem();

        // delete -> first Booking deleted
        deleteServiceTypeCommand.execute(model, commandHistory);

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
        DeleteServiceTypeCommand deleteServiceTypeCommand = new DeleteServiceTypeCommand(outOfBoundIndex);

        // execution failed -> hms book state not added into model
        assertBookingCommandFailure(deleteServiceTypeCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_SERVICE_TYPE_DISPLAYED_INDEX);

        // single hms book state in model -> undoCommand and redoCommand fail
        assertBookingCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertBookingCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteServiceTypeCommand deleteFirstCommand = new DeleteServiceTypeCommand(INDEX_FIRST_SERVICE_TYPE);
        DeleteServiceTypeCommand deleteSecondCommand = new DeleteServiceTypeCommand(INDEX_SECOND_SERVICE_TYPE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteServiceTypeCommand deleteFirstCommandCopy = new DeleteServiceTypeCommand(INDEX_FIRST_SERVICE_TYPE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

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
