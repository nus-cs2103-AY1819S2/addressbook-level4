package seedu.travel.model;

import java.nio.file.Path;

import seedu.travel.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getTravelBuddyFilePath();

}
