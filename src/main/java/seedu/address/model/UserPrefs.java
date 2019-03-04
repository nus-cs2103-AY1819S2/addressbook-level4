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
    private Path menuFilePath = Paths.get("data", "menu.json");
    private Path restOrRantFilePath = Paths.get("data" , "restorrant.json");
    private Path ordersFilePath = Paths.get("data" , "orders.json");

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
        setRestOrRantFilePath(newUserPrefs.getRestOrRantFilePath());
        setOrdersFilePath(newUserPrefs.getOrdersFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getRestOrRantFilePath() {
        return restOrRantFilePath;
    } // TODO: remove and add each feature's file path
    
    public Path getMenuFilePath() {
        return menuFilePath;
    }
    
    public void setRestOrRantFilePath(Path restOrRantFilePath) { // TODO: remove and add each feature's file path
        requireNonNull(restOrRantFilePath);
        this.restOrRantFilePath = restOrRantFilePath;
    }
    public void setMenuFilePath(Path menuFilePath) {
        requireNonNull(menuFilePath);
        this.menuFilePath = menuFilePath;
    }

    public Path getOrdersFilePath() {
        return ordersFilePath;
    }

    public void setOrdersFilePath(Path ordersFilePath) {
        requireNonNull(ordersFilePath);
        this.ordersFilePath = ordersFilePath;
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
                && restOrRantFilePath.equals(o.restOrRantFilePath)
                && ordersFilePath.equals(o.ordersFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, restOrRantFilePath, ordersFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + restOrRantFilePath);
        sb.append("\nLocal data file location for orders: " + ordersFilePath);
        return sb.toString();
    }

}
