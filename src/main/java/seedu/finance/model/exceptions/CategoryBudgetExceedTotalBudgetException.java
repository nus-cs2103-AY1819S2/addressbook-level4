package seedu.finance.model.exceptions;

//@@author Jackimaru96

import seedu.finance.model.budget.Budget;
import seedu.finance.model.budget.CategoryBudget;

/**
 * Exception when category budget exceeds the totalBudget of finance tracker
 */
public class CategoryBudgetExceedTotalBudgetException extends Exception {
    public CategoryBudgetExceedTotalBudgetException(CategoryBudget categoryBudget, Budget totalBudget) {
        super(String.format("Allocating the category budget $%.2f in %s "
                        + "will exceed the total budget of Finance Tracker $%.2f",
                categoryBudget.getTotalBudget(), categoryBudget.getCategory().toString(),
                totalBudget.getTotalBudget()));
    }

    public CategoryBudgetExceedTotalBudgetException(Budget budget, Double categoryBudgetTotal) {
        super(String.format("Setting the budget as $%.2f will cause the allocated category budget of"
                        + " $%.2f to exceed the total budget of the finance tracker.",
                budget.getTotalBudget(), categoryBudgetTotal));
    }

    public CategoryBudgetExceedTotalBudgetException(CategoryBudget categoryBudget) {
        super(String.format("The category budget $(%.2f) will exceed total budget of Finance Tracker"));
    }
}
