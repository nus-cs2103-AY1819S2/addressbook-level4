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
import seedu.address.model.project.Milestone;
import seedu.address.model.project.MilestoneDescription;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.util.PocketProjectDate;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalProjects;


/**
 * Contains unit tests for
 * {@code AddMilestoneToCommand}.
 */
public class AddMilestoneToCommandTest {

    private Model model = new ModelManager(TestUtil.typicalPocketProject(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validProjectNameValidIndexValidDate_success() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Milestone milestone = new Milestone(new MilestoneDescription("Completed UG"),
            new PocketProjectDate("05/05/2012"));
        AddMilestoneToCommand addMilestoneToCommand = new AddMilestoneToCommand(targetProject.getProjectName(),
            milestone);
        String expectedMessage = String.format(AddMilestoneToCommand.MESSAGE_ADD_MILESTONE_SUCCESS,
                milestone, targetProject.getProjectName());

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.addMilestoneTo(targetProject, milestone);
        expectedModel.commitPocketProject();

        assertCommandSuccess(addMilestoneToCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidProjectName_throwsCommandException() {
        AddMilestoneToCommand addMilestoneToCommand = new AddMilestoneToCommand(new ProjectName("INVALID"), new
            Milestone(new MilestoneDescription("Updated UG"), new PocketProjectDate("23/06/2019")));
        assertCommandFailure(addMilestoneToCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PROJECT_NAME);
    }

    @Test
    public void execute_duplicateMilestone_throwsCommandException() {

        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Milestone milestone = new Milestone(new MilestoneDescription("Completed UG"),
            new PocketProjectDate("05/05/2012"));
        AddMilestoneToCommand addMilestoneToCommand = new AddMilestoneToCommand(targetProject.getProjectName(),
            milestone);
        String expectedMessage = String.format(AddMilestoneToCommand.MESSAGE_ADD_MILESTONE_SUCCESS,
                milestone, targetProject.getProjectName());

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.addMilestoneTo(targetProject, milestone);
        expectedModel.commitPocketProject();

        assertCommandSuccess(addMilestoneToCommand, model, commandHistory, expectedMessage, expectedModel);

        Milestone duplicateMilestone = new Milestone(new MilestoneDescription("Completed UG"),
            new PocketProjectDate("05/05/2012"));
        AddMilestoneToCommand addMilestoneToCommand2 = new AddMilestoneToCommand(targetProject.getProjectName(),
            duplicateMilestone);
        assertCommandFailure(addMilestoneToCommand2, model, commandHistory,
            Messages.MESSAGE_DUPLICATE_MILESTONE);
    }

    @Test
    public void execute_invalidDate_throwsCommandException() {
        Project targetProject = model.getProjectWithName(TypicalProjects.PROJECT_ALICE.getProjectName());
        Milestone milestone = new Milestone(new MilestoneDescription("Completed UG"),
            new PocketProjectDate("05/05/2008"));
        AddMilestoneToCommand addMilestoneToCommand = new AddMilestoneToCommand(targetProject.getProjectName(),
            milestone);

        assertCommandFailure(addMilestoneToCommand, model, commandHistory,
            Messages.INVALID_MILESTONE_DATE);
    }

    @Test
    public void equals() {
        AddMilestoneToCommand addMilestoneToCommandOne = new AddMilestoneToCommand(TypicalProjects
            .PROJECT_ALICE.getProjectName(), new Milestone(new MilestoneDescription("Completed"),
                 new PocketProjectDate("22/05/2019")));
        AddMilestoneToCommand addMilestoneToCommandTwo = new AddMilestoneToCommand(TypicalProjects
            .PROJECT_BENSON.getProjectName(), new Milestone(new MilestoneDescription("Completed"),
                 new PocketProjectDate("30/11/2019")));

        // same object -> returns true
        assertTrue(addMilestoneToCommandOne.equals(addMilestoneToCommandOne));

        // same values -> returns true
        AddMilestoneToCommand addMilestoneToCommandOneCopy = new AddMilestoneToCommand((TypicalProjects
            .PROJECT_ALICE.getProjectName()), new Milestone(new MilestoneDescription("Completed"),
                 new PocketProjectDate("22/05/2019")));
        assertTrue(addMilestoneToCommandOne.equals(addMilestoneToCommandOneCopy));

        // different types -> returns false
        assertFalse(addMilestoneToCommandOne.equals(1));

        // null -> returns false
        assertFalse(addMilestoneToCommandOne.equals(null));

        // different projects -> returns false
        assertFalse(addMilestoneToCommandOne.equals(addMilestoneToCommandTwo));

        // different dates -> returns false
        AddMilestoneToCommand addMilestoneToCommandThree = new AddMilestoneToCommand(TypicalProjects
            .PROJECT_ALICE.getProjectName(), new Milestone(new MilestoneDescription("Completed"),
                 new PocketProjectDate("23/05/2019")));
        assertFalse(addMilestoneToCommandOne.equals(addMilestoneToCommandThree));

        //different description -> returns false
        AddMilestoneToCommand addMilestoneToCommandFour = new AddMilestoneToCommand(TypicalProjects
            .PROJECT_ALICE.getProjectName(), new Milestone(new MilestoneDescription("Completed Everything"),
                 new PocketProjectDate("22/05/2019")));
        assertFalse(addMilestoneToCommandOne.equals(addMilestoneToCommandFour));
    }
}
