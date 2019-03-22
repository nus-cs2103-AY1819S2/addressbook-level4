package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditHealthWorkerCommand.EditHealthWorkerDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.HealthWorkerBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientBook;
import seedu.address.model.RequestBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.testutil.EditHealthWorkerDescriptorBuilder;
import seedu.address.testutil.HealthWorkerBuilder;

public class EditHealthWorkerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
        getTypicalPatientBook(), getTypicalRequestBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecified() {
        HealthWorker editedHealthWorker = new HealthWorkerBuilder().build();
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder(editedHealthWorker).build();
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditHealthWorkerCommand.MESSAGE_EDIT_HEALTHWORKER_SUCCESS,
                editedHealthWorker);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new HealthWorkerBook(model.getHealthWorkerBook()), new PatientBook(model.getPatientBook()),
            new RequestBook(model.getRequestBook()), new UserPrefs());
        expectedModel.setHealthWorker(model.getFilteredHealthWorkerList().get(0), editedHealthWorker);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editHealthWorkerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified() {
        Index indexLastHealthWorker = Index.fromOneBased(model.getFilteredHealthWorkerList().size());
        HealthWorker lastHealthWorker = model.getFilteredHealthWorkerList().get(indexLastHealthWorker.getZeroBased());

        HealthWorker editedHealthWorker = ((HealthWorkerBuilder) new HealthWorkerBuilder(lastHealthWorker)
                .withName(VALID_NAME_BETTY).withPhone(VALID_PHONE_BETTY)).build();

        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_BETTY)
                .withPhone(VALID_PHONE_BETTY).build();
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(indexLastHealthWorker,
                descriptor);

        String expectedMessage = String.format(EditHealthWorkerCommand.MESSAGE_EDIT_HEALTHWORKER_SUCCESS,
                editedHealthWorker);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new HealthWorkerBook(model.getHealthWorkerBook()), new PatientBook(model.getPatientBook()),
            getTypicalRequestBook(), new UserPrefs());
        expectedModel.setHealthWorker(lastHealthWorker, editedHealthWorker);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editHealthWorkerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldsSpecified() {
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_FIRST, new
                EditHealthWorkerDescriptor());
        HealthWorker editedHealthWorker = model.getFilteredHealthWorkerList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditHealthWorkerCommand.MESSAGE_EDIT_HEALTHWORKER_SUCCESS,
                editedHealthWorker);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new HealthWorkerBook(model.getHealthWorkerBook()), new PatientBook(model.getPatientBook()),
            getTypicalRequestBook(), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editHealthWorkerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList() {
        HealthWorker healthWorkerInFilteredList = model.getFilteredHealthWorkerList().get(INDEX_FIRST
                .getZeroBased());
        HealthWorker editedHealthWorker = ((HealthWorkerBuilder) new HealthWorkerBuilder(healthWorkerInFilteredList)
                .withName(VALID_NAME_BETTY)).build();
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_FIRST, new
                EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_BETTY).build());

        String expectedMessage = String.format(EditHealthWorkerCommand.MESSAGE_EDIT_HEALTHWORKER_SUCCESS,
                editedHealthWorker);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new HealthWorkerBook(model.getHealthWorkerBook()), new PatientBook(model.getPatientBook()),
            getTypicalRequestBook(), new UserPrefs());
        expectedModel.setHealthWorker(model.getFilteredHealthWorkerList().get(0), editedHealthWorker);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editHealthWorkerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateHealthWorker() {
        HealthWorker firstHealthWorker = model.getFilteredHealthWorkerList().get(INDEX_FIRST.getZeroBased());
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder(firstHealthWorker).build();
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editHealthWorkerCommand, model, commandHistory,
                HealthWorkerCommand.DUPLICATE_HEALTH_WORKER);
    }

    @Test
    public void execute_duplicateHealthWorkerFilteredList() {
        HealthWorker healthWorkerInList = model.getHealthWorkerBook().getHealthWorkerList().get(INDEX_SECOND
                .getZeroBased());
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_FIRST,
                new EditHealthWorkerDescriptorBuilder(healthWorkerInList).build());

        assertCommandFailure(editHealthWorkerCommand, model, commandHistory,
                EditHealthWorkerCommand.DUPLICATE_HEALTH_WORKER);
    }

    @Test
    public void execute_invalidIndex() {
        Index outOfBounds = Index.fromOneBased(model.getFilteredHealthWorkerList().size() + 1);
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_BETTY)
                .build();
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(outOfBounds, descriptor);

        assertCommandFailure(editHealthWorkerCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_undoRedo() throws Exception {
        HealthWorker editedHealthWorker = new HealthWorkerBuilder().build();
        HealthWorker toEdit = model.getFilteredHealthWorkerList().get(INDEX_FIRST.getZeroBased());
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder(editedHealthWorker).build();
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_FIRST, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new HealthWorkerBook(model.getHealthWorkerBook()), new PatientBook(model.getPatientBook()),
            getTypicalRequestBook(), new UserPrefs());
        expectedModel.setHealthWorker(toEdit, editedHealthWorker);
        expectedModel.commitAddressBook();

        editHealthWorkerCommand.execute(model, commandHistory);

        // undo
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoRedo_invalidIndex() {
        Index outOfBounds = Index.fromOneBased(model.getFilteredHealthWorkerList().size() + 1);
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_BETTY)
                .build();
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(outOfBounds, descriptor);

        assertCommandFailure(editHealthWorkerCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
    @Test
    public void execute_undoRedo_filteredList() throws Exception {
        HealthWorker editedHealthWorker = new HealthWorkerBuilder().build();
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder(editedHealthWorker).build();
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new HealthWorkerBook(model.getHealthWorkerBook()), new PatientBook(model.getPatientBook()),
            getTypicalRequestBook(), new UserPrefs());

        HealthWorker toEdit = model.getFilteredHealthWorkerList().get(INDEX_FIRST.getZeroBased());
        expectedModel.setHealthWorker(toEdit, editedHealthWorker);
        expectedModel.commitAddressBook();

        editHealthWorkerCommand.execute(model, commandHistory);

        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredHealthWorkerList().get(INDEX_FIRST.getZeroBased()), toEdit);
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditHealthWorkerCommand standardCommand = new EditHealthWorkerCommand(INDEX_FIRST, DESC_ANDY);

        // same object -> return true
        assertTrue(standardCommand.equals(standardCommand));

        // same values -> return true
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptor(DESC_ANDY);
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_FIRST, descriptor);
        assertTrue(editHealthWorkerCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> return false
        assertFalse(standardCommand.equals(1));

        // different index -> return false
        assertFalse(standardCommand.equals(new EditHealthWorkerCommand(INDEX_SECOND, DESC_ANDY)));

        // different descriptor -> return false
        assertFalse(standardCommand.equals(new EditHealthWorkerCommand(INDEX_FIRST, DESC_BETTY)));
    }
}
