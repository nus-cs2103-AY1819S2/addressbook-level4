package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalProjects.getTypicalPocketProjectWithProjects;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.model.util.StatsUtil;
import seedu.address.testutil.TypicalProjectNames;
import seedu.address.testutil.TypicalProjects;

/**
 * Contains unit tests for
 * {@code IndividualStatsCommand}.
 */
public class IndividualStatsCommandTest {

    private Model model = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Project targetProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        IndividualStatsCommand individualStatsCommand = new IndividualStatsCommand(INDEX_FIRST_PROJECT);

        String expectedMessage = StatsUtil.individualStatsString(targetProject);

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());

        assertCommandSuccess(individualStatsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        IndividualStatsCommand individualStatsCommand = new IndividualStatsCommand(outOfBoundIndex);

        assertCommandFailure(individualStatsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }
    @Test
    public void execute_validName_success() {
        Project targetProject = model.getProjectList().get(0);
        IndividualStatsCommand individualStatsCommand = new IndividualStatsCommand(targetProject.getProjectName());

        String expectedMessage = StatsUtil.individualStatsString(targetProject);

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());

        assertCommandSuccess(individualStatsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        IndividualStatsCommand individualStatsCommand = new IndividualStatsCommand(
                TypicalProjectNames.NON_EXISTENT_PROJECT_NAME);

        assertCommandFailure(individualStatsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void equals() {
        IndividualStatsCommand checkAliceCommand = new IndividualStatsCommand(
                TypicalProjects.PROJECT_ALICE.getProjectName());
        IndividualStatsCommand checkBensonCommand = new IndividualStatsCommand(
                TypicalProjects.PROJECT_BENSON.getProjectName());

        // same object -> returns true
        assertTrue(checkAliceCommand.equals(checkAliceCommand));

        // same values -> returns true
        IndividualStatsCommand checkAliceCommandCopy = new IndividualStatsCommand(
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertTrue(checkAliceCommand.equals(checkAliceCommandCopy));

        // different types -> returns false
        assertFalse(checkAliceCommand.equals(1));

        // null -> returns false
        assertFalse(checkAliceCommand.equals(null));

        // different project -> returns false
        assertFalse(checkAliceCommand.equals(checkBensonCommand));
    }

}
