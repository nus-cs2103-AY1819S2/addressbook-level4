package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.UserPrefs.DARK_THEME;

import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.testutil.Assert;

public class UserPrefsTest {
    private final UserPrefs userPref = new UserPrefs();

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setTheme_lightToDark_success() {
        String newTheme = userPref.toggleTheme();
        userPref.setTheme(newTheme);

        assertEquals(DARK_THEME, newTheme);
        assertEquals(DARK_THEME, userPref.getTheme());
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs userPrefDiff = new UserPrefs(userPref);

        // same values -> returns true
        UserPrefs userPrefsCopy = new UserPrefs();
        assertTrue(userPrefs.equals(userPrefsCopy));

        // same object -> returns true
        assertTrue(userPrefs.equals(userPrefs));

        // null -> returns false
        assertFalse(userPrefs == null);

        // different types -> returns false
        assertFalse(userPrefs.equals(5));

        // different values -> returns false
        userPrefDiff.setLessonListFolderPath(Paths.get("test_different"));
        assertFalse(userPref.equals(userPrefDiff));
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

    @Test
    public void setUserFilePath_nullUserFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> userPref.setUserFilePath(null));
    }

    @Test
    public void setLessonListFolderPath_nullLessonListFolderPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> userPref.setLessonListFolderPath(null));
    }

    @Test
    public void toStringTest() {
        assertNotNull(userPref.toString());
    }
}
