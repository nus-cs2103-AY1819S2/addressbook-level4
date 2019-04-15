package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmployees.CARL;
import static seedu.address.testutil.TypicalEmployees.ELLE;
import static seedu.address.testutil.TypicalEmployees.FIONA;
import static seedu.address.testutil.TypicalEmployees.getTypicalPocketProjectWithEmployees;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.EmployeeNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEmployeeCommand}.
 */
public class FindEmployeeCommandTest {
    private Model model = new ModelManager(getTypicalPocketProjectWithEmployees(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPocketProjectWithEmployees(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        EmployeeNameContainsKeywordsPredicate firstPredicate =
            new EmployeeNameContainsKeywordsPredicate(Collections.singletonList("first"));
        EmployeeNameContainsKeywordsPredicate secondPredicate =
            new EmployeeNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEmployeeCommand findFirstCommand = new FindEmployeeCommand(firstPredicate);
        FindEmployeeCommand findSecondCommand = new FindEmployeeCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEmployeeCommand findFirstCommandCopy = new FindEmployeeCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEmployeeFound() {
        String expectedMessage = String.format(MESSAGE_EMPLOYEES_LISTED_OVERVIEW, 0);
        EmployeeNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindEmployeeCommand command = new FindEmployeeCommand(predicate);
        expectedModel.updateFilteredEmployeeList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEmployeeList());
    }

    @Test
    public void execute_multipleKeywords_multipleEmployeesFound() {
        String expectedMessage = String.format(MESSAGE_EMPLOYEES_LISTED_OVERVIEW, 3);
        EmployeeNameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindEmployeeCommand command = new FindEmployeeCommand(predicate);
        expectedModel.updateFilteredEmployeeList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredEmployeeList());
    }

    /**
     * Parses {@code userInput} into a {@code EmployeeNameContainsKeywordsPredicate}.
     */
    private EmployeeNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EmployeeNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
