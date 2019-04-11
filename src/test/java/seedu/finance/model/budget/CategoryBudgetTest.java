package seedu.finance.model.budget;
//@author jackimaru96

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.finance.model.category.CategoryTest.INVALID_CATEGORY_NAME_EMPTY;
import static seedu.finance.model.category.CategoryTest.VALID_CATEGORY_NAME;

import org.junit.Test;

import seedu.finance.testutil.Assert;

public class CategoryBudgetTest {

    public static final Double VALID_TOTAL_BUDGET = 50.00;
    public static final Double VALID_CURRENT_BUDGET = 45.00;

    private CategoryBudget sampleCatBudget = new CategoryBudget(VALID_CATEGORY_NAME, VALID_TOTAL_BUDGET,
            VALID_CURRENT_BUDGET);
    private CategoryBudget sampleCatBudgetWithDifferentBudget = new CategoryBudget(VALID_CATEGORY_NAME,
            40.00, 30.00);
    private CategoryBudget sampleCatBudget2 = new CategoryBudget("Wrong",
            VALID_TOTAL_BUDGET, VALID_CURRENT_BUDGET);

    @Test
    public void constructor_invalidCategoryStringValidBudget_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new CategoryBudget(INVALID_CATEGORY_NAME_EMPTY,
                VALID_TOTAL_BUDGET, VALID_CURRENT_BUDGET));
    }

    @Test
    public void constructor_invalidCategoryBudget_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CategoryBudget(null));
    }


    @Test
    public void equals() {
        // CategoryBudgets with same Category -> true
        assertTrue(new CategoryBudget(VALID_CATEGORY_NAME, VALID_TOTAL_BUDGET, VALID_CURRENT_BUDGET)
                .equals(sampleCatBudget));

        // CategoryBudgets with different total budget and current budget -> true
        assertTrue(sampleCatBudget.equals(new CategoryBudget(VALID_CATEGORY_NAME, 60.00, 50.00)));

        // CategoryBudgets with different Category -> false
        assertFalse(new CategoryBudget(VALID_CATEGORY_NAME, VALID_TOTAL_BUDGET, VALID_CURRENT_BUDGET)
                .equals(new CategoryBudget("WrongName", VALID_TOTAL_BUDGET, VALID_CURRENT_BUDGET)));

        // CategoryBudgets using constructor with only CategoryBudget as parameter, and different categories
        // -> false
        assertFalse(sampleCatBudget.equals(sampleCatBudget2));

    }

    @Test
    public void testingToString() {
        // Same object -> true
        assertTrue(sampleCatBudget.toString().equals(sampleCatBudget.toString()));

        // Different object -> false
        assertFalse(sampleCatBudget.toString().equals(sampleCatBudget2.toString()));

    }

    @Test
    public void testingHashCode() {
        // CategoryBudget with same Category and Budgets -> true
        assertTrue(sampleCatBudget.hashCode() == sampleCatBudget.hashCode());

        // CategoryBudget with same Category but different Budgets -> true
        assertTrue(sampleCatBudget.hashCode() == sampleCatBudgetWithDifferentBudget.hashCode());

        // CateogryBudget with different Category and same Budget -> false
        assertFalse(sampleCatBudget.hashCode() == sampleCatBudget2.hashCode());
    }
}
