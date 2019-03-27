package seedu.hms.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.DESC_ALICE_SPA;
import static seedu.hms.logic.commands.CommandTestUtil.DESC_CARL_SPA;
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
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.testutil.BookingBuilder;
import seedu.hms.testutil.EditBookingDescriptorBuilder;

public class EditBookingCommandTest {

    private BookingModel model =
            new BookingManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
                    new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Booking editedBooking = new BookingBuilder().build();
        EditBookingCommand.EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder(editedBooking)
                .build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST_BOOKING, descriptor);

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking);

        BookingModel expectedModel =
                new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                        new UserPrefs());
        expectedModel.setBooking(0, editedBooking);
        expectedModel.commitHotelManagementSystem();

        assertBookingCommandSuccess(editBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastBooking = Index.fromOneBased(model.getFilteredBookingList().size());
        Booking lastBooking = model.getFilteredBookingList().get(indexLastBooking.getZeroBased());

        BookingBuilder bookingInList = new BookingBuilder(lastBooking);
        Booking editedBooking = bookingInList.withService(ServiceType.POOL).build();

        EditBookingCommand.EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder()
            .withService(ServiceType.POOL).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(indexLastBooking, descriptor);

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking);

        BookingModel expectedModel =
                new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                        new UserPrefs());
        expectedModel.setBooking(indexLastBooking.getZeroBased(), editedBooking);
        expectedModel.commitHotelManagementSystem();

        assertBookingCommandSuccess(editBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST_BOOKING,
                new EditBookingCommand.EditBookingDescriptor());
        Booking editedBooking = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking);

        BookingModel expectedModel =
                new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                        new UserPrefs());
        expectedModel.commitHotelManagementSystem();

        assertBookingCommandSuccess(editBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBookingForPayer(model, ALICE);

        Booking bookingInFilteredList = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        Booking editedBooking = new BookingBuilder(bookingInFilteredList).withTiming(10, 11).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST_BOOKING,
                new EditBookingDescriptorBuilder().withTiming(10, 11).build());

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking);

        BookingModel expectedModel =
                new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                        new UserPrefs());
        expectedModel.setBooking(0, editedBooking);
        expectedModel.commitHotelManagementSystem();

        assertBookingCommandSuccess(editBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidBookingIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookingList().size() + 1);
        EditBookingCommand.EditBookingDescriptor descriptor =
                new EditBookingDescriptorBuilder().withTiming(10, 11).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(outOfBoundIndex, descriptor);

        assertBookingCommandFailure(editBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of hms book
     */
    @Test
    public void executeInvalidBookingIndexFilteredListFailure() {
        showBookingForPayer(model, ALICE);
        Index outOfBoundIndex = INDEX_SECOND_BOOKING;
        // ensures that outOfBoundIndex is still in bounds of hms book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHotelManagementSystem().getBookingList().size());

        EditBookingCommand editBookingCommand = new EditBookingCommand(outOfBoundIndex,
                new EditBookingDescriptorBuilder().withTiming(10, 11).build());

        assertBookingCommandFailure(editBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Booking editedBooking = new BookingBuilder().build();
        Booking bookingToEdit = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        EditBookingCommand.EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder(editedBooking)
                .build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST_BOOKING, descriptor);
        BookingModel expectedModel =
                new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                        new UserPrefs());
        expectedModel.setBooking(INDEX_FIRST_BOOKING.getZeroBased(), editedBooking);
        expectedModel.commitHotelManagementSystem();

        // edit -> first Booking edited
        editBookingCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered Booking list to show all Bookings
        expectedModel.undoHotelManagementSystem();
        assertBookingCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
                expectedModel);

        // redo -> same first Booking edited again
        expectedModel.redoHotelManagementSystem();
        assertBookingCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookingList().size() + 1);
        EditBookingCommand.EditBookingDescriptor descriptor =
                new EditBookingDescriptorBuilder().withTiming(10, 11).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(outOfBoundIndex, descriptor);

        // execution failed -> hms book state not added into model
        assertBookingCommandFailure(editBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);

        // single hms book state in model -> undoCommand and redoCommand fail
        assertBookingCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertBookingCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Booking} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the Booking object regardless of indexing.
     */
    @Test
    public void executeUndoRedoValidIndexFilteredListSameBookingEdited() throws Exception {
        Booking editedBooking = new BookingBuilder().build();
        EditBookingCommand.EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder(editedBooking)
                .build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST_BOOKING, descriptor);
        BookingModel expectedModel =
                new BookingManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                        new UserPrefs());

        showBookingForPayer(model, ALICE);
        Booking bookingToEdit = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        expectedModel.setBooking(INDEX_FIRST_BOOKING.getZeroBased(), editedBooking);
        expectedModel.commitHotelManagementSystem();

        // edit -> edits second Booking in unfiltered Booking list / first Booking in filtered Booking list
        editBookingCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered Booking list to show all Bookings
        expectedModel.undoHotelManagementSystem();
        assertBookingCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
                expectedModel);

        // redo -> edits same second Booking in unfiltered Booking list
        expectedModel.redoHotelManagementSystem();
        assertBookingCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void equals() {
        final EditBookingCommand standardCommand = new EditBookingCommand(INDEX_FIRST_BOOKING, DESC_ALICE_SPA);

        // same values -> returns true
        EditBookingCommand.EditBookingDescriptor copyDescriptor =
                new EditBookingCommand.EditBookingDescriptor(DESC_ALICE_SPA);
        EditBookingCommand commandWithSameValues = new EditBookingCommand(INDEX_FIRST_BOOKING, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearHotelManagementSystemCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditBookingCommand(INDEX_SECOND_BOOKING, DESC_ALICE_SPA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditBookingCommand(INDEX_FIRST_BOOKING, DESC_CARL_SPA)));
    }
}
