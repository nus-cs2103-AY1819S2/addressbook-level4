package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.travel.testutil.Assert;

public class CountryCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CountryCode(null));
    }

    @Test
    public void constructor_invalidCountryCode_throwsIllegalArgumentException() {
        String invalidCountryCode = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Rating(invalidCountryCode));
    }

    @Test
    public void isValidCountryCode() {
        // null country code
        Assert.assertThrows(NullPointerException.class, () -> CountryCode.isValidCountryCode(null));

        // invalid country code
        assertFalse(CountryCode.isValidCountryCode("")); // empty string
        assertFalse(CountryCode.isValidCountryCode(" ")); // spaces only
        assertFalse(CountryCode.isValidCountryCode("  ")); // spaces only
        assertFalse(CountryCode.isValidCountryCode("SG")); // only 2 letters
        assertFalse(CountryCode.isValidCountryCode("123")); // numeric
        assertFalse(CountryCode.isValidCountryCode("p")); // only 1 letters
        assertFalse(CountryCode.isValidCountryCode("SGX")); // not a country code

        // valid country code
        assertTrue(CountryCode.isValidCountryCode("SGP"));
        assertTrue(CountryCode.isValidCountryCode("JPN"));
        assertTrue(CountryCode.isValidCountryCode("USA"));
    }
}
