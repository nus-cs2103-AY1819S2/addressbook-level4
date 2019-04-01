package seedu.finance.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.record.Amount;

/**
 * Contains integration tests (interaction with Model) and unit test for AllocateCommand
 */
public class AllocateCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /*@Test
    public void execute_addCategoryBudget_success() {
        final CategoryBudget categoryBudget = new CategoryBudget(VALID_CATEGORY_FRIEND,
                Double.parseDouble(VALID_AMOUNT_AMY));
        final TotalBudget totalBudget = new TotalBudget(500.00);
        model.addBudget(totalBudget);
        AllocateCommand allocateCommand = new AllocateCommand(categoryBudget);
        String expectedMessage = String.format(MESSAGE_SUCCESS, VALID_CATEGORY_FRIEND,
                Double.parseDouble(VALID_AMOUNT_AMY));
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.addBudget(totalBudget);
        try {
            expectedModel.addCategoryBudget(categoryBudget);
        } catch (CategoryBudgetExceedTotalBudgetException e) {
            System.out.println(String.format("The category budget (%.2f) will exceed the total budget of " +
                    "Finance Tracker (%.2f)\n", categoryBudget.getTotalBudget(), totalBudget.getTotalBudget()));
        }

        expectedModel.commitFinanceTracker();
        assertCommandSuccess(allocateCommand, model, commandHistory, expectedMessage, expectedModel);
    }*/

    @Test
    public void equals() {
        final CategoryBudget catBudget = new CategoryBudget(VALID_CATEGORY_FRIEND,
                Double.parseDouble(VALID_AMOUNT_AMY));

        final AllocateCommand standardCommand = new AllocateCommand(catBudget);

        // same category (case-insensitive) -> returns true
        AllocateCommand commandWithSameCategory = new AllocateCommand(new CategoryBudget(
                "fRieND", Double.parseDouble("100.14")));
        assertTrue(standardCommand.equals(commandWithSameCategory));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new IncreaseCommand(new Amount ("312.00"))));

        // different category -> returns false
        assertFalse(standardCommand.equals(new AllocateCommand(new CategoryBudget(VALID_CATEGORY_HUSBAND,
                Double.parseDouble(VALID_AMOUNT_AMY)))));


    }
}
