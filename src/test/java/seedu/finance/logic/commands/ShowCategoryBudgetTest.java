package seedu.finance.logic.commands;

import static seedu.finance.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTrackerWithCatBudget;

import java.util.Iterator;

import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;
import seedu.finance.model.budget.CategoryBudget;

public class ShowCategoryBudgetTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyCatBudget_showEmptyCatBudgetMessage() {
        Model expectedModel = new ModelManager();
        assertCommandSuccess(new ShowCategoryBudgetCommand(), expectedModel, commandHistory,
                ShowCategoryBudgetCommand.MESSAGE_CATEGORY_BUDGET_EMPTY, expectedModel);


    }

    @Test
    public void execute_containCatBudget_showCorrectMessageInResultDisplay() {
        Model oldModel = new ModelManager(getTypicalFinanceTrackerWithCatBudget(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithCatBudget(), new UserPrefs());
        Iterator it = expectedModel.getCatBudget().iterator();
        CategoryBudget catBudget = (CategoryBudget) it.next();
        assertCommandSuccess(new ShowCategoryBudgetCommand(), oldModel, commandHistory,
                ShowCategoryBudgetCommand.MESSAGE_SUCCESS + "\n" + catBudget.toString(), expectedModel);
    }
}
