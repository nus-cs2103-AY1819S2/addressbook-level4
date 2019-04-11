package seedu.finance.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTrackerWithoutBudget;

import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;
import seedu.finance.model.budget.Budget;

public class SetCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTrackerWithoutBudget(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_financeTrackerWithoutBudget() {
        final String amount = "500.50";
        final Budget budget = new Budget(Double.parseDouble(amount));
        //model.addBudget(budget);
        SetCommand setCommand = new SetCommand(amount);
        String expectedMessage = String.format(SetCommand.MESSAGE_SUCCESS, amount);
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.addBudget(budget);
        expectedModel.commitFinanceTracker();
        assertCommandSuccess(setCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_financeTrackerWithBudget() {
        final String amount = "500.50";
        model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        SetCommand setCommand = new SetCommand(amount);
        assertCommandFailure(setCommand, model, commandHistory, SetCommand.MESSAGE_DUPLICATE_BUDGET);
    }

    @Test
    public void equals() {
        final String amount = "500.50";
        final SetCommand standardCommand = new SetCommand(amount);

        //same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        //null -> returns false
        assertFalse(standardCommand.equals(null));

        //same amount -> returns true
        assertTrue(standardCommand.equals(new SetCommand(amount)));

        //different amount -> returns false
        assertFalse(standardCommand.equals(new SetCommand("200")));
    }
}