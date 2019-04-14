package seedu.address.model.restaurant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OpeningHoursTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OpeningHours(null));
    }

    @Test
    public void constructor_invalidOpeningHours_throwsIllegalArgumentException() {
        String invalidOpeningHours = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OpeningHours(invalidOpeningHours));
    }

    @Test
    public void isValidOpeningHours() {
        // null OpeningHours
        Assert.assertThrows(NullPointerException.class, () -> OpeningHours.isValidOpeningHours(null));

        // blank OpeningHours
        assertFalse(OpeningHours.isValidOpeningHours("")); // empty string
        assertFalse(OpeningHours.isValidOpeningHours(" ")); // spaces only

        // missing number
        assertFalse(OpeningHours.isValidOpeningHours("000 to 2100"));
        assertFalse(OpeningHours.isValidOpeningHours("0000 to 210"));
        assertFalse(OpeningHours.isValidOpeningHours("00 to 210"));

        // invalid hours
        assertFalse(OpeningHours.isValidOpeningHours("0000 to 0000")); // should be 24hrs
        assertFalse(OpeningHours.isValidOpeningHours("1200 to 1200")); // cannot be same hours
        assertFalse(OpeningHours.isValidOpeningHours("0000 to 2400"));
        assertFalse(OpeningHours.isValidOpeningHours("0000 to 2401"));
        assertFalse(OpeningHours.isValidOpeningHours("0000 to 3000"));
        assertFalse(OpeningHours.isValidOpeningHours("0000 to 9999"));
        assertFalse(OpeningHours.isValidOpeningHours("0000 to 2360"));
        assertFalse(OpeningHours.isValidOpeningHours("0000 to 2399"));

        // missing to
        assertFalse(OpeningHours.isValidOpeningHours("0000 2359"));

        // valid OpeningHours
        assertTrue(OpeningHours.isValidOpeningHours("0000 to 2359"));
        assertTrue(OpeningHours.isValidOpeningHours("0001 to 2359"));
        assertTrue(OpeningHours.isValidOpeningHours("0001 to 1159"));
        assertTrue(OpeningHours.isValidOpeningHours("0001 to 1200"));
        assertTrue(OpeningHours.isValidOpeningHours("0000 to 0001"));
        assertTrue(OpeningHours.isValidOpeningHours("2359 to 0000"));
        assertTrue(OpeningHours.isValidOpeningHours("2359 to 1200")); // Can be from 1159 pm to 12noon next day
        assertTrue(OpeningHours.isValidOpeningHours("24hrs")); // to signify 24hrs

        // Placeholder should be return true. This is to allow writing placeholder into json
        assertTrue(OpeningHours.isValidOpeningHours("No opening hours added")); // should not
    }
}
