package quickdocs.model;

import java.nio.file.Path;

import quickdocs.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getQuickDocsFilePath();
}
