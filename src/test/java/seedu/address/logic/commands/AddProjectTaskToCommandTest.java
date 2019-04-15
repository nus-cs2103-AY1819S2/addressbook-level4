package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddProjectTaskToCommand.MESSAGE_ADD_PROJECT_TASK_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE;
import static seedu.address.testutil.TypicalMilestones.TYPICAL_MILESTONE_START;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.ProjectTaskDescription;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalProjects;

public class AddProjectTaskToCommandTest {

    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validProjectTaskNameValidIndex_success() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        ProjectName projectName = targetProject.getProjectName();
        ProjectTask task = new ProjectTask(new ProjectTaskDescription("Something for the future"));
        AddProjectTaskToCommand addProjectTaskToCommand = new AddProjectTaskToCommand(projectName, task,
                INDEX_FIRST_PROJECT_MILESTONE);
        String expectedMessage = String.format(MESSAGE_ADD_PROJECT_TASK_SUCCESS, task,
                INDEX_FIRST_PROJECT_MILESTONE.getOneBased(), projectName);

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.addProjectTaskTo(targetProject, TYPICAL_MILESTONE_START, task);
        expectedModel.commitPocketProject();


        assertCommandSuccess(addProjectTaskToCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidProjectName_throwsCommandException() {
        AddProjectTaskToCommand addProjectTaskToCommand = new AddProjectTaskToCommand(new ProjectName("INVALID"),
                new ProjectTask(new ProjectTaskDescription("Something happened!")), INDEX_FIRST_PROJECT_MILESTONE);
        assertCommandFailure(addProjectTaskToCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void execute_invalidIndexValidProjectName_throwsCommandException() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index outOfBoundIndex = Index.fromOneBased(targetProject.getMilestones().size() + 1);
        AddProjectTaskToCommand addProjectTaskToCommand = new AddProjectTaskToCommand(targetProject.getProjectName(),
                new ProjectTask(new ProjectTaskDescription("Something happened!")), outOfBoundIndex);

        assertCommandFailure(addProjectTaskToCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ProjectTask newTask = new ProjectTask(new ProjectTaskDescription("Testing"));
        final AddProjectTaskToCommand standardCommand = new AddProjectTaskToCommand(
                TypicalProjects.PROJECT_ALICE.getProjectName(), newTask, Index.fromOneBased(1));

        // same values -> returns true
        AddProjectTaskToCommand commandWithSameValues = new AddProjectTaskToCommand(
                TypicalProjects.PROJECT_ALICE.getProjectName(), newTask, Index.fromOneBased(1));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddProjectTaskToCommand(TypicalProjects.PROJECT_ALICE.getProjectName(),
                newTask, Index.fromOneBased(2))));

        // different project -> returns false
        assertFalse(standardCommand.equals(new AddProjectTaskToCommand(TypicalProjects.PROJECT_BENSON.getProjectName(),
                newTask, Index.fromOneBased(1))));
    }
}
