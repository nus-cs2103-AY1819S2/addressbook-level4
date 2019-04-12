package seedu.hms.logic.commands;

//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.hms.logic.commands.CommandTestUtil.DESC_ALICE_SINGLE_ROOM;
//import static seedu.hms.logic.commands.CommandTestUtil.DESC_CARL_SINGLE_ROOM;
//import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandFailure;
//import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandSuccess;
//import static seedu.hms.logic.commands.CommandTestUtil.showReservationForPayer;
//import static seedu.hms.testutil.TypicalCustomers.ALICE;
//import static seedu.hms.testutil.TypicalCustomers.DOUBLE;
//import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;
//import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;
//import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_RESERVATION;
//
//import org.junit.Test;
//
//import seedu.hms.commons.core.Messages;
//import seedu.hms.commons.core.index.Index;
//import seedu.hms.logic.CommandHistory;
//import seedu.hms.model.ReservationManager;
//import seedu.hms.model.ReservationModel;
//import seedu.hms.model.UserPrefs;
//import seedu.hms.model.VersionedHotelManagementSystem;
//import seedu.hms.model.reservation.Reservation;
//import seedu.hms.testutil.EditReservationDescriptorBuilder;
//import seedu.hms.testutil.ReservationBuilder;
//
public class EditReservationCommandTest {

//    private ReservationModel model =
//        new ReservationManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
//            new UserPrefs());
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Test
//    public void execute_allFieldsSpecifiedUnfilteredList_success() {
//        Reservation editedReservation = new ReservationBuilder().build();
//        EditReservationCommand.EditReservationDescriptor descriptor =
//            new EditReservationDescriptorBuilder(editedReservation)
//                .build();
//        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST_RESERVATION,
//            descriptor);
//
//        String expectedMessage = String.format(EditReservationCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
//            editedReservation);
//
//        ReservationModel expectedModel =
//            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
//                new UserPrefs());
//        expectedModel.setReservation(0, editedReservation);
//        expectedModel.commitHotelManagementSystem();
//
//        assertReservationCommandSuccess(editReservationCommand, model, commandHistory, expectedMessage,
// expectedModel);
//    }
//
//    @Test
//    public void execute_someFieldsSpecifiedUnfilteredList_success() {
//        Index indexLastReservation = Index.fromOneBased(model.getFilteredReservationList().size());
//        Reservation lastReservation = model.getFilteredReservationList().get(indexLastReservation.getZeroBased());
//
//        ReservationBuilder reservationInList = new ReservationBuilder(lastReservation);
//        Reservation editedReservation = reservationInList.withRoom(DOUBLE).build();
//
//        EditReservationCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
//            .withRoom(DOUBLE).build();
//        EditReservationCommand editReservationCommand = new EditReservationCommand(indexLastReservation, descriptor);
//
//        String expectedMessage = String.format(EditReservationCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
//            editedReservation);
//
//        ReservationModel expectedModel =
//            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
//                new UserPrefs());
//        expectedModel.setReservation(indexLastReservation.getZeroBased(), editedReservation);
//        expectedModel.commitHotelManagementSystem();
//
//        assertReservationCommandSuccess(editReservationCommand, model, commandHistory, expectedMessage,
// expectedModel);
//    }
//
//    @Test
//    public void execute_noFieldSpecifiedUnfilteredList_success() {
//        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST_RESERVATION,
//            new EditReservationCommand.EditReservationDescriptor());
//        Reservation editedReservation = model.getFilteredReservationList().get(INDEX_FIRST_RESERVATION.getZeroBased
// ());
//
//        String expectedMessage = String.format(EditReservationCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
//            editedReservation);
//
//        ReservationModel expectedModel =
//            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
//                new UserPrefs());
//        expectedModel.commitHotelManagementSystem();
//
//        assertReservationCommandSuccess(editReservationCommand, model, commandHistory, expectedMessage,
// expectedModel);
//    }
//
//    @Test
//    public void execute_filteredList_success() {
//        showReservationForPayer(model, ALICE);
//
//        Reservation reservationInFilteredList =
//            model.getFilteredReservationList().get(INDEX_FIRST_RESERVATION.getZeroBased());
//        Reservation editedReservation = new ReservationBuilder(reservationInFilteredList).withDates("14/05/2019",
//            "17/05/2019").build();
//        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST_RESERVATION,
//            new EditReservationDescriptorBuilder().withDates("14/05/2019", "17/05/2019").build());
//
//        String expectedMessage = String.format(EditReservationCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
//            editedReservation);
//
//        ReservationModel expectedModel =
//            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
//                new UserPrefs());
//        expectedModel.setReservation(0, editedReservation);
//        expectedModel.commitHotelManagementSystem();
//
//        assertReservationCommandSuccess(editReservationCommand, model, commandHistory, expectedMessage,
// expectedModel);
//    }
//
//    @Test
//    public void execute_invalidReservationIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
//        EditReservationCommand.EditReservationDescriptor descriptor =
//            new EditReservationDescriptorBuilder().withDates("14/05/2019", "17/05/2019").build();
//        EditReservationCommand editReservationCommand = new EditReservationCommand(outOfBoundIndex, descriptor);
//
//        assertReservationCommandFailure(editReservationCommand, model, commandHistory,
//            Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
//    }
//
//    /**
//     * Edit filtered list where index is larger than size of filtered list,
//     * but smaller than size of hms book
//     */
//    @Test
//    public void executeInvalidReservationIndexFilteredListFailure() {
//        showReservationForPayer(model, ALICE);
//        Index outOfBoundIndex = INDEX_SECOND_RESERVATION;
//        // ensures that outOfBoundIndex is still in bounds of hms book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getHotelManagementSystem().getReservationList().size());
//
//        EditReservationCommand editReservationCommand = new EditReservationCommand(outOfBoundIndex,
//            new EditReservationDescriptorBuilder().withDates("14/05/2019", "17/05/2019").build());
//
//        assertReservationCommandFailure(editReservationCommand, model, commandHistory,
//            Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
//        Reservation editedReservation = new ReservationBuilder().build();
//        EditReservationCommand.EditReservationDescriptor descriptor =
//            new EditReservationDescriptorBuilder(editedReservation)
//                .build();
//        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST_RESERVATION,
// descriptor);
//        ReservationModel expectedModel =
//            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
//                new UserPrefs());
//        expectedModel.setReservation(INDEX_FIRST_RESERVATION.getZeroBased(), editedReservation);
//        expectedModel.commitHotelManagementSystem();
//
//        // edit -> first Reservation edited
//        editReservationCommand.execute(model, commandHistory);
//
//        // undo -> reverts hotelManagementSystem back to previous state and filtered Reservation list to show all
//        // Reservations
//        expectedModel.undoHotelManagementSystem();
//        assertReservationCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
//            expectedModel);
//
//        // redo -> same first Reservation edited again
//        expectedModel.redoHotelManagementSystem();
//        assertReservationCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
//            expectedModel);
//    }
//
//    @Test
//    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
//        EditReservationCommand.EditReservationDescriptor descriptor =
//            new EditReservationDescriptorBuilder().withDates("14/05/2019", "17/05/2019").build();
//        EditReservationCommand editReservationCommand = new EditReservationCommand(outOfBoundIndex, descriptor);
//
//        // execution failed -> hms book state not added into model
//        assertReservationCommandFailure(editReservationCommand, model, commandHistory,
//            Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
//
//        // single hms book state in model -> undoCommand and redoCommand fail
//        assertReservationCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
//        assertReservationCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
//    }
//
//    /**
//     * 1. Edits a {@code Reservation} from a filtered list.
//     * 2. Undo the edit.
//     * 3. The unfiltered list should be shown now.
//     * 4. Redo the edit. This ensures {@code RedoCommand} edits the Reservation object regardless of indexing.
//     */
//    @Test
//    public void executeUndoRedoValidIndexFilteredListSameReservationEdited() throws Exception {
//        Reservation editedReservation = new ReservationBuilder().build();
//        EditReservationCommand.EditReservationDescriptor descriptor =
//            new EditReservationDescriptorBuilder(editedReservation)
//                .build();
//        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST_RESERVATION,
// descriptor);
//        ReservationModel expectedModel =
//            new ReservationManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
//                new UserPrefs());
//
//        showReservationForPayer(model, ALICE);
//        expectedModel.setReservation(INDEX_FIRST_RESERVATION.getZeroBased(), editedReservation);
//        expectedModel.commitHotelManagementSystem();
//
//        // edit -> edits second Reservation in unfiltered Reservation list / first Reservation in filtered
//        // Reservation list
//        editReservationCommand.execute(model, commandHistory);
//
//        // undo -> reverts hotelManagementSystem back to previous state and filtered Reservation list to show all
//        // Reservations
//        expectedModel.undoHotelManagementSystem();
//        assertReservationCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
//            expectedModel);
//
//        // redo -> edits same second Reservation in unfiltered Reservation list
//        expectedModel.redoHotelManagementSystem();
//        assertReservationCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
//            expectedModel);
//    }
//
//    @Test
//    public void equals() {
//        final EditReservationCommand standardCommand = new EditReservationCommand(INDEX_FIRST_RESERVATION,
//            DESC_ALICE_SINGLE_ROOM);
//
//        // same values -> returns true
//        EditReservationCommand.EditReservationDescriptor copyDescriptor =
//            new EditReservationCommand.EditReservationDescriptor(DESC_ALICE_SINGLE_ROOM);
//        EditReservationCommand commandWithSameValues = new EditReservationCommand(INDEX_FIRST_RESERVATION,
//            copyDescriptor);
//        assertTrue(standardCommand.equals(commandWithSameValues));
//
//        // same object -> returns true
//        assertTrue(standardCommand.equals(standardCommand));
//
//        // different types -> returns false
//        assertFalse(standardCommand.equals(new ClearHotelManagementSystemCommand()));
//
//        // different index -> returns false
//        assertFalse(standardCommand.equals(new EditReservationCommand(INDEX_SECOND_RESERVATION,
//            DESC_ALICE_SINGLE_ROOM)));
//
//        // different descriptor -> returns false
//        assertFalse(standardCommand.equals(new EditReservationCommand(INDEX_FIRST_RESERVATION,
// DESC_CARL_SINGLE_ROOM)));
//    }
}
