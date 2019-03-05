package braintrain.model;

import java.nio.file.Path;

import braintrain.commons.core.GuiSettings;


/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getLessonsFolderPath();

    Path getLessonImportExportFilePath();
}
