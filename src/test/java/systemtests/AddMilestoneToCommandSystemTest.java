package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddMilestoneToCommand;
import seedu.address.logic.commands.AddToCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.testutil.ProjectUtil;
import seedu.address.testutil.TypicalMilestones;
import seedu.address.testutil.TypicalProjects;

public class AddMilestoneToCommandSystemTest extends PocketProjectSystemTest {

    @Test
    public void addMilestoneTo() {
        Model model = getProjectModel();
        String command;

        /* ------------------------ Perform addto operations on the shown unfiltered list -------------------------- */

        /* Case: add a milestone to a project in the non-empty pocket project, command with leading spaces and trailing
         * spaces
         */

        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Milestone milestone = TypicalMilestones.TYPICAL_MILESTONE_COMPLETED_UG;
        assertCommandSuccess(targetProject, milestone);

        /* Case: undo adding milestone to Project Alice */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding milestone to the list -> milestone added again */
        command = RedoCommand.COMMAND_WORD;
        model.addMilestoneTo(targetProject, milestone);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* ----------------------------------- Perform invalid addto operations ------------------------------------- */
        /* Case: add a duplicate milestone to a project -> rejected */
        command = ProjectUtil.getAddMilestoneToCommand(targetProject, milestone);
        assertCommandFailure(command, Messages.MESSAGE_DUPLICATE_MILESTONE);

        /* Case: missing command word -> rejected */
        command = targetProject.getProjectName() + " " + AddMilestoneToCommand.ADD_MILESTONE_KEYWORD + " "
            + ProjectUtil.getMilestoneDetails(TypicalMilestones.TYPICAL_MILESTONE_GUI_V2);
        assertCommandFailure(command, String.format(Messages.MESSAGE_UNKNOWN_COMMAND));

        /* Case: missing project name -> rejected */
        command = AddToCommand.COMMAND_WORD + " " + AddMilestoneToCommand.ADD_MILESTONE_KEYWORD + " "
            + ProjectUtil.getMilestoneDetails(TypicalMilestones.TYPICAL_MILESTONE_GUI_V2);;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToCommand.MESSAGE_USAGE));

        /* Case: missing milestone keyword -> rejected */
        command = AddToCommand.COMMAND_WORD + " " + targetProject.getProjectName() + " "
            + ProjectUtil.getMilestoneDetails(TypicalMilestones.TYPICAL_MILESTONE_GUI_V2);
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToCommand.MESSAGE_USAGE));

        /* Case: missing milestone details -> rejected */
        command = AddToCommand.COMMAND_WORD + " " + targetProject.getProjectName() + " "
            + AddMilestoneToCommand.ADD_MILESTONE_KEYWORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the {@code AddMilestoneToCommand} that adds {@code milestone} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddMilestoneToCommand} with the details of
     * {@code milestone}.<br>
     * 4. {@code Storage} equal to the corresponding components in
     * the current model added with {@code milestone}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */

    private void assertCommandSuccess(Project targetProject, Milestone milestone) {
        assertCommandSuccess(ProjectUtil.getAddMilestoneToCommand(targetProject, milestone), targetProject, milestone);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Project, Milestone)}. Executes {@code command}
     * instead. @see AddMilestoneToCommandSystemTest#assertCommandSuccess(Project, Milestone)
     */

    private void assertCommandSuccess(String command, Project targetProject, Milestone milestone) {
        Model expectedModel = getProjectModel();
        expectedModel.addMilestoneTo(targetProject, milestone);
        String expectedResultMessage = String.format(AddMilestoneToCommand.MESSAGE_ADD_MILESTONE_SUCCESS,
                milestone, targetProject.getProjectName());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Project, Milestone)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddMilestoneToCommandSystemTest#assertCommandSuccess(String, Project, Milestone)
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

