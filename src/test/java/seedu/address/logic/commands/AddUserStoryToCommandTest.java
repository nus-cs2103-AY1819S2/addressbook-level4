package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalProjects;

/**
 * Contains unit tests for
 * {@code AddUserStoryToCommand}.
 */
public class AddUserStoryToCommandTest {

    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validProjectNameValidIndex_success() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        UserStory newStory = new UserStory(new UserStoryImportance("2"), new UserStoryUser("manager"),
                new UserStoryFunction("do stuff"), new UserStoryReason("test my code"));
        AddUserStoryToCommand addUserStoryToCommand = new AddUserStoryToCommand(targetProject.getProjectName(),
                newStory);
        String expectedMessage = String.format(AddUserStoryToCommand.MESSAGE_ADD_USER_STORY_SUCCESS,
                newStory, targetProject.getProjectName());

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.addUserStoryTo(targetProject, newStory);
        expectedModel.commitPocketProject();

        assertCommandSuccess(addUserStoryToCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidProjectName_throwsCommandException() {
        AddUserStoryToCommand addUserStoryToCommand = new AddUserStoryToCommand(new ProjectName("INVALID"),
                new UserStory(new UserStoryImportance("2"), new UserStoryUser("invalid manager"),
                new UserStoryFunction("do invalid stuff"), new UserStoryReason("test my invalid code")));
        assertCommandFailure(addUserStoryToCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void equals() {
        AddUserStoryToCommand addUserStoryToCommandOne = new AddUserStoryToCommand(TypicalProjects
                .PROJECT_ALICE.getProjectName(), new UserStory(new UserStoryImportance("1"),
                new UserStoryUser("a manager"), new UserStoryFunction("do stuff"),
                new UserStoryReason("test code")));
        AddUserStoryToCommand addUserStoryToCommandTwo = new AddUserStoryToCommand(TypicalProjects
                .PROJECT_BENSON.getProjectName(), new UserStory(new UserStoryImportance("1"),
                new UserStoryUser("a manager"), new UserStoryFunction("do stuff"),
                new UserStoryReason("test code")));

        // same object -> returns true
        assertTrue(addUserStoryToCommandOne.equals(addUserStoryToCommandOne));

        // same values -> returns true
        AddUserStoryToCommand addUserStoryToCommandOneCopy = new AddUserStoryToCommand(TypicalProjects
                .PROJECT_ALICE.getProjectName(), new UserStory(new UserStoryImportance("1"),
                new UserStoryUser("a manager"), new UserStoryFunction("do stuff"),
                new UserStoryReason("test code")));
        assertTrue(addUserStoryToCommandOne.equals(addUserStoryToCommandOneCopy));

        // different types -> returns false
        assertFalse(addUserStoryToCommandOne.equals(1));

        // null -> returns false
        assertFalse(addUserStoryToCommandOne.equals(null));

        // different projects -> returns false
        assertFalse(addUserStoryToCommandOne.equals(addUserStoryToCommandTwo));

        // different importance -> returns false
        AddUserStoryToCommand addUserStoryToCommandThree = new AddUserStoryToCommand(TypicalProjects
                .PROJECT_ALICE.getProjectName(), new UserStory(new UserStoryImportance("2"),
                new UserStoryUser("a manager"), new UserStoryFunction("do stuff"),
                new UserStoryReason("test code")));
        assertFalse(addUserStoryToCommandOne.equals(addUserStoryToCommandThree));

        // different user -> returns false
        AddUserStoryToCommand addUserStoryToCommandFour = new AddUserStoryToCommand(TypicalProjects
                .PROJECT_ALICE.getProjectName(), new UserStory(new UserStoryImportance("1"),
                new UserStoryUser("a different manager"), new UserStoryFunction("do stuff"),
                new UserStoryReason("test code")));
        assertFalse(addUserStoryToCommandOne.equals(addUserStoryToCommandFour));


        // different function -> returns false
        AddUserStoryToCommand addUserStoryToCommandFive = new AddUserStoryToCommand(TypicalProjects
                .PROJECT_ALICE.getProjectName(), new UserStory(new UserStoryImportance("1"),
                new UserStoryUser("a manager"), new UserStoryFunction("do different stuff"),
                new UserStoryReason("test code")));
        assertFalse(addUserStoryToCommandOne.equals(addUserStoryToCommandFive));


        // different reason -> returns false
        AddUserStoryToCommand addUserStoryToCommandSix = new AddUserStoryToCommand(TypicalProjects
                .PROJECT_ALICE.getProjectName(), new UserStory(new UserStoryImportance("1"),
                new UserStoryUser("a manager"), new UserStoryFunction("do stuff"),
                new UserStoryReason("test different code")));
        assertFalse(addUserStoryToCommandOne.equals(addUserStoryToCommandSix));
    }

}
