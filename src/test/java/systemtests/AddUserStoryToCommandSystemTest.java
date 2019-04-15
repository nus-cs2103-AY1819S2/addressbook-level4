package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddToCommand;
import seedu.address.logic.commands.AddUserStoryToCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.project.UserStory;
import seedu.address.testutil.ProjectUtil;
import seedu.address.testutil.TypicalProjects;
import seedu.address.testutil.TypicalUserStories;

public class AddUserStoryToCommandSystemTest extends PocketProjectSystemTest {

    @Test
    public void addUserStoryTo() {
        Model model = getProjectModel();
        String command;

        /* ------------------------ Perform addto operations on the shown unfiltered list -------------------------- */

        /* Case: add a user story to a project in the non-empty pocket project, command with leading spaces and trailing
         * spaces
         */

        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        UserStory userStory = TypicalUserStories.USER_STORY_MANY_PROJECTS_MANAGER;
        assertCommandSuccess(targetProject, userStory);

        /* Case: undo adding user story to Project Alice */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding user story to the list -> user story added again */
        command = RedoCommand.COMMAND_WORD;
        model.addUserStoryTo(targetProject, userStory);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* ----------------------------------- Perform invalid addto operations ------------------------------------- */
        /* Case: add a duplicate user story to a project -> rejected */
        command = ProjectUtil.getAddUserStoryToCommand(targetProject, userStory);
        assertCommandFailure(command, AddUserStoryToCommand.MESSAGE_DUPLICATE_USER_STORY);

        /* Case: missing command word -> rejected */
        command = targetProject.getProjectName() + " " + AddUserStoryToCommand.ADD_USERSTORY_KEYWORD + " "
                + ProjectUtil.getUserStoryDetails(TypicalUserStories.USER_STORY_MANY_EMPLOYEES_MANAGER);
        assertCommandFailure(command, String.format(Messages.MESSAGE_UNKNOWN_COMMAND));

        /* Case: missing project name -> rejected */
        command = AddToCommand.COMMAND_WORD + " " + AddUserStoryToCommand.ADD_USERSTORY_KEYWORD + " "
                + ProjectUtil.getUserStoryDetails(TypicalUserStories.USER_STORY_MANY_EMPLOYEES_MANAGER);;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToCommand.MESSAGE_USAGE));

        /* Case: missing userstory keyword -> rejected */
        command = AddToCommand.COMMAND_WORD + " " + targetProject.getProjectName() + " "
                + ProjectUtil.getUserStoryDetails(TypicalUserStories.USER_STORY_SECOND_MANAGER);;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToCommand.MESSAGE_USAGE));

        /* Case: missing user story details -> rejected */
        command = AddToCommand.COMMAND_WORD + " " + targetProject.getProjectName() + " "
                + AddUserStoryToCommand.ADD_USERSTORY_KEYWORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the {@code AddUserStoryToCommand} that adds {@code UserStory} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddUserStoryToCommand} with the details of
     * {@code UserStory}.<br>
     * 4. {@code Storage} equal to the corresponding components in
     * the current model added with {@code UserStory}.<br>
     * 5. details panel and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */

    private void assertCommandSuccess(Project targetProject, UserStory userStory) {
        assertCommandSuccess(ProjectUtil.getAddUserStoryToCommand(targetProject, userStory), targetProject, userStory);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Project, UserStory)}. Executes {@code command}
     * instead. @see AddUserStoryToCommandSystemTest#assertCommandSuccess(Project, UserStory)
     */

    private void assertCommandSuccess(String command, Project targetProject, UserStory userStory) {
        Model expectedModel = getProjectModel();
        expectedModel.addUserStoryTo(targetProject, userStory);
        String expectedResultMessage = String.format(AddUserStoryToCommand.MESSAGE_ADD_USER_STORY_SUCCESS,
                userStory, targetProject.getProjectName());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Project, UserStory)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddUserStoryToCommandSystemTest#assertCommandSuccess(String, Project, UserStory)
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
     * 5. Details panel, selected card and status bar remain unchanged.<br>
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
