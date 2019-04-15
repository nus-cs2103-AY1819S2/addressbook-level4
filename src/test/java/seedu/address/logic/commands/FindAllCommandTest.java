package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProjects.PROJECT_ALICE;
import static seedu.address.testutil.TypicalProjects.PROJECT_BENSON;
import static seedu.address.testutil.TypicalProjects.PROJECT_CARL;
import static seedu.address.testutil.TypicalProjects.PROJECT_DANIEL;
import static seedu.address.testutil.TypicalProjects.PROJECT_GEORGE;
import static seedu.address.testutil.TypicalProjects.getTypicalPocketProjectWithProjects;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.ProjectContainsKeywordsPredicate;

public class FindAllCommandTest {

    private Model model = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        ProjectContainsKeywordsPredicate firstPredicate =
            new ProjectContainsKeywordsPredicate(Collections.singletonList("first"));
        ProjectContainsKeywordsPredicate secondPredicate =
            new ProjectContainsKeywordsPredicate(Collections.singletonList("second"));

        FindAllCommand findFirstCommand = new FindAllCommand(firstPredicate);
        FindAllCommand findSecondCommand = new FindAllCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindAllCommand findFirstCommandCopy = new FindAllCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different project -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noProjectFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 0);
        ProjectContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindAllCommand command = new FindAllCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredProjectList());
    }

    @Test
    public void execute_multipleKeywords_multipleProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 5);
        ProjectContainsKeywordsPredicate predicate = preparePredicate(
            "hey FASS software");
        FindAllCommand command = new FindAllCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PROJECT_ALICE, PROJECT_BENSON, PROJECT_CARL, PROJECT_DANIEL, PROJECT_GEORGE),
            model.getFilteredProjectList());
    }

    /**
     * Parses {@code userInput} into a {@code EmployeeContainsKeywordsPredicate}.
     */
    private ProjectContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ProjectContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
