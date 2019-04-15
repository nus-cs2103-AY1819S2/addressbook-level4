package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PROJECT;
import static seedu.address.testutil.TypicalProjects.getTypicalPocketProjectWithProjects;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewProjectCommand}.
 */
public class ViewProjectCommandTest {
    private Model model = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredProjectList().size());

        assertExecutionSuccess(INDEX_FIRST_PROJECT);
        assertExecutionSuccess(INDEX_THIRD_PROJECT);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);
        showProjectAtIndex(expectedModel, INDEX_FIRST_PROJECT);

        assertExecutionSuccess(INDEX_FIRST_PROJECT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);
        showProjectAtIndex(expectedModel, INDEX_FIRST_PROJECT);

        Index outOfBoundsIndex = INDEX_SECOND_PROJECT;
        // ensures that outOfBoundIndex is still in bounds of pocket project list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getPocketProject().getProjectList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewProjectCommand viewFirstCommand = new ViewProjectCommand(INDEX_FIRST_PROJECT);
        ViewProjectCommand selectSecondCommand = new ViewProjectCommand(INDEX_SECOND_PROJECT);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewProjectCommand selectFirstCommandCopy = new ViewProjectCommand(INDEX_FIRST_PROJECT);
        assertTrue(viewFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(viewFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code ViewProjectCommand} with the given {@code index},
     * and checks that the model's selected project is set to the project at {@code index} in the filtered projects
     * list.
     */
    private void assertExecutionSuccess(Index index) {
        ViewProjectCommand viewProjectCommand = new ViewProjectCommand(index);
        String expectedMessage = String.format(ViewProjectCommand.MESSAGE_VIEW_PROJECT_SUCCESS, index.getOneBased());
        expectedModel.setSelectedProject(model.getFilteredProjectList().get(index.getZeroBased()));

        assertCommandSuccess(viewProjectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code ViewProjectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ViewProjectCommand viewProjectCommand = new ViewProjectCommand(index);
        assertCommandFailure(viewProjectCommand, model, commandHistory, expectedMessage);
    }
}
