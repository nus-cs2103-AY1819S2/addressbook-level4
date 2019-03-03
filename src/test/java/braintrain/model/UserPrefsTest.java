package braintrain.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import braintrain.commons.core.GuiSettings;
import braintrain.testutil.Assert;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        UserPrefs userPrefsCopy = new UserPrefs();
        assertTrue(userPrefs.equals(userPrefsCopy));

        // same object -> returns true
        assertTrue(userPrefs.equals(userPrefs));

        // null -> returns false
        assertFalse(userPrefs == null);

        // different types -> returns false
        assertFalse(userPrefs.equals(5));
    }

    @Test
    public void hashcode() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(guiSettings);

        UserPrefs userPrefsCopy = new UserPrefs();
        userPrefsCopy.setGuiSettings(guiSettings);

        UserPrefs userPrefsDiff = new UserPrefs();
        userPrefsDiff.setGuiSettings(new GuiSettings(1, 2, 3, 5));
        // same values -> returns same hashcode
        assertNotEquals(userPrefs.hashCode(), userPrefsDiff.hashCode());
    }
}
