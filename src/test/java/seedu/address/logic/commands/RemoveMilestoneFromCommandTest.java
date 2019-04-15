package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalProjects;

/**
 * Contains unit tests for
 * {@code RemoveMilestoneFromCommand}.
 */
public class RemoveMilestoneFromCommandTest {

    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validProjectNameValidIndex_success() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index validIndex = Index.fromOneBased(targetProject.getMilestones().size());
        RemoveMilestoneFromCommand removeMilestoneFromCommand = new RemoveMilestoneFromCommand(validIndex,
                targetProject.getProjectName());
        Milestone targetMilestone = targetProject.getMilestones().get(validIndex.getZeroBased());
        String expectedMessage = String.format(RemoveMilestoneFromCommand.MESSAGE_REMOVE_MILESTONE_SUCCESS,
                targetMilestone, targetProject.getProjectName());

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.removeMilestoneFrom(targetProject, targetMilestone);
        expectedModel.commitPocketProject();

        assertCommandSuccess(removeMilestoneFromCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidProjectName_throwsCommandException() {
        RemoveMilestoneFromCommand removeMilestoneFromCommand = new RemoveMilestoneFromCommand(Index.fromOneBased(1),
                new ProjectName("INVALID"));
        assertCommandFailure(removeMilestoneFromCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void execute_invalidIndexValidProjectName_throwsCommandException() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index outOfBoundIndex = Index.fromOneBased(targetProject.getMilestones().size() + 1);
        RemoveMilestoneFromCommand removeMilestoneFromCommand = new RemoveMilestoneFromCommand(outOfBoundIndex,
                targetProject.getProjectName());

        assertCommandFailure(removeMilestoneFromCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        RemoveMilestoneFromCommand removeMilestoneFromCommand1 = new RemoveMilestoneFromCommand(Index.fromOneBased(1),
                TypicalProjects.PROJECT_ALICE.getProjectName());
        RemoveMilestoneFromCommand removeMilestoneFromCommand2 = new RemoveMilestoneFromCommand(Index.fromOneBased(1),
                TypicalProjects.PROJECT_BENSON.getProjectName());

        // same object -> returns true
        assertTrue(removeMilestoneFromCommand1.equals(removeMilestoneFromCommand1));

        // same values -> returns true
        RemoveMilestoneFromCommand removeMilestoneFromCommand1Copy = new RemoveMilestoneFromCommand(Index
                .fromOneBased(1),
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertTrue(removeMilestoneFromCommand1.equals(removeMilestoneFromCommand1Copy));

        // different types -> returns false
        assertFalse(removeMilestoneFromCommand1.equals(1));

        // null -> returns false
        assertFalse(removeMilestoneFromCommand1.equals(null));

        // different projects -> returns false
        assertFalse(removeMilestoneFromCommand1.equals(removeMilestoneFromCommand2));

        // different indices -> returns false
        RemoveMilestoneFromCommand removeMilestoneFromCommand3 = new RemoveMilestoneFromCommand(Index.fromOneBased(2),
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertFalse(removeMilestoneFromCommand1.equals(removeMilestoneFromCommand3));
    }
}

