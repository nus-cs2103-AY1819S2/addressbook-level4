package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UpdateProjectTaskCommand.MESSAGE_UPDATE_PROJECTTASK_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT_MILESTONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT_TASK;
import static seedu.address.testutil.TypicalProjects.PROJECT_ALICE;
import static seedu.address.testutil.TypicalProjects.PROJECT_BENSON;
import static seedu.address.testutil.TypicalStatuses.STATUS_ONGOING;

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
import seedu.address.model.project.Status;
import seedu.address.testutil.TestUtil;

public class UpdateProjectTaskCommandTest {

    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validProjectNameValidIndexesValidStatus_success() {
        Project targetProject = model.getProjectWithName(PROJECT_ALICE.getProjectName());
        ProjectName projectName = targetProject.getProjectName();
        Milestone targetMilestone = targetProject.getMilestones().get(INDEX_FIRST_PROJECT_MILESTONE.getZeroBased());
        ProjectTask targetTask = targetMilestone.getProjectTaskList().get(INDEX_FIRST_PROJECT_TASK.getZeroBased());

        UpdateProjectTaskCommand updateProjectTaskCommand = new UpdateProjectTaskCommand(projectName,
                INDEX_FIRST_PROJECT_MILESTONE, INDEX_FIRST_PROJECT_TASK, STATUS_ONGOING);
        String expectedMessage = String.format(MESSAGE_UPDATE_PROJECTTASK_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.updateProjectTask(targetProject, targetMilestone, targetTask, STATUS_ONGOING);
        expectedModel.commitPocketProject();

        assertCommandSuccess(updateProjectTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidProjectNameValidMilestoneIndexValidTaskIndex_throwsCommandException() {
        UpdateProjectTaskCommand updateProjectTaskCommand = new UpdateProjectTaskCommand(
                new ProjectName("INVALID"), INDEX_FIRST_PROJECT_MILESTONE, INDEX_FIRST_PROJECT_TASK, STATUS_ONGOING);
        assertCommandFailure(updateProjectTaskCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void execute_invalidMilestoneIndexValidProjectNameValidTaskIndex_throwsCommandException() {
        Project targetProject = model.getProjectWithName(PROJECT_ALICE.getProjectName());
        Index outOfBoundIndex = Index.fromOneBased(targetProject.getMilestones().size() + 1);
        UpdateProjectTaskCommand updateProjectTaskCommand = new UpdateProjectTaskCommand(targetProject.getProjectName(),
                outOfBoundIndex, INDEX_FIRST_PROJECT_TASK, STATUS_ONGOING);

        assertCommandFailure(updateProjectTaskCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTaskIndexValidProjectNameValidMilestoneIndex_throwsCommandException() {
        Project targetProject = model.getProjectWithName(PROJECT_ALICE.getProjectName());
        Index milestoneIndex = INDEX_FIRST_PROJECT_MILESTONE;
        Milestone targetMilestone = targetProject.getMilestones().get(milestoneIndex.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(targetMilestone.getProjectTaskList().size() + 1);
        UpdateProjectTaskCommand updateProjectTaskCommand = new UpdateProjectTaskCommand(
                targetProject.getProjectName(), milestoneIndex, outOfBoundIndex, STATUS_ONGOING);

        assertCommandFailure(updateProjectTaskCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECTTASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UpdateProjectTaskCommand updateProjectTaskCommand = new UpdateProjectTaskCommand(
                PROJECT_ALICE.getProjectName(), INDEX_FIRST_PROJECT_MILESTONE, INDEX_FIRST_PROJECT_TASK,
                STATUS_ONGOING);
        UpdateProjectTaskCommand updateProjectTaskCommand1 = new UpdateProjectTaskCommand(
                PROJECT_BENSON.getProjectName(), INDEX_FIRST_PROJECT_MILESTONE, INDEX_FIRST_PROJECT_TASK,
                STATUS_ONGOING);

        // same object -> returns true
        assertTrue(updateProjectTaskCommand.equals(updateProjectTaskCommand));

        // same values -> returns true
        UpdateProjectTaskCommand updateProjectTaskCommandCopy = new UpdateProjectTaskCommand(
                PROJECT_ALICE.getProjectName(), INDEX_FIRST_PROJECT_MILESTONE, INDEX_FIRST_PROJECT_TASK,
                STATUS_ONGOING);
        assertTrue(updateProjectTaskCommand.equals(updateProjectTaskCommandCopy));

        // different types -> returns false
        assertFalse(updateProjectTaskCommand.equals(1));

        // null -> returns false
        assertFalse(updateProjectTaskCommand.equals(null));

        // different projects -> returns false
        assertFalse(updateProjectTaskCommand.equals(updateProjectTaskCommand1));

        // different indices -> returns false
        UpdateProjectTaskCommand updateProjectTaskCommand2 = new UpdateProjectTaskCommand(
                PROJECT_ALICE.getProjectName(), INDEX_SECOND_PROJECT_MILESTONE, INDEX_FIRST_PROJECT_TASK,
                STATUS_ONGOING);
        assertFalse(updateProjectTaskCommand.equals(updateProjectTaskCommand2));
        UpdateProjectTaskCommand updateProjectTaskCommand3 = new UpdateProjectTaskCommand(
                PROJECT_ALICE.getProjectName(), INDEX_FIRST_PROJECT_MILESTONE, INDEX_SECOND_PROJECT_TASK,
                STATUS_ONGOING);
        assertFalse(updateProjectTaskCommand.equals(updateProjectTaskCommand3));

        //different status -> returns false
        Status newStatus1 = new Status("complete");
        UpdateProjectTaskCommand updateProjectTaskCommand4 = new UpdateProjectTaskCommand(
                PROJECT_ALICE.getProjectName(), INDEX_FIRST_PROJECT_MILESTONE, INDEX_FIRST_PROJECT_TASK, newStatus1);
        assertFalse(updateProjectTaskCommand.equals(updateProjectTaskCommand4));

    }
}
