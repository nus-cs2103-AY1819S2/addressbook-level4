package quickdocs.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quickdocs.testutil.Assert;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String onlySpace = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(onlySpace));

        String emptyGender = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(emptyGender));

        String onlySymbols = "@";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(onlySymbols));

        String longForm = "Male";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(longForm));

        String invalidGenderCode = "N";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGenderCode));
    }

    @Test
    public void equals() {
        Gender gender = new Gender("M");
        assertTrue(gender.equals(gender));

        Gender gender2 = new Gender("M");
        assertTrue(gender.equals(gender2));

        Gender gender3 = new Gender("F");
        assertFalse(gender.equals(gender3));

    }
}
