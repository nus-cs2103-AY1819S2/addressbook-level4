package seedu.finance.model.category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        // invalid category name
        assertFalse(Category.isValidCategoryName("")); // empty string
        assertFalse(Category.isValidCategoryName(" ")); // space only
        assertFalse(Category.isValidCategoryName("Blue bags")); // Spaces not allowed
        assertFalse(Category.isValidCategoryName("^")); // non-alphanumeric characters not allowed
        assertFalse(Category.isValidCategoryName("12345678901234567890123456789012345678901")); // 41 characters


        // valid category name
        assertTrue(Category.isValidCategoryName("Food")); // alphabets only
        assertTrue(Category.isValidCategoryName("12345")); // numbers only
        assertTrue(Category.isValidCategoryName("1234567890123456789012345678901234567890")); // 40 characters



    }

}
