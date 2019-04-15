package seedu.hms.model;

import java.nio.file.Path;

import seedu.hms.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getHotelManagementSystemFilePath();

}
