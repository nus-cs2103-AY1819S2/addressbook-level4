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
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.Status;
import seedu.address.model.project.UserStory;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalProjects;

public class UpdateUserStoryCommandTest {

    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Status newStatus = new Status("on hold");

    @Test
    public void execute_validProjectNameValidIndex_success() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index validIndex = Index.fromOneBased(targetProject.getUserStories().size());
        UpdateUserStoryCommand updateUserStoryCommand = new UpdateUserStoryCommand(
                targetProject.getProjectName(), validIndex, newStatus);
        UserStory targetUserStory = targetProject.getUserStories().get(validIndex.getZeroBased());
        String expectedMessage = String.format(UpdateUserStoryCommand.MESSAGE_UPDATE_STORY_SUCCESS,
                targetUserStory, targetProject.getProjectName());

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.updateUserStory(targetProject, targetUserStory, newStatus);
        expectedModel.commitPocketProject();

        assertCommandSuccess(updateUserStoryCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidProjectName_throwsCommandException() {
        UpdateUserStoryCommand updateUserStoryCommand = new UpdateUserStoryCommand(new ProjectName("INVALID"),
                Index.fromOneBased(1), newStatus);
        assertCommandFailure(updateUserStoryCommand, model, commandHistory, Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void execute_invalidIndexValidProjectName_throwsCommandException() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index outOfBoundIndex = Index.fromOneBased(targetProject.getUserStories().size() + 1);
        UpdateUserStoryCommand updateUserStoryCommand = new UpdateUserStoryCommand(targetProject.getProjectName(),
                outOfBoundIndex, newStatus);

        assertCommandFailure(updateUserStoryCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_USERSTORY_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        UpdateUserStoryCommand updateUserStoryCommand1 = new UpdateUserStoryCommand(
                TypicalProjects.PROJECT_ALICE.getProjectName(), Index.fromOneBased(1), newStatus);
        UpdateUserStoryCommand updateUserStoryCommand2 = new UpdateUserStoryCommand(
                TypicalProjects.PROJECT_BENSON.getProjectName(), Index.fromOneBased(1), newStatus);

        // same object -> returns true
        assertTrue(updateUserStoryCommand1.equals(updateUserStoryCommand1));

        // same values -> returns true
        UpdateUserStoryCommand updateUserStoryCommand1Copy = new UpdateUserStoryCommand(
                TypicalProjects.PROJECT_ALICE.getProjectName(), Index.fromOneBased(1), newStatus);
        assertTrue(updateUserStoryCommand1.equals(updateUserStoryCommand1Copy));

        // different types -> returns false
        assertFalse(updateUserStoryCommand1.equals(1));

        // null -> returns false
        assertFalse(updateUserStoryCommand1.equals(null));

        // different projects -> returns false
        assertFalse(updateUserStoryCommand1.equals(updateUserStoryCommand2));

        // different indices -> returns false
        UpdateUserStoryCommand updateUserStoryCommand3 = new UpdateUserStoryCommand(
                TypicalProjects.PROJECT_ALICE.getProjectName(), Index.fromOneBased(2), newStatus);
        assertFalse(updateUserStoryCommand1.equals(updateUserStoryCommand3));

        //different status -> returns false
        Status newStatus1 = new Status("complete");
        UpdateUserStoryCommand updateUserStoryCommand4 = new UpdateUserStoryCommand(
                TypicalProjects.PROJECT_ALICE.getProjectName(), Index.fromOneBased(2), newStatus1);
        assertFalse(updateUserStoryCommand1.equals(updateUserStoryCommand4));

    }
}
