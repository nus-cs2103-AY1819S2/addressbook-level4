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
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private Path requestBookFilePath = Paths.get("data" , "requestbook.json");
    private Path healthWorkerBookFilePath = Paths.get("data" , "healthworkerbook.json");


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
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setHealthWorkerBookFilePath(newUserPrefs.getHealthWorkerBookFilePath());
        setRequestBookFilePath(newUserPrefs.getRequestBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }
    public Path getRequestBookFilePath() {
        return this.requestBookFilePath;
    }
    public void setRequestBookFilePath(Path requestBookFilePath) {
        requireNonNull(requestBookFilePath);
        this.requestBookFilePath = requestBookFilePath;
    }

    public Path getHealthWorkerBookFilePath() {
        return healthWorkerBookFilePath;
    }

    public void setHealthWorkerBookFilePath(Path healthWorkerBookFilePath) {
        requireNonNull(healthWorkerBookFilePath);
        this.healthWorkerBookFilePath = healthWorkerBookFilePath;
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
                && addressBookFilePath.equals(o.addressBookFilePath)
                && requestBookFilePath.equals(o.requestBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nAddress book Local data file location : " + addressBookFilePath);
        sb.append("\nRequest book Local data file location : " + requestBookFilePath);
        sb.append("\nHealthWorker book Local data file location : " + healthWorkerBookFilePath);
        return sb.toString();
    }


}
