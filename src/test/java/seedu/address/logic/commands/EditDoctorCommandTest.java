package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDoctorAtIndex;
import static seedu.address.testutil.TypicalDoctors.getTypicalDocX_doctor;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.doctor.EditDoctorCommand;
import seedu.address.logic.commands.doctor.EditDoctorCommand.EditDoctorDescriptor;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.EditDoctorDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditDoctorCommand.
 */
public class EditDoctorCommandTest {

    private Model model = new ModelManager(getTypicalDocX_doctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Doctor editedDoctor = new DoctorBuilder().build();
        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder(editedDoctor).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditDoctorCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.setDoctor(model.getFilteredDoctorList().get(0), editedDoctor);
        expectedModel.commitDocX();

        assertCommandSuccess(editDoctorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastDoctor = Index.fromOneBased(model.getFilteredDoctorList().size());
        Doctor lastDoctor = model.getFilteredDoctorList().get(indexLastDoctor.getZeroBased());

        DoctorBuilder doctorInList = new DoctorBuilder(lastDoctor);
        Doctor editedDoctor = doctorInList.withName(VALID_NAME_STEVEN).withPhone("90000000")
                .withSpecs("surgery").build();

        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder().withName(VALID_NAME_STEVEN)
                .withPhone("90000000").withSpecs("surgery").build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(indexLastDoctor, descriptor);

        String expectedMessage = String.format(EditDoctorCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.setDoctor(lastDoctor, editedDoctor);
        expectedModel.commitDocX();
        assertCommandSuccess(editDoctorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(INDEX_FIRST_PERSON,
                new EditDoctorCommand.EditDoctorDescriptor());
        Doctor editedDoctor = model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditDoctorCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.commitDocX();

        assertCommandSuccess(editDoctorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showDoctorAtIndex(model, INDEX_FIRST_PERSON);

        Doctor doctorInFilteredList = model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased());
        Doctor editedDoctor = new DoctorBuilder(doctorInFilteredList).withName(VALID_NAME_STEVEN).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(INDEX_FIRST_PERSON,
                new EditDoctorDescriptorBuilder().withName(VALID_NAME_STEVEN).build());

        String expectedMessage = String.format(EditDoctorCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.setDoctor(model.getFilteredDoctorList().get(0), editedDoctor);
        expectedModel.commitDocX();

        assertCommandSuccess(editDoctorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateDoctorUnfilteredList_failure() {
        Doctor firstDoctor = model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder(firstDoctor).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editDoctorCommand, model, commandHistory, EditDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);
    }

    @Test
    public void execute_duplicateDoctorFilteredList_failure() {
        showDoctorAtIndex(model, INDEX_FIRST_PERSON);

        // edit doctor in filtered list into a duplicate in docX record.
        Doctor doctorInList = model.getDocX().getDoctorList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(INDEX_FIRST_PERSON,
                new EditDoctorDescriptorBuilder(doctorInList).build());

        assertCommandFailure(editDoctorCommand, model, commandHistory, EditDoctorCommand.MESSAGE_DUPLICATE_DOCTOR);
    }

    @Test
    public void execute_invalidDoctorIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDoctorList().size() + 1);
        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder().withName(VALID_NAME_STEVEN).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editDoctorCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidDoctorIndexFilteredList_failure() {
        showDoctorAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of docX list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDocX().getDoctorList().size());

        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(outOfBoundIndex,
                new EditDoctorDescriptorBuilder().withName(VALID_NAME_STEVEN).build());

        assertCommandFailure(editDoctorCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Doctor editedDoctor = new DoctorBuilder().build();
        Doctor doctorToEdit = model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder(editedDoctor).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.setDoctor(doctorToEdit, editedDoctor);
        expectedModel.commitDocX();

        // edit -> first doctor edited
        editDoctorCommand.execute(model, commandHistory);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDoctorList().size() + 1);
        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder().withName(VALID_NAME_STEVEN).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(outOfBoundIndex, descriptor);

        // execution failed -> docX state not added into model
        assertCommandFailure(editDoctorCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
    }

    /**
     * 1. Edits a {@code Doctor} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited doctor in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the doctor object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameDoctorEdited() throws Exception {
        Doctor editedDoctor = new DoctorBuilder().build();
        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder(editedDoctor).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());

        showDoctorAtIndex(model, INDEX_SECOND_PERSON);
        Doctor doctorToEdit = model.getFilteredDoctorList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setDoctor(doctorToEdit, editedDoctor);
        expectedModel.commitDocX();

        // edit -> edits second doctor in unfiltered doctor list / first doctor in filtered doctor list
        editDoctorCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        final EditDoctorCommand standardCommand = new EditDoctorCommand(INDEX_FIRST_PERSON, DESC_ALVINA);

        // same values -> returns true
        EditDoctorDescriptor copyDescriptor = new EditDoctorDescriptor(DESC_ALVINA);
        EditDoctorCommand commandWithSameValues = new EditDoctorCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditDoctorCommand(INDEX_SECOND_PERSON, DESC_ALVINA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditDoctorCommand(INDEX_FIRST_PERSON, DESC_STEVEN)));
    }

}
