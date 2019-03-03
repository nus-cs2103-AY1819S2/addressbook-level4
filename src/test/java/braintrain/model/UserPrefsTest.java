package braintrain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.nio.file.Paths;

import org.junit.Test;

import braintrain.testutil.Assert;

public class UserPrefsTest {
    private final UserPrefs userPref = new UserPrefs();

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setLessonImportExportFilePath_nullLessonImportExportFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> userPref.setLessonImportExportFilePath(null));
    }

    @Test
    public void setLessonsFolderPath_nullLessonsFolderPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> userPref.setLessonsFolderPath(null));
    }

    @Test
    public void equals() {
        UserPrefs userPrefCopy = new UserPrefs(userPref);
        UserPrefs userPrefDiff = new UserPrefs(userPref);
        userPrefDiff.setLessonsFolderPath(Paths.get("test_different"));
        assertEquals(userPref, userPref);
        assertEquals(userPrefCopy, userPref);
        assertNotEquals(userPrefDiff, userPref);
        assertNotEquals(new Object(), userPref);
        assertNotEquals(userPref, new Object());
        assertNotEquals(userPref, "");
    }
}
