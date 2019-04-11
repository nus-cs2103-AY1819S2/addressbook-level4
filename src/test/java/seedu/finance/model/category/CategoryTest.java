package seedu.finance.model.category;

import org.junit.Test;

import seedu.finance.testutil.Assert;

public class CategoryTest {
    public static final String INVALID_CATEGORY_NAME_EMPTY = "";
    public static final String INVALID_CATEGORY_NAME_NOT_ALPHANUM = "Food%";
    public static final String VALID_CATEGORY_NAME = "Food";
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategoryName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Category(INVALID_CATEGORY_NAME_EMPTY));
    }

    @Test
    public void isValidCategoryName() {
        // null category name
        Assert.assertThrows(NullPointerException.class, () -> Category.isValidCategoryName(null));
    }

}
