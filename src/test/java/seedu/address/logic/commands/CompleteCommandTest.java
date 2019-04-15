package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalProjects.getTypicalPocketProjectWithProjects;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.model.util.PocketProjectDate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CompleteCommand}.
 */
public class CompleteCommandTest {
    private static final PocketProjectDate VALID_DATE = new PocketProjectDate("11/11/2011");
    private Model model = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Project projectToDelete = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        CompleteCommand completeCommand = new CompleteCommand(INDEX_FIRST_PROJECT, VALID_DATE);
        String expectedMessage = String.format(CompleteCommand.MESSAGE_COMPLETE_PROJECT_SUCCESS, projectToDelete);

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.completeProject(projectToDelete, VALID_DATE);
        expectedModel.commitPocketProject();

        assertCommandSuccess(completeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        CompleteCommand completeCommand = new CompleteCommand(outOfBoundIndex, VALID_DATE);

        assertCommandFailure(completeCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CompleteCommand completeFirstCommand = new CompleteCommand(
                INDEX_FIRST_PROJECT, VALID_DATE);
        CompleteCommand completeSecondCommand = new CompleteCommand(
                INDEX_SECOND_PROJECT, VALID_DATE);

        // same object -> returns true
        assertTrue(completeFirstCommand.equals(completeFirstCommand));

        // same values -> returns true
        CompleteCommand completeFirstCommandCopy = new CompleteCommand(
                INDEX_FIRST_PROJECT, VALID_DATE);
        assertTrue(completeFirstCommand.equals(completeFirstCommandCopy));

        // different types -> returns false
        assertFalse(completeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(completeFirstCommand.equals(null));

        // different project -> returns false
        assertFalse(completeFirstCommand.equals(completeSecondCommand));
    }

}
