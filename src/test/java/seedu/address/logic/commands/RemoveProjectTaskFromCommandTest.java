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
import seedu.address.model.project.ProjectTask;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalIndexes;
import seedu.address.testutil.TypicalProjects;

/**
 * Contains unit tests for
 * {@code RemoveProjectTaskFromCommand}.
 */
public class RemoveProjectTaskFromCommandTest {

    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validProjectNameValidIndexes_success() {
        ProjectName projectName = TypicalProjects.PROJECT_ALICE.getProjectName();
        Project targetProject = model.getProjectWithName(projectName);
        Index milestoneIndex = TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE;
        Index taskIndex = TypicalIndexes.INDEX_FIRST_PROJECT_TASK;
        RemoveProjectTaskFromCommand removeProjectTaskFromCommand = new RemoveProjectTaskFromCommand(
                milestoneIndex, taskIndex, projectName);

        Milestone targetMilestone = targetProject.getMilestones().get(milestoneIndex.getZeroBased());
        ProjectTask targetTask = targetMilestone.getProjectTaskList().get(taskIndex.getZeroBased());
        String expectedMessage = String.format(RemoveProjectTaskFromCommand.MESSAGE_REMOVE_PROJECTTASK_SUCCESS,
                targetTask, milestoneIndex.getOneBased(), projectName);

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.removeProjectTaskFrom(targetProject, targetMilestone, targetTask);
        expectedModel.commitPocketProject();

        assertCommandSuccess(removeProjectTaskFromCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidProjectName_throwsCommandException() {
        RemoveProjectTaskFromCommand removeProjectTaskFromCommand = new RemoveProjectTaskFromCommand(
                TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE, TypicalIndexes.INDEX_FIRST_PROJECT_TASK,
                new ProjectName("INVALID"));
        assertCommandFailure(removeProjectTaskFromCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_NAME);
    }
    @Test
    public void execute_invalidMilestoneIndexValidProjectNameValidTaskIndex_throwsCommandException() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index outOfBoundIndex = Index.fromOneBased(targetProject.getMilestones().size() + 1);
        RemoveProjectTaskFromCommand removeProjectTaskFromCommand = new RemoveProjectTaskFromCommand(
                outOfBoundIndex, TypicalIndexes.INDEX_FIRST_PROJECT_TASK, targetProject.getProjectName());

        assertCommandFailure(removeProjectTaskFromCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
    }
    @Test
    public void execute_invalidTaskIndexValidProjectNameValidMilestoneIndex_throwsCommandException() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index milestoneIndex = TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE;
        Milestone targetMilestone = targetProject.getMilestones().get(milestoneIndex.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(targetMilestone.getProjectTaskList().size() + 1);
        RemoveProjectTaskFromCommand removeProjectTaskFromCommand = new RemoveProjectTaskFromCommand(
                milestoneIndex, outOfBoundIndex, targetProject.getProjectName());

        assertCommandFailure(removeProjectTaskFromCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECTTASK_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        RemoveProjectTaskFromCommand removeProjectTaskFromCommand = new RemoveProjectTaskFromCommand(
                TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE, TypicalIndexes.INDEX_FIRST_PROJECT_TASK,
                TypicalProjects.PROJECT_ALICE.getProjectName());
        RemoveProjectTaskFromCommand removeProjectTaskFromCommand1 = new RemoveProjectTaskFromCommand(
                TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE, TypicalIndexes.INDEX_FIRST_PROJECT_TASK,
                TypicalProjects.PROJECT_BENSON.getProjectName());

        // same object -> returns true
        assertTrue(removeProjectTaskFromCommand.equals(removeProjectTaskFromCommand));

        // same values -> returns true
        RemoveProjectTaskFromCommand removeProjectTaskFromCommandCopy = new RemoveProjectTaskFromCommand(
                TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE, TypicalIndexes.INDEX_FIRST_PROJECT_TASK,
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertTrue(removeProjectTaskFromCommand.equals(removeProjectTaskFromCommandCopy));

        // different types -> returns false
        assertFalse(removeProjectTaskFromCommand.equals(1));

        // null -> returns false
        assertFalse(removeProjectTaskFromCommand.equals(null));

        // different projects -> returns false
        assertFalse(removeProjectTaskFromCommand.equals(removeProjectTaskFromCommand1));

        // different indices -> returns false
        RemoveProjectTaskFromCommand removeProjectTaskFromCommand2 = new RemoveProjectTaskFromCommand(
                TypicalIndexes.INDEX_SECOND_PROJECT_MILESTONE, TypicalIndexes.INDEX_FIRST_PROJECT_TASK,
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertFalse(removeProjectTaskFromCommand.equals(removeProjectTaskFromCommand2));
        RemoveProjectTaskFromCommand removeProjectTaskFromCommand3 = new RemoveProjectTaskFromCommand(
                TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE, TypicalIndexes.INDEX_SECOND_PROJECT_TASK,
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertFalse(removeProjectTaskFromCommand.equals(removeProjectTaskFromCommand3));
    }
}

