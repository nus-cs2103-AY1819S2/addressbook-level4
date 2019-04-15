package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddProjectTaskToCommand;
import seedu.address.logic.commands.AddToCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectTask;
import seedu.address.testutil.ProjectUtil;
import seedu.address.testutil.TypicalIndexes;
import seedu.address.testutil.TypicalMilestones;
import seedu.address.testutil.TypicalProjectTasks;
import seedu.address.testutil.TypicalProjects;

public class AddProjectTaskToCommandSystemTest extends PocketProjectSystemTest {

    @Test
    public void addProjectTaskTo() {
        Model model = getProjectModel();
        String command;

        /* ------------------------ Perform addto operations on the shown unfiltered list -------------------------- */

        /* Case: add a project task to a project milestone in the non-empty pocket project, command with leading spaces
         * and trailing spaces
         */

        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Milestone milestone = TypicalMilestones.TYPICAL_MILESTONE_START;
        Index index = TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE;
        ProjectTask task = TypicalProjectTasks.PROJECT_TASK_ONGOING;
        ProjectTask taskDifferentStatus = TypicalProjectTasks.PROJECT_TASK_ONGOING_STATUS_CHANGE;
        assertCommandSuccess(targetProject, milestone, index, task);

        /* Case: undo adding task to milestone 1 in Project Alice */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding task to the list -> task added again */
        command = RedoCommand.COMMAND_WORD;
        model.addProjectTaskTo(targetProject, milestone, task);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* ----------------------------------- Perform invalid addto operations ------------------------------------- */
        /* Case: add a duplicate task to a milestone -> rejected */
        command = ProjectUtil.getAddProjectTaskToCommand(targetProject, index, task);
        assertCommandFailure(command, Messages.MESSAGE_DUPLICATE_PROJECT_TASK);

        /* Case: add a duplicate task with different status to a milestone -> rejected */
        command = ProjectUtil.getAddProjectTaskToCommand(targetProject, index, taskDifferentStatus);
        assertCommandFailure(command, Messages.MESSAGE_DUPLICATE_PROJECT_TASK);

        /* Case: missing command word -> rejected */
        command = targetProject.getProjectName() + " " + AddProjectTaskToCommand.ADD_PROJECTTASK_KEYWORD + " "
                + ProjectUtil.getProjectTaskDetails(TypicalProjectTasks.PROJECT_TASK_COMPLETED, index);
        assertCommandFailure(command, String.format(Messages.MESSAGE_UNKNOWN_COMMAND));

        /* Case: missing project name -> rejected */
        command = AddToCommand.COMMAND_WORD + " " + AddProjectTaskToCommand.ADD_PROJECTTASK_KEYWORD + " "
                + ProjectUtil.getProjectTaskDetails(TypicalProjectTasks.PROJECT_TASK_COMPLETED, index);
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToCommand.MESSAGE_USAGE));

        /* Case: missing project task keyword -> rejected */
        command = AddToCommand.COMMAND_WORD + " " + targetProject.getProjectName() + " "
                + ProjectUtil.getProjectTaskDetails(TypicalProjectTasks.PROJECT_TASK_COMPLETED, index);
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToCommand.MESSAGE_USAGE));

        /* Case: missing project task details -> rejected */
        command = AddToCommand.COMMAND_WORD + " " + targetProject.getProjectName() + " "
                + AddProjectTaskToCommand.ADD_PROJECTTASK_KEYWORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the {@code AddProjectTaskToCommand} that adds {@code task} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddProjectTaskToCommand} with the details
     * of {@code task}.<br>
     * 4. {@code Storage} equal to the corresponding components in
     * the current model added with {@code task}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */

    private void assertCommandSuccess(Project targetProject, Milestone milestone, Index index, ProjectTask task) {
        assertCommandSuccess(ProjectUtil.getAddProjectTaskToCommand(targetProject, index, task),
                targetProject, milestone, index, task);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Project, Milestone, ProjectTask)}.
     * Executes {@code command} instead. @see AddProjectTaskToCommandSystemTest#assertCommandSuccess(Project, Milestone,
     * Index, ProjectTask)
     */

    private void assertCommandSuccess(String command, Project targetProject, Milestone milestone, Index index,
                                      ProjectTask task) {
        Model expectedModel = getProjectModel();
        expectedModel.addProjectTaskTo(targetProject, milestone, task);
        String expectedResultMessage = String.format(AddProjectTaskToCommand.MESSAGE_ADD_PROJECT_TASK_SUCCESS,
            task, index.getOneBased(), targetProject.getProjectName());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Project, Milestone, Index, ProjectTask)}
     * except asserts that the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddProjectTaskToCommandSystemTest#assertCommandSuccess(String, Project, Milestone, Index, ProjectTask)
     */

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */

    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }






}

