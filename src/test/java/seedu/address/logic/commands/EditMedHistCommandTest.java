package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Patient;
import seedu.address.testutil.EditMedHistDescriptorBuilder;
import seedu.address.testutil.MedHistBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_MED_HIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WRITE_UP;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalDocX;

/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for EditMedHistCommand.
 */
public class EditMedHistCommandTest {

    private Model model = new ModelManager(getTypicalDocX(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredMedHistList_success() {
        MedicalHistory editedMedHist = new MedHistBuilder().build();
        EditMedHistCommand.EditMedHistDescriptor descriptor = new EditMedHistDescriptorBuilder(editedMedHist).build();
        EditMedHistCommand editMedHistCommand = new EditMedHistCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditMedHistCommand.MESSAGE_EDIT_MEDHIST_SUCCESS, editedMedHist);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.setMedHist(model.getFilteredMedHistList().get(0), editedMedHist);
        expectedModel.commitDocX();

        assertCommandSuccess(editMedHistCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredMedHistList_success() {
        Index indexLastMedHist = Index.fromOneBased(model.getFilteredMedHistList().size());
        MedicalHistory lastMedHist = model.getFilteredMedHistList().get(indexLastMedHist.getZeroBased());

        MedHistBuilder medHistInList = new MedHistBuilder(lastMedHist);
        MedicalHistory editedMedHist = medHistInList.withWriteUp(VALID_WRITE_UP).build();

        EditMedHistCommand.EditMedHistDescriptor descriptor = new EditMedHistDescriptorBuilder()
                .withWriteUp(VALID_WRITE_UP).build();
        EditMedHistCommand editMedHistCommand = new EditMedHistCommand(indexLastMedHist, descriptor);

        String expectedMessage = String.format(EditMedHistCommand.MESSAGE_EDIT_MEDHIST_SUCCESS, editedMedHist);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.setMedHist(lastMedHist, editedMedHist);
        expectedModel.commitDocX();

        assertCommandSuccess(editMedHistCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredMedHistList_success() {
        EditMedHistCommand editMedHistCommand = new EditMedHistCommand(INDEX_FIRST_PERSON,
                new EditMedHistCommand.EditMedHistDescriptor());
        MedicalHistory editedMedHist = model.getFilteredMedHistList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditMedHistCommand.MESSAGE_EDIT_MEDHIST_SUCCESS, editedMedHist);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.commitDocX();

        assertCommandSuccess(editMedHistCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateUnfilteredMedHistList_failure() {
        MedicalHistory firstMedHist = model.getFilteredMedHistList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditMedHistCommand.EditMedHistDescriptor descriptor = new EditMedHistDescriptorBuilder(firstMedHist).build();
        EditMedHistCommand editMedHistCommand = new EditMedHistCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editMedHistCommand, model, commandHistory, EditMedHistCommand.MESSAGE_DUPLICATE_MEDHIST);
    }

}


    @Test
    public void execute_duplicatePatientFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PERSON);

        // edit patient in filtered list into a duplicate in docX record.
        Patient patientInList = model.getDocX().getPatientList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditPatientCommand editPatientCommand = new EditPatientCommand(INDEX_FIRST_PERSON,
                new EditPatientDescriptorBuilder(patientInList).build());

        assertCommandFailure(editPatientCommand, model, commandHistory, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editPatientCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPatientIndexFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDocX().getPatientList().size());

        EditPatientCommand editPatientCommand = new EditPatientCommand(outOfBoundIndex,
                new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editPatientCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPatientCommand standardCommand = new EditPatientCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPatientCommand.EditPatientDescriptor copyDescriptor = new EditPatientCommand.EditPatientDescriptor(DESC_AMY);
        EditPatientCommand commandWithSameValues = new EditPatientCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPatientCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPatientCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}

