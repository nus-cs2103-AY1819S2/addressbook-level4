package seedu.hms.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.hms.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path hotelManagementSystemFilePath = Paths.get("data", "hotelManagementSystem.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

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
        setHotelManagementSystemFilePath(newUserPrefs.getHotelManagementSystemFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getHotelManagementSystemFilePath() {
        return hotelManagementSystemFilePath;
    }

    public void setHotelManagementSystemFilePath(Path hotelManagementSystemFilePath) {
        requireNonNull(hotelManagementSystemFilePath);
        this.hotelManagementSystemFilePath = hotelManagementSystemFilePath;
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

        return guiSettings.equals(o.guiSettings)
            && hotelManagementSystemFilePath.equals(o.hotelManagementSystemFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, hotelManagementSystemFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + hotelManagementSystemFilePath);
        return sb.toString();
    }

}
