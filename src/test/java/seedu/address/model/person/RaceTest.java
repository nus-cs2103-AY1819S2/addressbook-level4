package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RaceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Race(null));
    }

    @Test
    public void constructor_invalidRace_throwsIllegalArgumentException() {
        String invalidRace = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Race(invalidRace));
    }

    @Test
    public void isValidRace() {
        // null race
        Assert.assertThrows(NullPointerException.class, () -> Race.isValidRace(null));

        // invalid race
        assertFalse(Race.isValidRace("")); // empty string
        assertFalse(Race.isValidRace(" ")); // spaces only
        assertFalse(Race.isValidRace("91")); // numeric
        assertFalse(Race.isValidRace("phone")); // not in list
        assertFalse(Race.isValidRace("phone999")); // mix


        // valid races
        assertTrue(Race.isValidRace("Chinese")); // in race list
        assertTrue(Race.isValidRace("Indian")); // in race list
        assertTrue(Race.isValidRace("Malay")); // in race list
        assertTrue(Race.isValidRace("Others")); // in race list
    }
}
