package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDoctorAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Doctor;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteDoctorCommand}.
 */
public class DeleteDoctorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Doctor doctorToDelete = model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteDoctorCommand.MESSAGE_DELETE_DOCTOR_SUCCESS, doctorToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteDoctor(doctorToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteDoctorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDoctorList().size() + 1);
        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(outOfBoundIndex);

        assertCommandFailure(deleteDoctorCommand, model,
                commandHistory, Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDoctorAtIndex(model, INDEX_FIRST_PERSON);

        Doctor doctorToDelete = model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteDoctorCommand.MESSAGE_DELETE_DOCTOR_SUCCESS, doctorToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteDoctor(doctorToDelete);
        expectedModel.commitAddressBook();
        showNoDoctor(expectedModel);

        assertCommandSuccess(deleteDoctorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDoctorAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of docX list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getDoctorList().size());

        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(outOfBoundIndex);

        assertCommandFailure(deleteDoctorCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Doctor doctorToDelete = model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteDoctor(doctorToDelete);
        expectedModel.commitAddressBook();

        // delete -> first doctor deleted
        deleteDoctorCommand.execute(model, commandHistory);

        // undo -> reverts docX back to previous state and filtered doctor list to show all doctors
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first doctor deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDoctorList().size() + 1);
        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(outOfBoundIndex);

        // execution failed -> docX state not added into model
        assertCommandFailure(deleteDoctorCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);

        // single docX state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Doctor} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted doctor in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the doctor object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameDoctorDeleted() throws Exception {
        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showDoctorAtIndex(model, INDEX_SECOND_PERSON);
        Doctor doctorToDelete = model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.deleteDoctor(doctorToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second doctor in unfiltered doctor list / first doctor in filtered doctor list
        deleteDoctorCommand.execute(model, commandHistory);

        // undo -> reverts docX back to previous state and filtered doctor list to show all doctors
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(doctorToDelete, model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second patient in unfiltered patient list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteDoctorCommand deleteFirstCommand = new DeleteDoctorCommand(INDEX_FIRST_PERSON);
        DeleteDoctorCommand deleteSecondCommand = new DeleteDoctorCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteDoctorCommand deleteFirstCommandCopy = new DeleteDoctorCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different doctor -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDoctor(Model model) {
        model.updateFilteredDoctorList(p -> false);

        assertTrue(model.getFilteredDoctorList().isEmpty());
    }
}
