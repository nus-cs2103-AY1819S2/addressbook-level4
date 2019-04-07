package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProjects.PROJECT_BENSON;
import static seedu.address.testutil.TypicalProjects.PROJECT_CARL;
import static seedu.address.testutil.TypicalProjects.getTypicalPocketProjectWithProjects;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.ProjectContainsDeadlinePredicate;

public class FindDeadlineCommandTest {

    private Model model = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        ProjectContainsDeadlinePredicate firstPredicate = new ProjectContainsDeadlinePredicate("01/01/2001");
        ProjectContainsDeadlinePredicate secondPredicate = new ProjectContainsDeadlinePredicate("02/01/2001");

        FindDeadlineCommand findFirstCommand = new FindDeadlineCommand(firstPredicate);
        FindDeadlineCommand findSecondCommand = new FindDeadlineCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDeadlineCommand findFirstCommandCopy = new FindDeadlineCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different project -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 2);
        ProjectContainsDeadlinePredicate predicate = new ProjectContainsDeadlinePredicate("12/12/2012");
        FindDeadlineCommand command = new FindDeadlineCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PROJECT_BENSON, PROJECT_CARL),
            model.getFilteredProjectList());
    }
}
