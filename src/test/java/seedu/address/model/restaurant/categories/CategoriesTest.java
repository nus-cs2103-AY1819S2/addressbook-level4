package seedu.address.model.restaurant.categories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

import org.junit.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalCategories;

public class CategoriesTest {
    @Test
    public void getCategoriesSuggestions() {
        // null prefix
        Assert.assertThrows(NullPointerException.class, () -> Categories.getCategoriesSuggestions(null));

        // prefix not Cuisine, Occasion or Price Range should return empty ArrayList
        Prefix notCategories = PREFIX_ADDRESS;
        assertEquals(0, Categories.getCategoriesSuggestions(notCategories).size());
    }

    @Test
    public void merge() {
        // null previous category or updated category
        Assert.assertThrows(AssertionError.class, () -> Categories.merge(null, null));
        Assert.assertThrows(AssertionError.class, () -> Categories.merge(null,
                TypicalCategories.VALID_ALL_SET));
        Assert.assertThrows(AssertionError.class, () -> Categories.merge(TypicalCategories.VALID_ALL_SET, null));

        // merge should not overwrite non updated fields
        Categories previous = TypicalCategories.VALID_ALL_SET;
        Categories updated = TypicalCategories.VALID_CUISINE_NOT_SET;
        assertEquals(Categories.merge(previous, updated), new Categories(previous.getCuisine(), updated.getOccasion(),
                updated.getPriceRange()));
    }

    @Test
    public void match() {
        Categories allSet = TypicalCategories.VALID_ALL_SET;
        assertTrue(allSet.match("western")); // western should match Western
        assertTrue(allSet.match("$$$$$"));
        assertTrue(allSet.match("fine")); // should match fine dining

        assertFalse(allSet.match("wes"));
        assertFalse(allSet.match("$"));
        assertFalse(allSet.match("finedining"));
    }
}
