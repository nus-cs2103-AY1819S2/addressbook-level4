package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class KnownProgLangTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new KnownProgLang(null));
    }

    @Test
    public void constructor_invalidPastJob_throwsIllegalArgumentException() {
        String invalidKnownProgLang = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new KnownProgLang(invalidKnownProgLang));
    }

    @Test
    public void isValidKnowProgLang() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> KnownProgLang.isValidKnownProgLang(null));

        // invalid addresses
        assertFalse(KnownProgLang.isValidKnownProgLang("")); // empty string
        assertFalse(KnownProgLang.isValidKnownProgLang(" ")); // spaces only

        // valid addresses
        assertTrue(KnownProgLang.isValidKnownProgLang("Python"));
        assertTrue(KnownProgLang.isValidKnownProgLang("-")); // one character
        assertTrue(KnownProgLang.isValidKnownProgLang("CPP")); // short job title
    }
}
