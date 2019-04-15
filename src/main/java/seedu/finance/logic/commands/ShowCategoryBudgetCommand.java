package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Iterator;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.budget.CategoryBudget;

/**
 * Shows the allocated category budgets on the result display.
 */
public class ShowCategoryBudgetCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String COMMAND_ALIAS = "showCatBudget";

    public static final String MESSAGE_SUCCESS = "==== Listing category budgets ====";

    public static final String MESSAGE_CATEGORY_BUDGET_EMPTY = "You have not allocated budget to any category!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        HashSet<CategoryBudget> catBudgets = model.getCatBudget();
        if (catBudgets.isEmpty()) {
            return new CommandResult(MESSAGE_CATEGORY_BUDGET_EMPTY);
        }
        CategoryBudget cBudget;
        Iterator<CategoryBudget> it = catBudgets.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS + "\n");
        while (it.hasNext()) {
            cBudget = it.next();
            String category = cBudget.toString();
            sb.append(category);
        }
        return new CommandResult(sb.toString());
    }
}
