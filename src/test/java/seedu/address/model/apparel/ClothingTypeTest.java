package seedu.address.model.apparel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;

public class ClothingTypeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ClothingType(invalidEmail));
    }

    @Test
    public void isValidClothingType() {
        // empty clothingType
        assertFalse(ClothingTypeValue.isValidClothingType("")); // empty string

        // invalid clothingType
        assertFalse(ClothingTypeValue.isValidClothingType(" ")); // spaces only
        assertFalse(ClothingTypeValue.isValidClothingType("19838")); // numbers
        assertFalse(ClothingTypeValue.isValidClothingType("189a81")); // alphabets within digits
        assertFalse(ClothingTypeValue.isValidClothingType("TOP ")); // additional space not accepted

        // valid clothingType
        // case insensitive
        assertTrue(ClothingTypeValue.isValidClothingType("TOP"));
        assertTrue(ClothingTypeValue.isValidClothingType("tOP"));

        assertTrue(ClothingTypeValue.isValidClothingType("BOTTOM"));
        assertTrue(ClothingTypeValue.isValidClothingType("bOttoM"));

        assertTrue(ClothingTypeValue.isValidClothingType("BELT"));
        assertTrue(ClothingTypeValue.isValidClothingType("beLt"));

        assertTrue(ClothingTypeValue.isValidClothingType("SHOES"));
        assertTrue(ClothingTypeValue.isValidClothingType("shOEs"));
    }
}
