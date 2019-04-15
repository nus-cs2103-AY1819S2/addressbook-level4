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
import seedu.address.model.project.UserStory;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalProjects;

/**
 * Contains unit tests for
 * {@code RemoveUserStoryFromCommand}.
 */
public class RemoveUserStoryFromCommandTest {

    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validProjectNameValidIndex_success() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index validIndex = Index.fromOneBased(targetProject.getUserStories().size());
        RemoveUserStoryFromCommand removeUserStoryFromCommand = new RemoveUserStoryFromCommand(validIndex,
                targetProject.getProjectName());
        UserStory targetUserStory = targetProject.getUserStories().get(validIndex.getZeroBased());
        String expectedMessage = String.format(RemoveUserStoryFromCommand.MESSAGE_REMOVE_USERSTORY_SUCCESS,
                targetUserStory, targetProject.getProjectName());

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.removeUserStoryFrom(targetProject, targetUserStory);
        expectedModel.commitPocketProject();

        assertCommandSuccess(removeUserStoryFromCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidProjectName_throwsCommandException() {
        RemoveUserStoryFromCommand removeUserStoryFromCommand = new RemoveUserStoryFromCommand(Index.fromOneBased(1),
                new ProjectName("INVALID"));
        assertCommandFailure(removeUserStoryFromCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void execute_invalidIndexValidProjectName_throwsCommandException() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Index outOfBoundIndex = Index.fromOneBased(targetProject.getMilestones().size() + 1);
        RemoveUserStoryFromCommand removeUserStoryFromCommand = new RemoveUserStoryFromCommand(outOfBoundIndex,
                targetProject.getProjectName());

        assertCommandFailure(removeUserStoryFromCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_USERSTORY_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        RemoveUserStoryFromCommand removeUserStoryFromCommand1 = new RemoveUserStoryFromCommand(Index.fromOneBased(1),
                TypicalProjects.PROJECT_ALICE.getProjectName());
        RemoveUserStoryFromCommand removeUserStoryFromCommand2 = new RemoveUserStoryFromCommand(Index.fromOneBased(1),
                TypicalProjects.PROJECT_BENSON.getProjectName());

        // same object -> returns true
        assertTrue(removeUserStoryFromCommand1.equals(removeUserStoryFromCommand1));

        // same values -> returns true
        RemoveUserStoryFromCommand removeUserStoryFromCommand1Copy = new RemoveUserStoryFromCommand(Index
                .fromOneBased(1),
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertTrue(removeUserStoryFromCommand1.equals(removeUserStoryFromCommand1Copy));

        // different types -> returns false
        assertFalse(removeUserStoryFromCommand1.equals(1));

        // null -> returns false
        assertFalse(removeUserStoryFromCommand1.equals(null));

        // different projects -> returns false
        assertFalse(removeUserStoryFromCommand1.equals(removeUserStoryFromCommand2));

        // different indices -> returns false
        RemoveUserStoryFromCommand removeUserStoryFromCommand3 = new RemoveUserStoryFromCommand(Index.fromOneBased(2),
                TypicalProjects.PROJECT_ALICE.getProjectName());
        assertFalse(removeUserStoryFromCommand1.equals(removeUserStoryFromCommand3));
    }
}
