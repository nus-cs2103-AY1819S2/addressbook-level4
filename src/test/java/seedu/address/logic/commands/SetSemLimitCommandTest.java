package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_Y1S1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_Y2S2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_CAP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_LECTURE_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_PROJECT_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SEMESTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SEMESTER;
import static seedu.address.testutil.TypicalModuleTaken.getTypicalGradTrak;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.SetSemLimitCommand.EditSemLimitDescriptor;
import seedu.address.model.GradTrak;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SemLimit;
import seedu.address.model.UserInfo;
import seedu.address.model.UserPrefs;
import seedu.address.model.course.CourseList;
import seedu.address.model.moduleinfo.ModuleInfoList;
import seedu.address.testutil.EditSemLimitDescriptorBuilder;
import seedu.address.testutil.SemLimitBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for SetSemLimitCommand.
 */
public class SetSemLimitCommandTest {

    private static final int INVALID_SEMESTER_INDEX = 11;

    private Model model = new ModelManager(getTypicalGradTrak(), new UserPrefs(),
            new ModuleInfoList(), new CourseList(), new UserInfo());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        SemLimit editedSemLimit = new SemLimitBuilder().build();
        EditSemLimitDescriptor descriptor = new EditSemLimitDescriptorBuilder(editedSemLimit).build();
        SetSemLimitCommand setSemLimitCommand = new SetSemLimitCommand(
                Index.fromZeroBased(INDEX_FIRST_SEMESTER.getIndex()), descriptor);

        String expectedMessage = String.format(SetSemLimitCommand.MESSAGE_EDIT_LIMIT_SUCCESS, editedSemLimit);

        Model expectedModel = new ModelManager(new GradTrak(model.getGradTrak()), new UserPrefs(),
                                               new ModuleInfoList(), new CourseList(), new UserInfo());
        expectedModel.setSemesterLimit(INDEX_FIRST_SEMESTER.getIndex(), editedSemLimit);
        expectedModel.commitGradTrak();

        assertCommandSuccess(setSemLimitCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastSem = Index.fromOneBased(model.getSemLimitList().size());
        SemLimit lastSemLimit = model.getSemLimitList().get(indexLastSem.getZeroBased());

        SemLimitBuilder personInList = new SemLimitBuilder(lastSemLimit);
        SemLimit editedSemLimit = personInList
                .withMaxCap(Double.parseDouble(VALID_MAX_CAP))
                .withMaxLectureHour(VALID_MAX_LECTURE_HOUR)
                .withMaxProjectHour(VALID_MAX_PROJECT_HOUR).build();

        EditSemLimitDescriptor descriptor = new EditSemLimitDescriptorBuilder()
                .withMaxCap(VALID_MAX_CAP)
                .withMaxLectureHour(VALID_MAX_LECTURE_HOUR)
                .withMaxProjectHour(VALID_MAX_PROJECT_HOUR).build();
        SetSemLimitCommand editCommand = new SetSemLimitCommand(indexLastSem, descriptor);

        String expectedMessage = String.format(SetSemLimitCommand.MESSAGE_EDIT_LIMIT_SUCCESS, editedSemLimit);

        Model expectedModel = new ModelManager(new GradTrak(model.getGradTrak()), new UserPrefs(),
                                               new ModuleInfoList(), new CourseList(), new UserInfo());
        expectedModel.setSemesterLimit(indexLastSem.getZeroBased(), editedSemLimit);
        expectedModel.commitGradTrak();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        SetSemLimitCommand editCommand = new SetSemLimitCommand(
                Index.fromZeroBased(INDEX_FIRST_SEMESTER.getIndex()), new EditSemLimitDescriptor());
        SemLimit editedSemLimit = model.getSemLimitList().get(INDEX_FIRST_SEMESTER.getIndex());

        String expectedMessage = String.format(SetSemLimitCommand.MESSAGE_EDIT_LIMIT_SUCCESS, editedSemLimit);

        Model expectedModel = new ModelManager(new GradTrak(model.getGradTrak()), new UserPrefs(),
                                               new ModuleInfoList(), new CourseList(), new UserInfo());
        expectedModel.commitGradTrak();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSemLimitIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(INVALID_SEMESTER_INDEX);
        EditSemLimitDescriptor descriptor = new EditSemLimitDescriptorBuilder()
                .withMaxCap(VALID_MAX_CAP).build();
        SetSemLimitCommand editCommand = new SetSemLimitCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_SEMESTER_LIMIT);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        SemLimit editedSemLimit = new SemLimitBuilder().build();
        EditSemLimitDescriptor descriptor = new EditSemLimitDescriptorBuilder(editedSemLimit).build();
        SetSemLimitCommand editCommand = new SetSemLimitCommand(
                Index.fromZeroBased(INDEX_FIRST_SEMESTER.getIndex()), descriptor);
        Model expectedModel = new ModelManager(new GradTrak(model.getGradTrak()), new UserPrefs(),
                                               new ModuleInfoList(), new CourseList(), new UserInfo());
        expectedModel.setSemesterLimit(0, editedSemLimit);
        expectedModel.commitGradTrak();

        // edit -> first semLimit edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered semLimit list to show all persons
        expectedModel.undoGradTrak();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first semLimit edited again
        expectedModel.redoGradTrak();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(INVALID_SEMESTER_INDEX);
        EditSemLimitDescriptor descriptor = new EditSemLimitDescriptorBuilder()
                .withMaxCap(VALID_MAX_CAP).build();
        SetSemLimitCommand setSemLimitCommand = new SetSemLimitCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(setSemLimitCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_SEMESTER_LIMIT);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final SetSemLimitCommand standardCommand = new SetSemLimitCommand(
                Index.fromZeroBased(INDEX_FIRST_SEMESTER.getIndex()), DESC_Y2S2);

        // same values -> returns true
        EditSemLimitDescriptor copyDescriptor = new EditSemLimitDescriptor(DESC_Y2S2);
        SetSemLimitCommand commandWithSameValues = new SetSemLimitCommand(
                Index.fromZeroBased(INDEX_FIRST_SEMESTER.getIndex()), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new SetSemLimitCommand(
                Index.fromZeroBased(INDEX_SECOND_SEMESTER.getIndex()), DESC_Y2S2)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new SetSemLimitCommand(
                Index.fromZeroBased(INDEX_FIRST_SEMESTER.getIndex()), DESC_Y1S1)));
    }

}
