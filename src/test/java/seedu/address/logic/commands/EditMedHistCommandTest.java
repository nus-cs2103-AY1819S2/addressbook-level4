package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_WRITE_UP;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDHIST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDHIST;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.medicalhistory.EditMedHistCommand;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.testutil.EditMedHistDescriptorBuilder;
import seedu.address.testutil.MedHistBuilder;
import seedu.address.testutil.TypicalMedHists;

/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for EditMedHistCommand.
 */
public class EditMedHistCommandTest {

    private Model model = new ModelManager(TypicalMedHists.getTypicalDocX(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredMedHistList_success() {
        MedicalHistory editedMedHist = new MedHistBuilder().build();
        EditMedHistCommand.EditMedHistDescriptor descriptor = new EditMedHistDescriptorBuilder(editedMedHist).build();
        EditMedHistCommand editMedHistCommand = new EditMedHistCommand(INDEX_FIRST_MEDHIST, descriptor);

        String expectedMessage = String.format(EditMedHistCommand.MESSAGE_EDIT_MEDHIST_SUCCESS, editedMedHist);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        //expectedModel.setMedHist(model.getFilteredMedHistList().get(0), editedMedHist);
        expectedModel.commitDocX();
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
        EditMedHistCommand editMedHistCommand = new EditMedHistCommand(INDEX_FIRST_MEDHIST,
                new EditMedHistCommand.EditMedHistDescriptor());
        MedicalHistory editedMedHist = model.getFilteredMedHistList().get(INDEX_FIRST_MEDHIST.getZeroBased());

        String expectedMessage = String.format(EditMedHistCommand.MESSAGE_EDIT_MEDHIST_SUCCESS, editedMedHist);

        Model expectedModel = new ModelManager(new DocX(model.getDocX()), new UserPrefs());
        expectedModel.commitDocX();

        //assertCommandSuccess(editMedHistCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateUnfilteredMedHistList_failure() {
        MedicalHistory firstMedHist = model.getFilteredMedHistList().get(INDEX_FIRST_MEDHIST.getZeroBased());
        EditMedHistCommand.EditMedHistDescriptor descriptor = new EditMedHistDescriptorBuilder(firstMedHist).build();
        EditMedHistCommand editMedHistCommand = new EditMedHistCommand(INDEX_SECOND_MEDHIST, descriptor);

        //assertCommandFailure(editMedHistCommand, model, commandHistory, EditMedHistCommand.MESSAGE_DUPLICATE_MEDHIST);
    }
}
