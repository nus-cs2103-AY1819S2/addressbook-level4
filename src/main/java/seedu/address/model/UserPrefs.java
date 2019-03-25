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
    private GuiSettings guiSettings = new GuiSettings();
    private Path lessonsFolderPath = Paths.get("data");
    private Path userFilePath = Paths.get("data", "user", "savedata.csv");
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
        setLessonsFolderPath(newUserPrefs.getLessonsFolderPath());
        setUserFilePath(newUserPrefs.getUserFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getLessonsFolderPath() {
        return lessonsFolderPath;
    }

    public void setLessonsFolderPath(Path lessonsFolderPath) {
        requireNonNull(lessonsFolderPath);
        this.lessonsFolderPath = lessonsFolderPath;
    }

    public Path getUserFilePath() {
        return userFilePath;
    }

    public void setUserFilePath(Path userFilePath) {
        requireNonNull(userFilePath);
        this.userFilePath = userFilePath;
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
        return Objects.hash(guiSettings, lessonsFolderPath, userFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + lessonsFolderPath);
        sb.append("\nLocal user file location : " + userFilePath);
        return sb.toString();
    }


}
