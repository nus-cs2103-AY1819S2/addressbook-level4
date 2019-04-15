package seedu.finance.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.model.category.Category;
import seedu.finance.testutil.Assert;

public class JsonAdaptedCategoryTest {

    private static final String INVALID_CATEGORY_NAME = "Food@";
    private static final String VALID_CATEGORY_NAME = "Food";
    private static final Category catExample = new Category(VALID_CATEGORY_NAME);

    @Test
    public void toModelType_validCategoryName_returnCategory() throws Exception {
        JsonAdaptedCategory validCategory = new JsonAdaptedCategory(VALID_CATEGORY_NAME);
        assertEquals(validCategory.toModelType(), catExample);
    }

    @Test
    public void toModelType_invalidCategoryName_throwsIllegalValueException() {
        JsonAdaptedCategory invalidCategory = new JsonAdaptedCategory(INVALID_CATEGORY_NAME);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, invalidCategory::toModelType);
    }
}
