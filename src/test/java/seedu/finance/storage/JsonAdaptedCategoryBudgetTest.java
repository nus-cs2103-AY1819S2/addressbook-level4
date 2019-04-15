package seedu.finance.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.category.Category;
import seedu.finance.testutil.Assert;

public class JsonAdaptedCategoryBudgetTest {
    private static final String VALID_CATEGORY_1 = "Food";
    private static final String VALID_TOTAL_BUDGET = "50.00";
    private static final String VALID_CURRENT_BUDGET = "20.00";
    private static final String INVALID_CATEGORY = "F@ ASD";


    @Test
    public void toModelType_validCategoryBudget_returnsCategoryBudget() throws Exception {
        CategoryBudget categoryBudget = new CategoryBudget(VALID_CATEGORY_1,
                Double.parseDouble(VALID_TOTAL_BUDGET), Double.parseDouble(VALID_CURRENT_BUDGET));
        JsonAdaptedCategoryBudget catBudget = new JsonAdaptedCategoryBudget(categoryBudget);
        assertEquals(catBudget.toModelType(), categoryBudget);
    }

    @Test
    public void toModelType_invalidCategoryName_throwsIllegalValueException() {
        JsonAdaptedCategory category = new JsonAdaptedCategory(INVALID_CATEGORY);
        JsonAdaptedCategoryBudget catBudget = new JsonAdaptedCategoryBudget(category,
                VALID_TOTAL_BUDGET, VALID_CURRENT_BUDGET);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, catBudget::toModelType);
    }
}
