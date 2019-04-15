package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProjects.PROJECT_CARL;
import static seedu.address.testutil.TypicalProjects.PROJECT_ELLE;
import static seedu.address.testutil.TypicalProjects.PROJECT_FIONA;
import static seedu.address.testutil.TypicalProjects.getTypicalPocketProjectWithProjects;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindProjectCommand}.
 */
public class FindProjectCommandTest {

    private Model model = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPocketProjectWithProjects(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        ProjectNameContainsKeywordsPredicate firstPredicate =
            new ProjectNameContainsKeywordsPredicate(Collections.singletonList("first"));
        ProjectNameContainsKeywordsPredicate secondPredicate =
            new ProjectNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindProjectCommand findFirstCommand = new FindProjectCommand(firstPredicate);
        FindProjectCommand findSecondCommand = new FindProjectCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindProjectCommand findFirstCommandCopy = new FindProjectCommand(firstPredicate);
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
        ProjectNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindProjectCommand command = new FindProjectCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredProjectList());
    }

    @Test
    public void execute_multipleKeywords_multipleProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 3);
        ProjectNameContainsKeywordsPredicate predicate = preparePredicate(
                "Carl Elle fiona");
        FindProjectCommand command = new FindProjectCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PROJECT_CARL, PROJECT_ELLE, PROJECT_FIONA), model.getFilteredProjectList());
    }

    /**
     * Parses {@code userInput} into a {@code EmployeeNameContainsKeywordsPredicate}.
     */
    private ProjectNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ProjectNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
