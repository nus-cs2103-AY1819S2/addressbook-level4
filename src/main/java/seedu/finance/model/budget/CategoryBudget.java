package seedu.finance.model.budget;

import static seedu.finance.commons.util.AppUtil.checkArgument;

import seedu.finance.model.category.Category;

/**
 * Represents the budget for a category in the finance tracker
 * The budget here never exceeds the budget of the finance tracker
 */
public class CategoryBudget extends Budget {

    private Category categoryOfBudget;

    public CategoryBudget (String category, Double budget) {
        super(budget);
        checkArgument(Category.isValidCategoryName(category), Category.MESSAGE_CONSTRAINTS);
        categoryOfBudget = new Category(category);
    }

    public CategoryBudget (String category, Double budget, Double currentBudget) {
        super(budget, currentBudget);
        checkArgument(Category.isValidCategoryName(category), Category.MESSAGE_CONSTRAINTS);
        this.categoryOfBudget = new Category(category);
    }

    public CategoryBudget (CategoryBudget catBudget) {
        super(catBudget);
        categoryOfBudget = catBudget.getCategory();
    }

    @Override
    public boolean equals(Object catBudget) {
        CategoryBudget otherCatBudget = (CategoryBudget) catBudget;
        return categoryOfBudget.equals(otherCatBudget.getCategory());
    }

    @Override
    public String toString() {
        return categoryOfBudget + ":\n"
                + String.format("$%.2f/ $%.2f\n", getCurrentSpendings(), getTotalBudget());
    }

    @Override
    public int hashCode() {
        return categoryOfBudget.hashCode();
    }

    public Category getCategory() {
        return this.categoryOfBudget;
    }

}
