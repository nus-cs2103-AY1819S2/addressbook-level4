package seedu.finance.model.exceptions;

//@@author Jackimaru96

import seedu.finance.model.budget.Budget;
import seedu.finance.model.budget.CategoryBudget;

/**
 * Exception when category budget exceeds the totalBudget of finance tracker
 */
public class CategoryBudgetExceedTotalBudgetException extends Exception {
    public CategoryBudgetExceedTotalBudgetException(CategoryBudget categoryBudget, Budget totalBudget) {
        super(String.format("The category budget (%.2f) will exceed the total budget of Finance Tracker (%.2f)",
                categoryBudget.getTotalBudget(), totalBudget.getTotalBudget()));
    }

    public CategoryBudgetExceedTotalBudgetException(CategoryBudget categoryBudget) {
        super(String.format("The category budget (%.2f) will exceed total budget of Finance Tracker"));
    }
}
