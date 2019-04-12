package seedu.finance.model.exceptions;

//@@author Jackimaru96

import seedu.finance.model.budget.CategoryBudget;

/**
 * Exception when user tries to allocate a category budget where current expenditure
 * in that category exceeds the amount the user wants to allocate to the category
 */
public class SpendingInCategoryBudgetExceededException extends Exception {
    public SpendingInCategoryBudgetExceededException(CategoryBudget categoryBudget) {
        super(String.format("The current spending in %s is $%.2f, which exceeds the allocated $%.2f category budget",
                categoryBudget.getCategory().toString(),
                categoryBudget.getCurrentSpendings(),
                categoryBudget.getTotalBudget()));

    }
}
