package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditHealthWorkerCommand.EditHealthWorkerDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.HealthWorkerBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.testutil.EditHealthWorkerDescriptorBuilder;
import seedu.address.testutil.HealthWorkerBuilder;

public class EditHealthWorkerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecified() {
        HealthWorker editedHealthWorker = new HealthWorkerBuilder().build();
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder(editedHealthWorker).build();
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditHealthWorkerCommand.MESSAGE_EDIT_HEALTHWORKER_SUCCESS,
                editedHealthWorker);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new HealthWorkerBook(model.getHealthWorkerBook()), new UserPrefs());
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
                new HealthWorkerBook(model.getHealthWorkerBook()), new UserPrefs());
        expectedModel.setHealthWorker(lastHealthWorker, editedHealthWorker);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editHealthWorkerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldsSpecified() {
        EditHealthWorkerCommand editHealthWorkerCommand = new EditHealthWorkerCommand(INDEX_FIRST_PERSON, new
                EditHealthWorkerDescriptor());
        HealthWorker editedHealthWorker = model.getFilteredHealthWorkerList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditHealthWorkerCommand.MESSAGE_EDIT_HEALTHWORKER_SUCCESS,
                editedHealthWorker);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new HealthWorkerBook(model.getHealthWorkerBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editHealthWorkerCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
