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
    private String name = "Anonymous";
    private Path foodDiaryFilePath = Paths.get("data" , "fooddiary.json");


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
        setFoodDiaryFilePath(newUserPrefs.getFoodDiaryFilePath());
        setName(newUserPrefs.getName());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getFoodDiaryFilePath() {
        return foodDiaryFilePath;
    }

    public String getName() {
        return name;
    }

    public void setFoodDiaryFilePath(Path foodDiaryFilePath) {
        requireNonNull(foodDiaryFilePath);
        this.foodDiaryFilePath = foodDiaryFilePath;
    }

    public void setName(String name) {
        this.name = name;
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
                && foodDiaryFilePath.equals(o.foodDiaryFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, foodDiaryFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + foodDiaryFilePath);
        sb.append("\nName" + name);
        return sb.toString();
    }

}
