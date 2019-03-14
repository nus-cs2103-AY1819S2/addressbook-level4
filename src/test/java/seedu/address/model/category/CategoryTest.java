package seedu.address.model.category;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidCategoryName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategoryName));
    }

    @Test
    public void isValidTagName() {
        // null category name
        Assert.assertThrows(NullPointerException.class, () -> Category.isValidCategoryName(null));
    }

}
