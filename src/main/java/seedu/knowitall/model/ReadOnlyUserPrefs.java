package seedu.knowitall.model;

import java.nio.file.Path;

import seedu.knowitall.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getcardFolderFilesPath();

}
