package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.WarningPanelSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private WarningPanelSettings warningPanelSettings = new WarningPanelSettings();
    private InformationPanelSettings informationPanelSettings = new InformationPanelSettings();
    private Path inventoryFilePath = Paths.get("data" , "MediTabs.json");

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
        setInventoryFilePath(newUserPrefs.getInventoryFilePath());
        setWarningPanelSettings(newUserPrefs.getWarningPanelSettings());
        setInformationPanelSettings(newUserPrefs.getInformationPanelSettings());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public WarningPanelSettings getWarningPanelSettings() {
        return warningPanelSettings;
    }

    public void setWarningPanelSettings(WarningPanelSettings warningPanelSettings) {
        requireNonNull(warningPanelSettings);
        this.warningPanelSettings = warningPanelSettings;
    }

    public InformationPanelSettings getInformationPanelSettings() {
        return informationPanelSettings;
    }

    public void setInformationPanelSettings(InformationPanelSettings informationPanelSettings) {
        requireNonNull(informationPanelSettings);
        this.informationPanelSettings = informationPanelSettings;
    }

    public Path getInventoryFilePath() {
        return inventoryFilePath;
    }

    public void setInventoryFilePath(Path inventoryFilePath) {
        requireNonNull(inventoryFilePath);
        this.inventoryFilePath = inventoryFilePath;
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
                && inventoryFilePath.equals(o.inventoryFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, inventoryFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nWarning Panel Settings : " + warningPanelSettings);
        sb.append("\nInformation Panel Settings : " + informationPanelSettings);
        sb.append("\nLocal data file location : " + inventoryFilePath);
        return sb.toString();
    }

}
