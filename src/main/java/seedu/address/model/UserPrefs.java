package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {
    public static final String LIGHT_THEME = "view/LightTheme.css";
    public static final String DARK_THEME = "view/DarkTheme.css";

    private GuiSettings guiSettings = new GuiSettings();
    private Path lessonListFolderPath = Paths.get("lessons");
    private Path userFilePath = Paths.get("userdata", "savedata.csv");
    private String theme = LIGHT_THEME;

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setLessonListFolderPath(newUserPrefs.getLessonListFolderPath());
        setUserFilePath(newUserPrefs.getUserFilePath());
        setTheme(newUserPrefs.getTheme());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getLessonListFolderPath() {
        return lessonListFolderPath;
    }

    public void setLessonListFolderPath(Path lessonListFolderPath) {
        requireNonNull(lessonListFolderPath);
        this.lessonListFolderPath = lessonListFolderPath;
    }

    public Path getUserFilePath() {
        return userFilePath;
    }

    public void setUserFilePath(Path userFilePath) {
        requireNonNull(userFilePath);
        this.userFilePath = userFilePath;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String newTheme) {
        requireNonNull(newTheme);
        this.theme = newTheme;
    }

    public String toggleTheme() {
        return theme.equals(LIGHT_THEME) ? DARK_THEME : LIGHT_THEME;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, lessonListFolderPath, userFilePath, theme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + lessonListFolderPath);
        sb.append("\nLocal user file location : " + userFilePath);
        sb.append("\nTheme location : " + theme);
        return sb.toString();
    }


}
